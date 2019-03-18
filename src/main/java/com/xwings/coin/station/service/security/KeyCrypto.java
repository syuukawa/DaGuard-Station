package com.xwings.coin.station.service.security;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by ajax.wang on 11/13/2018.
 */
public class KeyCrypto {

    private String cipher;
    @JsonProperty("ciphertext")
    private String cipherText;
    @JsonProperty("cipherparams")
    private Map<String, Object> cipherParams;
    private String kdf;
    @JsonProperty("kdfparams")
    private Map<String, Object> kdfParams;

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public String getCipherText() {
        return cipherText;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    public Map<String, Object> getCipherParams() {
        return cipherParams;
    }

    public void setCipherParams(Map<String, Object> cipherParams) {
        this.cipherParams = cipherParams;
    }

    public String getKdf() {
        return kdf;
    }

    public void setKdf(String kdf) {
        this.kdf = kdf;
    }

    public Map<String, Object> getKdfParams() {
        return kdfParams;
    }

    public void setKdfParams(Map<String, Object> kdfParams) {
        this.kdfParams = kdfParams;
    }
}
