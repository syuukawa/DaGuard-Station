package com.xwings.coin.station.rpc.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigInteger;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Input {
    private String txid;
    private int vout;
    private ScriptSig scriptSig;
    private List<String> txinwitness;
    private BigInteger sequence;

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public int getVout() {
        return vout;
    }

    public void setVout(int vout) {
        this.vout = vout;
    }

    public ScriptSig getScriptSig() {
        return scriptSig;
    }

    public void setScriptSig(ScriptSig scriptSig) {
        this.scriptSig = scriptSig;
    }

    public List<String> getTxinwitness() {
        return txinwitness;
    }

    public void setTxinwitness(List<String> txinwitness) {
        this.txinwitness = txinwitness;
    }

    public BigInteger getSequence() {
        return sequence;
    }

    public void setSequence(BigInteger sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "Input{" +
                "txid='" + txid + '\'' +
                ", vout=" + vout +
                ", scriptSig=" + scriptSig +
                ", txinwitness=" + txinwitness +
                ", sequence=" + sequence +
                '}';
    }
}
