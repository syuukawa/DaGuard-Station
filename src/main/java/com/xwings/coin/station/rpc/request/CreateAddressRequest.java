package com.xwings.coin.station.rpc.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xwings.coin.station.rpc.response.WalletAddressResponse;

public class CreateAddressRequest implements Request<WalletAddressResponse> {
    private String label;

    public CreateAddressRequest(String label) {
        this.label = label;
    }

    @JsonIgnore
    @Override
    public Class<WalletAddressResponse> getResponseType() {
        return WalletAddressResponse.class;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
