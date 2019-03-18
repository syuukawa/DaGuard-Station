package com.xwings.coin.station.rpc.response;

public class WebHookResponse extends Response {
    private String walletId;
    private String type;
    private String url;
    private Integer version;
    private Long createdTime;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "WebHookResponse{" +
                "walletId='" + walletId + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", version=" + version +
                ", createdTime=" + createdTime +
                '}';
    }
}
