package com.xwings.coin.station.rpc.request;

import com.xwings.coin.station.rpc.response.Transfer;

public class GetTransferRequest implements Request<Transfer> {
    private String walletId;
    private String transferId;

    @Override
    public Class<Transfer> getResponseType() {
        return Transfer.class;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    @Override
    public String toString() {
        return "GetTransferRequest{" +
                "walletId='" + walletId + '\'' +
                ", transferId='" + transferId + '\'' +
                '}';
    }
}
