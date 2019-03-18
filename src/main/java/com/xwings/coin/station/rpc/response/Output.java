package com.xwings.coin.station.rpc.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Output {
    private BigInteger value;
    private int n;
    private ScriptPubKey scriptPubKey;

    @JsonIgnore
    public boolean isEffective() {
        return BigInteger.ZERO.compareTo(value) != 0 && !CollectionUtils.isEmpty(scriptPubKey.getAddresses());
    }

    @JsonIgnore
    public String getAddress() {
        return scriptPubKey.getAddresses().get(0);
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public ScriptPubKey getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(ScriptPubKey scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }

    @Override
    public String toString() {
        return "Output{" +
                "value=" + value +
                ", n=" + n +
                ", scriptPubKey=" + scriptPubKey +
                '}';
    }
}
