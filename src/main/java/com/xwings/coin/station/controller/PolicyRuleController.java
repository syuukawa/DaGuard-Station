package com.xwings.coin.station.controller;

import com.xwings.coin.station.rpc.DaGuardClient;
import com.xwings.coin.station.rpc.request.CreatePolicyRuleRequest;
import com.xwings.coin.station.rpc.response.ListPolicyRuleResponse;
import com.xwings.coin.station.rpc.response.PolicyRuleResponse;
import com.xwings.coin.station.controller.dto.CreatePolicyRule;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class PolicyRuleController extends BaseController {

    @PostMapping(RestEndpoints.CREATE_POLICY_RULE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "createPolicyRule", value = "{\n" +
                    "  \"type\": \"x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=\"\n" +
                    "  \"conditions\": \"{}\"\n" +
                    "  \"action\": \"action\"\n" +
                    "}", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "walletId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path")
    })
    public PolicyRuleResponse createPolicyRule(@RequestBody CreatePolicyRule createPolicyRule) throws Exception {
        final DaGuardClient daGuardClient = getClient();

        final CreatePolicyRuleRequest createPolicyRuleRequest = new CreatePolicyRuleRequest();
        createPolicyRuleRequest.setAction(createPolicyRule.getAction());
        createPolicyRuleRequest.setType(createPolicyRule.getType());
        createPolicyRuleRequest.setConditions(createPolicyRule.getConditions());

        return apiForwardService.post(daGuardClient, getServletPath(), createPolicyRuleRequest);
    }

    @GetMapping(RestEndpoints.GET_POLICY_RULE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "walletId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path")
    })
    public ListPolicyRuleResponse getPolicyRule() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getServletPath(), ListPolicyRuleResponse.class);
    }

}
