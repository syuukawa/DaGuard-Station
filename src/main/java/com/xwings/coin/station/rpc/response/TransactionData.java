package com.xwings.coin.station.rpc.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajax.wang on 11/16/2018.
 */
public class TransactionData extends Response {
    private String hex;
    private List<UTXO> vin;
    private Object json;
    private String address;
    private String outputs;
    // used by menero specifically
    private String keyImages;
    // used by ethereum specifically
    private Integer v1;
    private String r1;
    private String s1;
    private Integer v2;
    private String r2;
    private String s2;

    public TransactionData() {
    }

    // used by bitcoin specifically
    public TransactionData(String hex, List<UTXO> vin) {
        this.hex = hex;
        this.vin = vin;
    }

    public TransactionData(String hex, UTXO utxo) {
        this.hex = hex;
        this.vin = new ArrayList<>();
        this.vin.add(utxo);
    }

    // used by monero specifically
    public TransactionData(String address, String hex, String outputs) {
        this.address = address;
        this.hex = hex;
        this.outputs = outputs;
    }

    // used by ethereum specifically
    public TransactionData(String hex) {
        this.hex = hex;
    }

    public TransactionData(Object json) {
        this.json = json;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public List<UTXO> getVin() {
        return vin;
    }

    public void setVin(List<UTXO> vin) {
        this.vin = vin;
    }

    public Object getJson() {
        return json;
    }

    public void setJson(Object json) {
        this.json = json;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOutputs() {
        return outputs;
    }

    public void setOutputs(String outputs) {
        this.outputs = outputs;
    }

    public String getKeyImages() {
        return keyImages;
    }

    public void setKeyImages(String keyImages) {
        this.keyImages = keyImages;
    }

    public Integer getV1() {
        return v1;
    }

    public void setV1(Integer v1) {
        this.v1 = v1;
    }

    public String getR1() {
        return r1;
    }

    public void setR1(String r1) {
        this.r1 = r1;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public Integer getV2() {
        return v2;
    }

    public void setV2(Integer v2) {
        this.v2 = v2;
    }

    public String getR2() {
        return r2;
    }

    public void setR2(String r2) {
        this.r2 = r2;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }
}
