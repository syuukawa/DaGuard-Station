package com.xwings.coin.station.rpc.response;

import java.util.List;

public class ListTransfersResponse extends Response {

    private List<TransferResponse> transfers;

    public ListTransfersResponse() {
    }

    public ListTransfersResponse(List<TransferResponse> transfers) {
        this.transfers = transfers;
    }

    public List<TransferResponse> getTransferResponses() {
        return transfers;
    }

    public void setTransferResponses(List<TransferResponse> transfers) {
        this.transfers = transfers;
    }

    @Override
    public String toString() {
        return "{" +
                "transfers=" + transfers +
                '}';
    }
}
