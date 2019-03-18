package com.xwings.coin.station.controller.dto;

/**
 * Created by zihao.long on 12/19/2018.
 */
public class CreateWalletAll {
    private String label;
    private String coin;
    private String walletPassphrase;
    private String backupPublicKey;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getWalletPassphrase() {
        return walletPassphrase;
    }

    public void setWalletPassphrase(String walletPassphrase) {
        this.walletPassphrase = walletPassphrase;
    }

    public String getBackupPublicKey() {
        return backupPublicKey;
    }

    public void setBackupPublicKey(String backupPublicKey) {
        this.backupPublicKey = backupPublicKey;
    }

    @Override
    public String toString() {
        return "CreateWalletAll{" +
                "label='" + label + '\'' +
                ", coin='" + coin + '\'' +
                ", walletPassphrase='" + walletPassphrase + '\'' +
                ", backupPublicKey='" + backupPublicKey + '\'' +
                '}';
    }
}
