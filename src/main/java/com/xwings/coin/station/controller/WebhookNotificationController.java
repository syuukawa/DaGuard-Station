package com.xwings.coin.station.controller;

import com.xwings.coin.station.controller.dto.CreateWebhook;
import com.xwings.coin.station.controller.dto.DeleteWebhook;
import com.xwings.coin.station.rpc.DaGuardClient;
import com.xwings.coin.station.rpc.RpcException;
import com.xwings.coin.station.rpc.request.CreateWebhookRequest;
import com.xwings.coin.station.rpc.request.DeleteWebhookRequest;
import com.xwings.coin.station.rpc.response.ListWebHooksResponse;
import com.xwings.coin.station.rpc.response.SuccessResponse;
import com.xwings.coin.station.rpc.response.WebHookResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@Api(tags = "Webhook Notifications")
public class WebhookNotificationController extends BaseController {

    @PostMapping(RestEndpoints.CREATE_WEB_HOOK)
    @ApiOperation(value = "Create Webhook")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "walletId", value = "b18f529ddbcd41f4af26df6b3615413d", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "createWebhook", value = "{\"type\": \"PENDING_APPROVAL/TRANSFER\",\"url\": \"127.0.0.1\"}", required = true, dataType = "String", paramType = "body")
    })
    public WebHookResponse createWebHook(@RequestBody CreateWebhook createWebhook) throws IOException, GeneralSecurityException, RpcException {
        final DaGuardClient daGuardClient = getClient();

        final CreateWebhookRequest createWebhookRequest = new CreateWebhookRequest(createWebhook.getType(), createWebhook.getUrl());

        return apiForwardService.post(daGuardClient, getServletPath(), createWebhookRequest);
    }

    @DeleteMapping(RestEndpoints.DELETE_WEB_HOOK)
    @ApiOperation(value = "Delete Webhook")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "walletId", value = "b18f529ddbcd41f4af26df6b3615413d", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "deleteWebhook", value = "{\"type\": \"PENDING_APPROVAL/TRANSFER\",\"url\": \"127.0.0.1\"}",
                    required = true, dataTypeClass = DeleteWebhook.class, paramType = "body")
    })
    public SuccessResponse deleteWebHook(@RequestBody DeleteWebhook deleteWebhook) throws Exception {
        final DaGuardClient daGuardClient = getClient();

        final DeleteWebhookRequest deleteWebhookRequest = new DeleteWebhookRequest(deleteWebhook.getType(), deleteWebhook.getUrl());

        return apiForwardService.delete(daGuardClient, getServletPath(), deleteWebhookRequest);
    }

    @GetMapping(RestEndpoints.GET_WEB_HOOK)
    @ApiOperation(value = "Get Web Hook")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "walletId", value = "b18f529ddbcd41f4af26df6b3615413d", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "webHookId", value = "AAA", required = true, dataType = "String", paramType = "path")
    })
    public WebHookResponse getWebHook() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.delete(daGuardClient, getServletPath(), WebHookResponse.class);
    }

    @GetMapping(RestEndpoints.LIST_WEB_HOOKS)
    @ApiOperation(value = "Get Wallet All WebHooks")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "walletId", value = "b18f529ddbcd41f4af26df6b3615413d", required = true, dataType = "String", paramType = "path")
    })
    public ListWebHooksResponse listWebHooks() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.delete(daGuardClient, getServletPath(), ListWebHooksResponse.class);
    }

}
