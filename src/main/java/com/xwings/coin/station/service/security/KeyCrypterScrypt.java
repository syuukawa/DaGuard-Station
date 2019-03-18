package com.xwings.coin.station.service.security;

import com.google.common.base.Stopwatch;
import com.google.protobuf.ByteString;
import com.lambdaworks.crypto.SCrypt;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.crypto.KeyCrypterException;
import org.bitcoinj.wallet.Protos;
import org.bitcoinj.wallet.Protos.ScryptParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * Created by ajax.wang on 11/14/2018.
 */
class KeyCrypterScrypt {
    private static final Logger LOG = LoggerFactory.getLogger(KeyCrypterScrypt.class);

    /**
     * Key size in bytes.
     */
    public static final int KEY_SIZE = 32; // = 256 bits.

    /**
     * The size of an AES block in bytes.
     * This is also the length of the initialisation vector.
     */
    public static final int BLOCK_SIZE = 16;  // = 128 bits.

    /**
     * The size of the salt used.
     */
    public static final int SALT_SIZE = 8;

    /**
     * Default cipher mode.
     */
    public static final String CIPHER_MODE = "CTR";

    // Scrypt parameters.
    private ScryptParameters scryptParameters;

    public KeyCrypterScrypt() {
        Protos.ScryptParameters.Builder scryptParametersBuilder = Protos.ScryptParameters.newBuilder().setSalt(
                ByteString.copyFrom(randomSalt()));
        this.scryptParameters = scryptParametersBuilder.build();
    }

    public KeyCrypterScrypt(int iterations) {
        Protos.ScryptParameters.Builder scryptParametersBuilder = Protos.ScryptParameters.newBuilder()
                .setSalt(ByteString.copyFrom(randomSalt())).setN(iterations);
        this.scryptParameters = scryptParametersBuilder.build();
    }

    public KeyCrypterScrypt(ScryptParameters scryptParameters) {
        this.scryptParameters = scryptParameters;
        // Check there is a non-empty salt.
        // (Some early MultiBit wallets has a missing salt so it is not a hard fail).
        if (scryptParameters.getSalt() == null
                || scryptParameters.getSalt().toByteArray() == null
                || scryptParameters.getSalt().toByteArray().length == 0) {
            LOG.warn("You are using a ScryptParameters with no salt. Your encryption may be vulnerable to a dictionary attack.");
        }
    }

    public ScryptParameters getScryptParameters() {
        return scryptParameters;
    }

    public byte[] deriveKey(CharSequence password) throws KeyCrypterException {
        return deriveKey(password, KEY_SIZE);
    }

    public byte[] deriveKey(CharSequence password, int keySize) throws KeyCrypterException {
        byte[] passwordBytes = null;
        try {
            passwordBytes = convertToByteArray(password);
            byte[] salt = new byte[0];
            if ( scryptParameters.getSalt() != null) {
                salt = scryptParameters.getSalt().toByteArray();
            } else {
                // Warn the user that they are not using a salt.
                // (Some early MultiBit wallets had a blank salt).
                LOG.warn("You are using a ScryptParameters with no salt. Your encryption may be vulnerable to a dictionary attack.");
            }

            final Stopwatch watch = Stopwatch.createStarted();
            byte[] keyBytes = SCrypt.scrypt(passwordBytes, salt, (int) scryptParameters.getN(), scryptParameters.getR(), scryptParameters.getP(), keySize);
            watch.stop();
            LOG.info("Deriving key took {} for {} scrypt iterations.", watch, scryptParameters.getN());
            return keyBytes;
        } catch (Exception e) {
            throw new KeyCrypterException("Could not generate key from password and salt.", e);
        } finally {
            // Zero the password bytes.
            if (passwordBytes != null) {
                java.util.Arrays.fill(passwordBytes, (byte) 0);
            }
        }
    }

    public EncryptedData encrypt(byte[] plainBytes, byte[] keyBytes) throws KeyCrypterException {
        return encrypt(plainBytes, keyBytes, BLOCK_SIZE, CIPHER_MODE);
    }

    public EncryptedData encrypt(byte[] plainBytes, byte[] keyBytes, int blockSize, String mode) throws KeyCrypterException {
        try {
            // Generate iv - each encryption call has a different iv.
            byte[] iv = new byte[blockSize];
            new SecureRandom().nextBytes(iv);

            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/" + mode + "/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);

            byte[] encryptedBytes = cipher.doFinal(plainBytes);
            return new EncryptedData(iv, encryptedBytes, getCipher(cipher.getBlockSize(), mode));
        } catch (Exception e) {
            throw new KeyCrypterException("Could not encrypt bytes.", e);
        }
    }

    public byte[] decrypt(EncryptedData dataToDecrypt, byte[] keyBytes) throws KeyCrypterException {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(dataToDecrypt.getIv());
            String mode = StringUtils.split(dataToDecrypt.getCipher(), "-")[2].toUpperCase();

            Cipher cipher = Cipher.getInstance("AES/" + mode + "/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);

            return cipher.doFinal(dataToDecrypt.getEncryptedBytes());
        } catch (Exception e) {
            throw new KeyCrypterException("Could not decrypt bytes", e);
        }
    }

    private byte[] randomSalt() {
        byte[] salt = new byte[SALT_SIZE];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private byte[] convertToByteArray(CharSequence charSequence) {
        byte[] byteArray = new byte[charSequence.length() << 1];
        for(int i = 0; i < charSequence.length(); i++) {
            int bytePosition = i << 1;
            byteArray[bytePosition] = (byte) ((charSequence.charAt(i)&0xFF00)>>8);
            byteArray[bytePosition + 1] = (byte) (charSequence.charAt(i)&0x00FF);
        }
        return byteArray;
    }

    private String getCipher(int blockLength, String mode) {
        return String.format("aes-%d-%s", blockLength * 8, mode.toLowerCase());
    }

}
