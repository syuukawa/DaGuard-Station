package com.xwings.coin.station.service.security;

import com.google.common.base.Objects;

import java.util.Arrays;

/**
 * Created by ajax.wang on 11/15/2018.
 */
class EncryptedData {
    private final byte[] iv;
    private final byte[] encryptedBytes;
    private final String cipher;

    public EncryptedData(byte[] iv, byte[] encryptedBytes, String cipher) {
        this.iv = Arrays.copyOf(iv, iv.length);
        this.encryptedBytes = Arrays.copyOf(encryptedBytes, encryptedBytes.length);
        this.cipher = cipher;
    }

    public byte[] getIv() {
        return iv;
    }

    public byte[] getEncryptedBytes() {
        return encryptedBytes;
    }

    public String getCipher() {
        return cipher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptedData other = (EncryptedData) o;
        return Arrays.equals(encryptedBytes, other.encryptedBytes) && Arrays.equals(iv, other.iv) && cipher.equals(other.cipher);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Arrays.hashCode(encryptedBytes), Arrays.hashCode(iv), cipher);
    }

    @Override
    public String toString() {
        return "EncryptedData [initialisationVector=" + Arrays.toString(iv)
                + ", encryptedPrivateKey=" + Arrays.toString(encryptedBytes)
                + ", cipher=" + cipher + "]";
    }

}
