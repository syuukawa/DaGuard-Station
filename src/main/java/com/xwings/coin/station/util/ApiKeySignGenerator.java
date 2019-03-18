package com.xwings.coin.station.util;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zihao.long on 12/13/2018.
 */
public class ApiKeySignGenerator {
    private static final String SECRET_KEY_SPEC = "HmacSHA256";

    public static String generateSignature(String secret, long timestamp, String method, String path) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), SECRET_KEY_SPEC);
        Mac mac = Mac.getInstance(SECRET_KEY_SPEC);
        mac.init(secretKey);
        String message = timestamp + (method == null ? "" : method.toUpperCase()) + (path == null ? "" : path);
        byte[] hmacData = mac.doFinal(message.getBytes());
        return new String(Hex.encodeHex(hmacData));
    }
}
