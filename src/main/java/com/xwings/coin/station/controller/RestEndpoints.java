package com.xwings.coin.station.controller;

public class RestEndpoints {
    public static final String V_COIN = "coin";
    public static final String V_WALLET_ID = "walletId";
    public static final String V_TRANSFER_ID = "transferId";
    public static final String V_ADDRESS = "address";
    public static final String V_TRANSFER_REQUEST_ID = "transferRequestId";
    public static final String V_WEB_HOOK = "webhooks";
    public static final String V_ORGANIZATION = "organizations";
    public static final String V_ORGANIZATION_ID = "organizationId";
    public static final String V_KEY_ID = "keyId";

    public static final String API_PREFIX = "/api";

    public static final String LIST_COINS = "/coins";
    public static final String GET_COIN = "/coins/{" + V_COIN + "}";

    public static final String LIST_WALLETS = "/{" + V_COIN + "}/wallets";
    public static final String CREATE_WALLETS = "/wallets";
    public static final String GET_WALLET = LIST_WALLETS + "/{" + V_WALLET_ID + "}";

    public static final String LIST_TRANSFERS = GET_WALLET + "/transfers";
    public static final String GET_TRANSFER = GET_WALLET + "/transfers/{" + V_TRANSFER_ID + "}";

    public static final String LIST_ADDRESSES = GET_WALLET + "/addresses";
    public static final String CREATE_ADDRESS = GET_WALLET + "/addresses";
    public static final String GET_ADDRESS = GET_WALLET + "/addresses/{" + V_ADDRESS + "}";

    public static final String CREATE_TRANSFER_REQUEST = GET_WALLET + "/transferRequests";
    public static final String GET_TRANSFER_REQUEST = GET_WALLET + "/transferRequests/{" + V_TRANSFER_REQUEST_ID + "}";
    public static final String LIST_TRANSFER_REQUESTS = GET_WALLET + "/transferRequests";
    public static final String APPROVE_PENDING_APPROVAL = "/pendingApprovals/{" + V_TRANSFER_REQUEST_ID + "}/approve";
    public static final String REJECT_PENDING_APPROVAL = "/pendingApprovals/{" + V_TRANSFER_REQUEST_ID + "}/reject";

    public static final String CREATE_WEB_HOOK = "/wallets/{" + V_WALLET_ID + "}/" + V_WEB_HOOK;
    public static final String LIST_WEB_HOOKS = "/wallets/{" + V_WALLET_ID + "}/" + V_WEB_HOOK;
    public static final String DELETE_WEB_HOOK = "/wallets/{" + V_WALLET_ID + "}/" + V_WEB_HOOK;
    public static final String GET_WEB_HOOK = "/wallets/{" + V_WALLET_ID + "}/" + V_WEB_HOOK + "/{webHookId}";

    public static final String GET_POLICY_RULE = "/{" + V_COIN + "}/wallets/{" + V_WALLET_ID + "}/policyRule";
    public static final String CREATE_POLICY_RULE = "/{" + V_COIN + "}/wallets/{" + V_WALLET_ID + "}/policyRule";

    public static final String GET_KEY_CHAIN = "/{" + V_COIN + "}/key/{" + V_KEY_ID + "}";
    public static final String ORGANIZATION_TANKS = "/" + V_ORGANIZATION + "/{" + V_ORGANIZATION_ID + "}/tanks";

}
