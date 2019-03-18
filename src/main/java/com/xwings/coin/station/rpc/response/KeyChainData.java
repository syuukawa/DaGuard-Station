package com.xwings.coin.station.rpc.response;

/**
 * Created by zihao.long on 12/17/2018.
 */
public class KeyChainData extends Response {

    private String id;

    private String publicKey;

    private String encryptedPrivateKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getEncryptedPrivateKey() {
        return encryptedPrivateKey;
    }

    public void setEncryptedPrivateKey(String encryptedPrivateKey) {
        this.encryptedPrivateKey = encryptedPrivateKey;
    }
}
