package com.xwings.coin.station.rpc.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigInteger;

/**
 * Created by ajax.wang on 11/16/2018.
 */
public class UTXO extends Response {
    private String address;
    private String txid;
    private int vout;
    @JsonIgnore
    private BigInteger amount;
    @JsonIgnore
    private BigInteger omniAmount;
    private String redeemScript;
    private boolean tank;
    private int sequence;

    public UTXO() {
    }

    public UTXO(String address, String redeemScript, String txid, int vout, BigInteger amount) {
        this(address, redeemScript, txid, vout, amount, null, false);
    }

    public UTXO(String address, String redeemScript, String txid, int vout, BigInteger amount, BigInteger omniAmount) {
        this(address, redeemScript, txid, vout, amount, omniAmount, false);
    }

    public UTXO(String address, String redeemScript, String txid, int vout, BigInteger amount, BigInteger omniAmount, boolean tank) {
        this.address = address;
        this.redeemScript = redeemScript;
        this.txid = txid;
        this.vout = vout;
        this.amount = amount;
        this.omniAmount = omniAmount;
        this.tank = tank;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getRedeemScript() {
        return redeemScript;
    }

    public void setRedeemScript(String redeemScript) {
        this.redeemScript = redeemScript;
    }

    public BigInteger getOmniAmount() {
        return omniAmount;
    }

    public void setOmniAmount(BigInteger omniAmount) {
        this.omniAmount = omniAmount;
    }

    public boolean isTank() {
        return tank;
    }

    public void setTank(boolean tank) {
        this.tank = tank;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
