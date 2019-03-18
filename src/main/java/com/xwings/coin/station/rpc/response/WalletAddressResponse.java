package com.xwings.coin.station.rpc.response;


/**
 * Created by zihao.long on 12/4/2018.
 */
public class WalletAddressResponse extends Response {

    private String label;

    private String address;

    private String coin;

    private String walletId;

    private Long createdTime;

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

    @Override
    public String toString() {
        return "WalletAddressResponse{" +
                "label='" + label + '\'' +
                ", address='" + address + '\'' +
                ", coin='" + coin + '\'' +
                ", walletId='" + walletId + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
