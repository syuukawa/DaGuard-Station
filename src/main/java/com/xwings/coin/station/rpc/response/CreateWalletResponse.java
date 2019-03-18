package com.xwings.coin.station.rpc.response;

public class CreateWalletResponse extends Response {
    private String walletId;
    private String coin;
    private String label;
    private String userKey;
    private String backupKey;
    private String encryptedWalletPassword;

    public CreateWalletResponse(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public CreateWalletResponse(WalletResponse wallet, UserKey userKey) {
        this.walletId = wallet.getWalletId();
        this.coin = wallet.getCoin();
        this.label = wallet.getLabel();
        this.userKey = userKey.getUserKey();
        this.backupKey = userKey.getPubKey();
        this.encryptedWalletPassword = userKey.getWalletKey();
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getBackupKey() {
        return backupKey;
    }

    public void setBackupKey(String backupKey) {
        this.backupKey = backupKey;
    }

    public String getEncryptedWalletPassword() {
        return encryptedWalletPassword;
    }

    public void setEncryptedWalletPassword(String encryptedWalletPassword) {
        this.encryptedWalletPassword = encryptedWalletPassword;
    }

    @Override
    public String toString() {
        return "CreateWalletResponse{" +
                "walletId='" + walletId + '\'' +
                ", coin='" + coin + '\'' +
                ", label='" + label + '\'' +
                ", userKey='" + userKey + '\'' +
                ", backupKey='" + backupKey + '\'' +
                ", encryptedWalletPassword='" + encryptedWalletPassword + '\'' +
                '}';
    }
}
