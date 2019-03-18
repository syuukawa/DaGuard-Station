package com.xwings.coin.station.rpc.response;

/**
 * Created by zihao.long on 12/12/2018.
 */
public class SignedTransaction extends Response {

    private String txId;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }
}
