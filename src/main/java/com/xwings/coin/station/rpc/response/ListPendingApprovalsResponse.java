package com.xwings.coin.station.rpc.response;

import java.util.List;

/**
 * Created by zihao.long on 12/4/2018.
 */
public class ListPendingApprovalsResponse extends Response {

    private List<TransferRequestResponse> transferRequestResponses;

    public ListPendingApprovalsResponse() {
    }

    public ListPendingApprovalsResponse(List<TransferRequestResponse> transferResponses) {
        this.transferRequestResponses = transferResponses;
    }

    public List<TransferRequestResponse> getTransferRequestResponses() {
        return transferRequestResponses;
    }

    public void setTransferRequestResponses(List<TransferRequestResponse> transferRequestResponses) {
        this.transferRequestResponses = transferRequestResponses;
    }
}
