package com.xwings.coin.station.rpc.response;

import java.util.List;

/**
 * Created by ajax.wang on 12/20/2018.
 */
public class ListWalletAddressesResponse extends Response {
    private List<WalletAddressResponse> addresses;

    public ListWalletAddressesResponse() {
    }

    public ListWalletAddressesResponse(List<WalletAddressResponse> addresses) {
        this.addresses = addresses;
    }

    public List<WalletAddressResponse> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<WalletAddressResponse> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "ListWalletAddressesResponse{" +
                "addresses=" + addresses +
                '}';
    }
}