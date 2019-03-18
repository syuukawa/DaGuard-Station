package com.xwings.coin.station.rpc.request;

import com.xwings.coin.station.rpc.response.SuccessResponse;

public class DeleteWebhookRequest implements Request<SuccessResponse> {
    private String type;
    private String url;

    public DeleteWebhookRequest() {
    }

    public DeleteWebhookRequest(String type, String url) {
        this.type = type;
        this.url = url;
    }

    @Override
    public Class<SuccessResponse> getResponseType() {
        return SuccessResponse.class;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DeleteWebhookRequest{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
