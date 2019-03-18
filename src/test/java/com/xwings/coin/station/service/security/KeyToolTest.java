package com.xwings.coin.station.service.security;

import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.constant.NetworkMode;
import com.xwings.coin.station.util.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.EnumUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

@Ignore
public class KeyToolTest {
    @Test
    public void testKeyToolGood1() throws GeneralSecurityException, IOException {
        KeyPair originalKeyPair = KeyTool.generateKey(Coin.EOS, NetworkMode.TESTNET);
        System.out.println(JsonUtils.convertToPrettyJson(originalKeyPair));
        KeyEntry keyEntry = KeyTool.encryptKey(originalKeyPair, "Password1");
        System.out.println(JsonUtils.convertToPrettyJson(keyEntry));
        System.out.println(keyEntry.getCrypto());
        KeyPair decryptedKeyPair = KeyTool.decryptKey(keyEntry, "Password1");
        System.out.println(JsonUtils.convertToPrettyJson(decryptedKeyPair));
        Assert.assertEquals(originalKeyPair, decryptedKeyPair);
    }

    @Test
    public void testKeyToolGood2() throws GeneralSecurityException, IOException {
        String walletPassphrase = "Ajax.Wang1&Ajax.Wang2";
        String passphraseEncryptionCode = "sldkfj9823749xvmx*#&*&$";
        KeyCrypto keyCrypto = KeyTool.encryptKey(walletPassphrase, passphraseEncryptionCode);
        System.out.println(JsonUtils.convertToPrettyJson(keyCrypto));
        Assert.assertEquals(walletPassphrase, KeyTool.decryptKey(keyCrypto, passphraseEncryptionCode));
    }

    @Test
    public void testKeyToolBad() throws GeneralSecurityException, IOException {
        KeyPair originalKeyPair = KeyTool.generateKey(Coin.BTC, NetworkMode.TESTNET);
        System.out.println(JsonUtils.convertToPrettyJson(originalKeyPair));
        KeyEntry keyEntry = KeyTool.encryptKey(originalKeyPair, "Password1");
        System.out.println(JsonUtils.convertToPrettyJson(keyEntry));
        KeyPair decryptedKeyPair = KeyTool.decryptKey(keyEntry, "Password2");
        System.out.println(JsonUtils.convertToPrettyJson(decryptedKeyPair));
        Assert.assertNotEquals(originalKeyPair, decryptedKeyPair);
    }

    public static void main(String[] args) throws Exception {
        if (args != null && args.length == 4) {
            String password = args[0];
            String asset = args[1];
            String networkMode = args[2];
            String outputDir = args[3];
            Coin coinEnum = EnumUtils.getEnum(Coin.class, asset);
            NetworkMode networkModeEnum = EnumUtils.getEnum(NetworkMode.class, networkMode);
            if (coinEnum != null && networkModeEnum != null) {
                KeyPair keyPair = KeyTool.generateKey(coinEnum, networkModeEnum);
                KeyEntry keyEntry = KeyTool.encryptKey(keyPair, password);
                String json = JsonUtils.convertToPrettyJson(keyEntry);
                System.out.println(json);
                String outputFile = outputDir + File.separator + keyEntry.getId();
                FileUtils.writeStringToFile(new File(outputFile), json, Charset.forName("UTF-8"));
                System.out.println("Encrypted key file saved to " + outputFile);
                return;
            }

            goHelp();
        }
    }

    private static void goHelp() {
        System.out.println("usage: KeyTool password asset networkMode keyStoreDir");
        System.out.println("password - password to encrypt the key");
        System.out.println("asset - BTC, ETH or XRP");
        System.out.println("networkMode - mainnet or testnet");
        System.out.println("keyStoreDir - directory to store the encrypted key file");
    }

}