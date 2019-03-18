package com.xwings.coin.station.rpc.request;

import com.xwings.coin.station.rpc.response.TransferRequestResponse;

/**
 * Created by zihao.long on 12/4/2018.
 */
public class CreateTransferRequest implements Request<TransferRequestResponse> {
    private String walletPassPhrase;
    private String amount;
    private String toAddress;
    private String comment;

    @Override
    public Class<TransferRequestResponse> getResponseType() {
        return TransferRequestResponse.class;
    }

    public String getWalletPassPhrase() {
        return walletPassPhrase;
    }

    public void setWalletPassPhrase(String walletPassPhrase) {
        this.walletPassPhrase = walletPassPhrase;
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
        return "{" +
                "walletPassPhrase='" + walletPassPhrase + '\'' +
                ", amount='" + amount + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

}
