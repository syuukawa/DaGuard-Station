package com.xwings.coin.station.rpc.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinResponse extends Response {
    private String coin;
    private String coinType;
    private String contractAddress;
    private Integer decimals;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    @Override
    public String toString() {
        return "CoinResponse{" +
                "coin='" + coin + '\'' +
                ", coinType='" + coinType + '\'' +
                ", contractAddress='" + contractAddress + '\'' +
                ", decimals=" + decimals +
                '}';
    }
}
