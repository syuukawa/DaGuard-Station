package com.xwings.coin.station.rpc.response;

public class UserKey extends Response {
    private String apiKey;
    private String userKey;
    private String pubKey;
    private String walletKey;
    private String passphraseEncryptionCode;

    public UserKey() {
    }

    public UserKey(String apiKey, String userKey, String pubKey, String walletKey, String passphraseEncryptionCode) {
        this.apiKey = apiKey;
        this.userKey = userKey;
        this.pubKey = pubKey;
        this.walletKey = walletKey;
        this.passphraseEncryptionCode = passphraseEncryptionCode;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getWalletKey() {
        return walletKey;
    }

    public void setWalletKey(String walletKey) {
        this.walletKey = walletKey;
    }

    public String getPassphraseEncryptionCode() {
        return passphraseEncryptionCode;
    }

    public void setPassphraseEncryptionCode(String passphraseEncryptionCode) {
        this.passphraseEncryptionCode = passphraseEncryptionCode;
    }

    @Override
    public String toString() {
        return "UserKey{" +
                "apiKey='" + apiKey + '\'' +
                ", userKey='" + userKey + '\'' +
                ", pubKey='" + pubKey + '\'' +
                ", walletKey='" + walletKey + '\'' +
                ", passphraseEncryptionCode='" + passphraseEncryptionCode + '\'' +
                '}';
    }
}
