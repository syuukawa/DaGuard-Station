package com.xwings.coin.station.controller.dto;

public class DeleteWebhook {
    private String type;
    private String url;

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
        return "DeleteWebhook{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
