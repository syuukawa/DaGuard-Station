package com.xwings.coin.station.rpc.response;

import java.util.List;

public class ListCoinResponse extends Response {
    private List<CoinResponse> coinResponses;

    public List<CoinResponse> getCoinResponses() {
        return coinResponses;
    }

    public void setCoinResponses(List<CoinResponse> coinResponses) {
        this.coinResponses = coinResponses;
    }

    @Override
    public String toString() {
        return "ListCoinResponse{" +
                "coinResponses=" + coinResponses +
                '}';
    }
}
