package com.xwings.coin.station.rpc.response;


/**
 * Created by ajax.wang on 12/27/2018.
 */
public class SuccessResponse extends Response {
    private Boolean success;

    public SuccessResponse() {
    }

    public SuccessResponse(boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "SuccessResponse{" +
                "success=" + success +
                '}';
    }
}
