package com.xwings.coin.station.controller.dto;


import javax.validation.constraints.NotBlank;

/**
 * Created by ajax.wang on 12/12/2018.
 */
public class SendTransfer {
    @NotBlank
    private String txData;

    public SendTransfer() {
    }

    public SendTransfer(String txData) {
        this.txData = txData;
    }

    public String getTxData() {
        return txData;
    }

    public void setTxData(String txData) {
        this.txData = txData;
    }
}
