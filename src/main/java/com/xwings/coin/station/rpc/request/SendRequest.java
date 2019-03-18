package com.xwings.coin.station.rpc.request;

import com.xwings.coin.station.rpc.response.TransferData;

/**
 * Created by zihao.long on 12/19/2018.
 */
public class SendRequest implements Request<TransferData> {
    private String coin;
    private String walletId;
    private String walletPassphrase;
    private String address;
    private String amount;
    private String apiKey;
    private String apiKeyPassphrase;
    private String apiKeySecret;

    @Override
    public Class<TransferData> getResponseType() {
        return TransferData.class;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getWalletPassphrase() {
        return walletPassphrase;
    }

    public void setWalletPassphrase(String walletPassphrase) {
        this.walletPassphrase = walletPassphrase;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKeyPassphrase() {
        return apiKeyPassphrase;
    }

    public void setApiKeyPassphrase(String apiKeyPassphrase) {
        this.apiKeyPassphrase = apiKeyPassphrase;
    }

    public String getApiKeySecret() {
        return apiKeySecret;
    }

    public void setApiKeySecret(String apiKeySecret) {
        this.apiKeySecret = apiKeySecret;
    }

}
