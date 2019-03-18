package com.xwings.coin.station.service;

import com.google.common.base.Strings;
import com.google.zxing.WriterException;
import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.constant.ErrorCode;
import com.xwings.coin.station.constant.NetworkMode;
import com.xwings.coin.station.rpc.DaGuardClient;
import com.xwings.coin.station.rpc.RpcException;
import com.xwings.coin.station.rpc.request.CreateWalletRequest;
import com.xwings.coin.station.rpc.response.UserKey;
import com.xwings.coin.station.rpc.response.WalletResponse;
import com.xwings.coin.station.service.cosign.CosignHelper;
import com.xwings.coin.station.service.security.KeyCrypto;
import com.xwings.coin.station.service.security.KeyEntry;
import com.xwings.coin.station.service.security.KeyTool;
import com.xwings.coin.station.util.JsonUtils;
import com.xwings.coin.station.util.KeyCardGenerator;
import com.xwings.coin.station.util.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WalletService {

    private Map<String, UserKey> userKeys = new ConcurrentHashMap<>();

    @Value("${networkMode}")
    private NetworkMode networkMode;

    @Autowired
    private CosignHelper cosignHelper;

    public byte[] createUserKey(String coin, String walletPassphrase, String backupPublicKey, String id) throws IOException, GeneralSecurityException, WriterException {
        final UserKey userKey = getUserKey(coin, walletPassphrase, backupPublicKey, id);

        return KeyCardGenerator.generateKeyCard(coin, userKey.getUserKey(), userKey.getPubKey(), userKey.getWalletKey());
    }

    public UserKey getUserKey(String coin, String walletPassphrase, String backupPublicKey, String id) throws GeneralSecurityException, IOException {
        validatePubKey(coin, backupPublicKey);

        final UserKey userKeyEntity = new UserKey();
        final UserKey trulyKey = new UserKey();
        final Coin coinEnum = Coin.valueOf(coin).keyChainCoin();

        final KeyEntry userKeyEntry = KeyTool.generateKeyEntry(coinEnum, networkMode, walletPassphrase);
        userKeyEntity.setUserKey(JsonUtils.convertToJson(userKeyEntry));
        trulyKey.setUserKey(JsonUtils.convertToJson(userKeyEntry));

        userKeyEntity.setPubKey(backupPublicKey);
        trulyKey.setPubKey(backupPublicKey);
        if (Strings.isNullOrEmpty(backupPublicKey)) {
            final KeyEntry backupKeyEntry = KeyTool.generateKeyEntry(coinEnum, networkMode, RandomUtils.randomPassphrase(20));

            userKeyEntity.setPubKey(JsonUtils.convertToJson(backupKeyEntry));
            trulyKey.setPubKey(backupKeyEntry.getPub());
        }

        final String passphraseEncryptionCode = RandomUtils.randomPassphrase(20);
        final KeyCrypto walletKeyCrypto = KeyTool.encryptKey(walletPassphrase, RandomUtils.randomPassphrase(20));
        userKeyEntity.setWalletKey(JsonUtils.convertToJson(walletKeyCrypto));
        trulyKey.setWalletKey(JsonUtils.convertToJson(walletKeyCrypto));

        userKeyEntity.setPassphraseEncryptionCode(passphraseEncryptionCode);
        trulyKey.setPassphraseEncryptionCode(passphraseEncryptionCode);

        userKeys.put(id, trulyKey);

        return userKeyEntity;
    }

    public void validatePubKey(String coin, String backupPublicKey) {
        if (!Strings.isNullOrEmpty(backupPublicKey)) {
            cosignHelper.validatePubKey(Coin.valueOf(coin), backupPublicKey);
        }
    }

    public WalletResponse createWallet(DaGuardClient daGuardClient, String coin, String label, String id) throws IOException, GeneralSecurityException, ValidationException, RpcException {
        if (userKeys.get(id) == null) {
            throw new ValidationException(ErrorCode.MISSING_USER_KEY.name(), "Create keys first. ");
        }

        final UserKey userKeyEntity = userKeys.get(id);
        final String userKey = userKeyEntity.getUserKey();
        final String backupPublicKey = userKeyEntity.getPubKey();
        final String passphraseEncryptionCode = userKeyEntity.getPassphraseEncryptionCode();

        return sendCreateRequest(daGuardClient, coin, label, backupPublicKey, passphraseEncryptionCode, userKey);
    }

    private WalletResponse sendCreateRequest(DaGuardClient daGuardClient, String coin, String label, String backupKey, String passPhaseEncryptionCode, String userKey) throws IOException, InvalidKeyException, NoSuchAlgorithmException, RpcException {
        final String url = "/" + coin + "/wallets";

        final CreateWalletRequest createWalletRequest = new CreateWalletRequest();
        createWalletRequest.setLabel(label);
        createWalletRequest.setBackupKey(backupKey);
        createWalletRequest.setPassphraseEncryptionCode(passPhaseEncryptionCode);
        createWalletRequest.setUserKey(userKey);

        return daGuardClient.post(url, createWalletRequest);
    }


}
