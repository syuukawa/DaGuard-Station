package com.xwings.coin.station.controller.dto;


/**
 * Created by zihao.long on 12/4/2018.
 */
public class CreateTransfer {
    private String walletPassphrase;
    private String amount;
    private String toAddress;
    private String comment;

    public String getWalletPassphrase() {
        return walletPassphrase;
    }

    public void setWalletPassphrase(String walletPassphrase) {
        this.walletPassphrase = walletPassphrase;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CreateTransfer{" +
                "walletPassphrase='" + walletPassphrase + '\'' +
                ", amount='" + amount + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
