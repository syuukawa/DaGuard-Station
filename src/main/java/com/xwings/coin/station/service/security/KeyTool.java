package com.xwings.coin.station.service.security;

import com.google.common.collect.ImmutableMap;
import com.google.protobuf.ByteString;
import com.ripple.core.coretypes.AccountID;
import com.ripple.crypto.ecdsa.Seed;
import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.constant.NetworkMode;
import com.xwings.coin.station.util.Numbers;
import io.bigbearbro.eos4j.crypto.EccTool;
import org.apache.commons.codec.digest.DigestUtils;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.wallet.Protos;
import org.bitcoinj.wallet.Protos.ScryptParameters;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.util.Base64;

/**
 * Created by ajax.wang on 11/13/2018.
 */
public class KeyTool {
    public static final int V1 = 1;

    public static KeyEntry generateKeyEntry(Coin coin, NetworkMode networkMode, String password) throws GeneralSecurityException, IOException {
        final KeyPair keyPair = generateKey(coin, networkMode);
        final KeyEntry keyEntry = encryptKey(keyPair, password);

        return keyEntry;
    }

    public static KeyPair generateKey(Coin asset, NetworkMode networkMode) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        KeyPair keyPair = null;
        switch (asset) {
            case BTC:
                keyPair = generateBitcoinKey(networkMode);
                break;
            case ETH:
                keyPair = generateEthereumKey(networkMode);
                break;
            case XRP:
                keyPair = generateRippleKey(networkMode);
                break;
            case EOS:
                keyPair = generateEosKey(networkMode);
                break;
            case XLM:
                keyPair = generateStellarKey(networkMode);
                break;
            default:
                throw new IllegalArgumentException("Not supported coin: "+ asset);
        }

        return keyPair;
    }

    public static KeyEntry encryptKey(KeyPair keyPair, String password) throws GeneralSecurityException {
        // encrypt and build
        KeyEntry keyEntry = new KeyEntry();
        keyEntry.setVersion(V1);
        keyEntry.setPub(keyPair.getPublicKey());
        keyEntry.setId(DigestUtils.md5Hex(keyPair.getPublicKey()));
        KeyCrypto keyCrypto = encryptKey(keyPair.getPrivateKey(), password);
        keyEntry.setCrypto(keyCrypto);

        return keyEntry;
    }

    public static KeyCrypto encryptKey(String key, String password) throws GeneralSecurityException {
        // encrypt
        KeyCrypterScrypt keyCrypter = new KeyCrypterScrypt();
        byte[] keyBytes = keyCrypter.deriveKey(password);
        EncryptedData encryptedData = keyCrypter.encrypt(key.getBytes(), keyBytes);

        // build
        KeyCrypto keyCrypto = new KeyCrypto();
        keyCrypto.setCipher(encryptedData.getCipher());
        keyCrypto.setCipherText(Base64.getEncoder().encodeToString(encryptedData.getEncryptedBytes()));
        keyCrypto.setCipherParams(ImmutableMap.of("iv", Base64.getEncoder().encodeToString(encryptedData.getIv())));
        keyCrypto.setKdf("scrypt");
        Protos.ScryptParameters scryptParameters = keyCrypter.getScryptParameters();
        keyCrypto.setKdfParams(ImmutableMap.of("dklen", KeyCrypterScrypt.KEY_SIZE,
                "n", scryptParameters.getN(),
                "p", scryptParameters.getP(),
                "r", scryptParameters.getR(),
                "salt", Base64.getEncoder().encodeToString(scryptParameters.getSalt().toByteArray())));

        return keyCrypto;
    }

    public static KeyPair decryptKey(KeyEntry keyEntry, String password) {
        // get params
        KeyCrypto keyCrypto = keyEntry.getCrypto();
        String privateKey = decryptKey(keyCrypto, password);

        return new KeyPair(privateKey, keyEntry.getPub());
    }

    public static String decryptKey(KeyCrypto keyCrypto, String password) {
        byte[] iv = Base64.getDecoder().decode(keyCrypto.getCipherParams().get("iv").toString());
        byte[] encryptedBytes = Base64.getDecoder().decode(keyCrypto.getCipherText());

        // scrypt parameters
        int dkLen = Numbers.intValue(keyCrypto.getKdfParams().get("dklen"));
        int n = Numbers.intValue(keyCrypto.getKdfParams().get("n"));
        int p = Numbers.intValue(keyCrypto.getKdfParams().get("p"));
        int r = Numbers.intValue(keyCrypto.getKdfParams().get("r"));
        byte[] salt = Base64.getDecoder().decode(keyCrypto.getKdfParams().get("salt").toString());

        Protos.ScryptParameters.Builder scryptParametersBuilder = ScryptParameters.newBuilder()
                .setN(n).setP(p).setR(r).setSalt(ByteString.copyFrom(salt));
        ScryptParameters scryptParams = scryptParametersBuilder.build();
        KeyCrypterScrypt keyCrypter = new KeyCrypterScrypt(scryptParams);
        byte[] keyBytes = keyCrypter.deriveKey(password, dkLen);
        EncryptedData encryptedData = new EncryptedData(iv, encryptedBytes, keyCrypto.getCipher());

        return new String(keyCrypter.decrypt(encryptedData, keyBytes));
    }

    private static KeyPair generateBitcoinKey(NetworkMode networkMode) {
        byte[] seedBytes = new byte[32];
        new SecureRandom().nextBytes(seedBytes);
        DeterministicKey key = HDKeyDerivation.createMasterPrivateKey(seedBytes);
        ECKey ecKey = ECKey.fromPrivate(key.getPrivKeyBytes());
        NetworkParameters networkParams = networkMode.toNetworkParameters();

        return new KeyPair(key.serializePrivB58(networkParams), ecKey.getPublicKeyAsHex());
    }

    private static KeyPair generateEthereumKey(NetworkMode networkMode) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        ECKeyPair keyPair = Keys.createEcKeyPair();
        Credentials credentials = Credentials.create(keyPair);

        return new KeyPair(Numeric.toHexStringNoPrefix(keyPair.getPrivateKey()), credentials.getAddress());
    }

    private static KeyPair generateRippleKey(NetworkMode networkMode) {
        byte[] seedBytes = new byte[16];
        new SecureRandom().nextBytes(seedBytes);
        String seed = new Seed(seedBytes).toString();

        return new KeyPair(seed, AccountID.fromSeedString(seed).address);
    }

    private static KeyPair generateEosKey(NetworkMode networkMode) {
        byte[] seedBytes = new byte[32];
        new SecureRandom().nextBytes(seedBytes);
        String privateKey = EccTool.privateKeyFromBigInteger(new BigInteger(seedBytes));
        String publicKey = EccTool.privateToPublic(privateKey);

        return new KeyPair(privateKey, publicKey);
    }

    private static KeyPair generateStellarKey(NetworkMode networkMode) {
        org.stellar.sdk.KeyPair keyPair = org.stellar.sdk.KeyPair.random();
        String privateKey = new String(keyPair.getSecretSeed());
        String publicKey = keyPair.getAccountId();
        return new KeyPair(privateKey, publicKey);
    }

}
