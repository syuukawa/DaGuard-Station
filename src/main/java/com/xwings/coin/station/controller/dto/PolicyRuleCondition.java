package com.xwings.coin.station.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyRuleCondition {
    private String amount;
    private String timeWindow;
    private List<String> addresses;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(String timeWindow) {
        this.timeWindow = timeWindow;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public List<String> getAddresses() {
        return addresses;
    }
}
