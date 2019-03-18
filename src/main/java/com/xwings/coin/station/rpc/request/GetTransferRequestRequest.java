package com.xwings.coin.station.rpc.request;

import com.xwings.coin.station.rpc.response.TransferRequest;

public class GetTransferRequestRequest implements Request<TransferRequest> {
    private String transferRequestId;

    @Override
    public Class<TransferRequest> getResponseType() {
        return TransferRequest.class;
    }

    public String getTransferRequestId() {
        return transferRequestId;
    }

    public void setTransferRequestId(String transferRequestId) {
        this.transferRequestId = transferRequestId;
    }

    @Override
    public String toString() {
        return "GetTransferRequestRequest{" +
                "transferRequestId='" + transferRequestId + '\'' +
                '}';
    }
}
