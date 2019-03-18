package com.xwings.coin.station.rpc.response;

import java.util.List;

public class ListTransferRequestResponse extends Response {
    private List<TransferRequestResponse> transferRequestResponses;

    public ListTransferRequestResponse() {
    }

    public ListTransferRequestResponse(List<TransferRequestResponse> transferRequestResponses) {
        this.transferRequestResponses = transferRequestResponses;
    }

    public List<TransferRequestResponse> getTransferRequestResponses() {
        return transferRequestResponses;
    }

    public void setTransferRequestResponses(List<TransferRequestResponse> transferRequestResponses) {
        this.transferRequestResponses = transferRequestResponses;
    }

    @Override
    public String toString() {
        return "ListTransferRequestResponse{" +
                "transferRequestResponses=" + transferRequestResponses +
                '}';
    }
}
