package com.xwings.coin.station.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.web3j.utils.Numeric;

import java.security.SecureRandom;

/**
 * Created by ajax.wang on 5/24/2018.
 */
public class RandomUtils {
    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String randomHex(int bytes) {
        byte[] buf = new byte[bytes];
        RANDOM.nextBytes(buf);

        return Numeric.toHexStringNoPrefix(buf);
    }

    public static String randomPassphrase(int length) {
        return RandomStringUtils.random(length, characters);
    }
}
