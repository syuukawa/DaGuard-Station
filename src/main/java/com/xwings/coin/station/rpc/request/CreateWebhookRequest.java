package com.xwings.coin.station.rpc.request;

import com.xwings.coin.station.rpc.response.WebHookResponse;

public class CreateWebhookRequest implements Request<WebHookResponse> {
    private String type;
    private String url;

    public CreateWebhookRequest() {
    }

    public CreateWebhookRequest(String type, String url) {
        this.type = type;
        this.url = url;
    }

    @Override
    public Class<WebHookResponse> getResponseType() {
        return WebHookResponse.class;
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
        return "CreateWebhookRequest{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
