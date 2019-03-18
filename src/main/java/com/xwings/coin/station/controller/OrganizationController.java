package com.xwings.coin.station.controller;

import com.xwings.coin.station.rpc.DaGuardClient;
import com.xwings.coin.station.rpc.response.ListTanksResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@ApiIgnore
@RestController
public class OrganizationController extends BaseController {

    @GetMapping(RestEndpoints.ORGANIZATION_TANKS)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "organizationId", value = "0552d5614b0a4748992e7119d98f4d9a", required = true, dataType = "String", paramType = "path")
    })
    public ListTanksResponse getTanks() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getServletPath(), ListTanksResponse.class);
    }

}
