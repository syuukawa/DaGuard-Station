package com.xwings.coin.station.rpc.request;


import com.xwings.coin.station.rpc.response.SuccessResponse;

/**
 * Created by zihao.long on 12/27/2018.
 */
public class DeletePolicyRuleRequest implements Request<SuccessResponse> {
    private String address;

    public DeletePolicyRuleRequest() {
    }

    public DeletePolicyRuleRequest(String address) {
        this.address = address;
    }

    @Override
    public Class<SuccessResponse> getResponseType() {
        return SuccessResponse.class;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
