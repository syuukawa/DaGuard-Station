package com.xwings.coin.station.rpc.response;

import java.util.List;

public class ListWalletsResponse extends Response {
    private List<WalletResponse> wallets;

    public ListWalletsResponse() {
    }

    public ListWalletsResponse(List<WalletResponse> wallets) {
        this.wallets = wallets;
    }

    public List<WalletResponse> getWallets() {
        return wallets;
    }

    public void setWallets(List<WalletResponse> wallets) {
        this.wallets = wallets;
    }

    @Override
    public String toString() {
        return "{" +
                "wallets=" + wallets +
                '}';
    }
}
