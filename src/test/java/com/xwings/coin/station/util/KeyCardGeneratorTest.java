package com.xwings.coin.station.util;

import com.google.zxing.WriterException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class KeyCardGeneratorTest {

    @Ignore
    @Test
    public void testGenerateKeyCard() throws IOException, WriterException {
        final String userKey = "{\"version\":1,\"id\":\"10716799ac897118188934abe8918b15\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"kdf\":\"scrypt\",\"ciphertext\":\"wtxDdZs2e3pTO7KQd1nANuMKUu2WRz/vPArPVoDiMJN6F4uqD/VjSNiOEMIP45WwmzXEEq+GUtpq0/S1kcYdyl4t8K+dDxyrOmcb4pKQo4SexpUs7FLDw9HXT5tyutH4thdBhqQxRfws7ySBlJ7e\",\"cipherparams\":{\"iv\":\"x91tpv61cYas/t3JIVwU0w==\"},\"kdfparams\":{\"dklen\":16,\"n\":16384,\"p\":1,\"r\":8,\"salt\":\"/nfd8Hdm9gg=\"}},\"pub\":\"02076bee768136614812c735e851ced83c2be6413b44870e0b78d5226c3756bc6e\"}".replace(" ", "");
        final byte[] bytes = KeyCardGenerator.generateKeyCard("BTC", "AA", "AA", "AA");

        final File file = new File(System.getProperty("user.dir") + "/src/test/resources/keyCard.pdf");
        FileUtils.writeByteArrayToFile(file, bytes);

        Assert.assertTrue(file.exists());
        Assert.assertTrue(file.isFile());
    }
}