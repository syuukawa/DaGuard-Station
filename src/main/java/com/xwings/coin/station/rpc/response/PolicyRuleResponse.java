package com.xwings.coin.station.rpc.response;


/**
 * Created by zihao.long on 12/19/2018.
 */
public class PolicyRuleResponse extends Response {

    private String id;
    private String walletPolicyId;
    private String coin;
    private String action;
    private String condition;
    private String policyRuleType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWalletPolicyId() {
        return walletPolicyId;
    }

    public void setWalletPolicyId(String walletPolicyId) {
        this.walletPolicyId = walletPolicyId;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPolicyRuleType() {
        return policyRuleType;
    }

    public void setPolicyRuleType(String policyRuleType) {
        this.policyRuleType = policyRuleType;
    }
}
