package com.xwings.coin.station.rpc.response;

import java.util.List;

public class ListWebHooksResponse extends Response {
    private List<WebHookResponse> webHooks;

    public ListWebHooksResponse() {
    }

    public ListWebHooksResponse(List<WebHookResponse> webHooks) {
        this.webHooks = webHooks;
    }

    public List<WebHookResponse> getWebHooks() {
        return webHooks;
    }

    public void setWebHooks(List<WebHookResponse> webHooks) {
        this.webHooks = webHooks;
    }

    @Override
    public String toString() {
        return "ListWebHooksResponse{" +
                "webHooks=" + webHooks +
                '}';
    }
}
