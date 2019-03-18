package com.xwings.coin.station.controller.dto;

import java.util.List;

/**
 * Created by zihao.long on 12/19/2018.
 */
public class CreatePolicyRule {
    private String type;
    private List<PolicyRuleCondition> conditions;
    private String action;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PolicyRuleCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<PolicyRuleCondition> conditions) {
        this.conditions = conditions;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
