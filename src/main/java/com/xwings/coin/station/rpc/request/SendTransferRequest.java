package com.xwings.coin.station.rpc.request;

import com.xwings.coin.station.rpc.response.TransferRequestResponse;

import javax.validation.constraints.NotBlank;

public class SendTransferRequest implements Request<TransferRequestResponse> {
    @NotBlank
    private String txData;

    public SendTransferRequest() {
    }

    public SendTransferRequest(String txData) {
        this.txData = txData;
    }

    public String getTxData() {
        return txData;
    }

    public void setTxData(String txData) {
        this.txData = txData;
    }

    @Override
    public Class<TransferRequestResponse> getResponseType() {
        return TransferRequestResponse.class;
    }
}
