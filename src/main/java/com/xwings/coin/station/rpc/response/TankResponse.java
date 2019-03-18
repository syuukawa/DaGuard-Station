package com.xwings.coin.station.rpc.response;

public class TankResponse extends Response {
    private String coin;
    private String address;
    private String balance;
    private String minBalance;
    private String maxBalance;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(String minBalance) {
        this.minBalance = minBalance;
    }

    public String getMaxBalance() {
        return maxBalance;
    }

    public void setMaxBalance(String maxBalance) {
        this.maxBalance = maxBalance;
    }

    @Override
    public String toString() {
        return "TankResponse{" +
                "coin='" + coin + '\'' +
                ", address='" + address + '\'' +
                ", balance='" + balance + '\'' +
                ", minBalance='" + minBalance + '\'' +
                ", maxBalance='" + maxBalance + '\'' +
                '}';
    }
}
