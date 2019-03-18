package com.xwings.coin.station.rpc.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entry {
    private String address;
    private String wallet;
    private BigInteger value;
    private String valueString;
    private boolean isPayDaguard;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public boolean isPayDaguard() {
        return isPayDaguard;
    }

    public void setPayDaguard(boolean payDaguard) {
        isPayDaguard = payDaguard;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "address='" + address + '\'' +
                ", wallet='" + wallet + '\'' +
                ", value=" + value +
                ", valueString='" + valueString + '\'' +
                ", isPayDaguard=" + isPayDaguard +
                '}';
    }
}
