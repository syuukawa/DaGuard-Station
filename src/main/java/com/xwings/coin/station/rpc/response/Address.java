package com.xwings.coin.station.rpc.response;

/**
 * Created by zihao.long on 12/4/2018.
 */
public class Address extends Response {
    private String label;
    private String address;
    private String coin;
    private String walletId;
    private Long createdTime;
    private String coinSpecific;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getCoinSpecific() {
        return coinSpecific;
    }

    public void setCoinSpecific(String coinSpecific) {
        this.coinSpecific = coinSpecific;
    }

    @Override
    public String toString() {
        return "Address{" +
                "label='" + label + '\'' +
                ", address='" + address + '\'' +
                ", coin='" + coin + '\'' +
                ", walletId='" + walletId + '\'' +
                ", createdTime=" + createdTime +
                ", coinSpecific='" + coinSpecific + '\'' +
                '}';
    }
}
