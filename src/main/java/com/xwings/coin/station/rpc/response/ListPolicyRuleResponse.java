package com.xwings.coin.station.rpc.response;

import java.util.List;

/**
 * Created by zihao.long on 12/19/2018.
 */
public class ListPolicyRuleResponse extends Response {

    private List<PolicyRuleResponse> policyRules;

    public ListPolicyRuleResponse() {
    }

    public ListPolicyRuleResponse(List<PolicyRuleResponse> policyRules) {
        this.policyRules = policyRules;
    }

    public List<PolicyRuleResponse> getPolicyRules() {
        return policyRules;
    }

    public void setPolicyRules(List<PolicyRuleResponse> policyRules) {
        this.policyRules = policyRules;
    }
}
