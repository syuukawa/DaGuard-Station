package com.xwings.coin.station.service.security;

import java.util.Objects;

/**
 * Created by ajax.wang on 11/15/2018.
 */
class KeyPair {
    private final String privateKey;
    private final String publicKey;

    public KeyPair(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyPair other = (KeyPair) o;
        return privateKey.equals(other.privateKey) && publicKey.equals(other.publicKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(privateKey, publicKey);
    }
}
