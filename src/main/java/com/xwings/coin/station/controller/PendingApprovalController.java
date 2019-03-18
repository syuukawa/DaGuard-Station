package com.xwings.coin.station.controller;

import com.xwings.coin.station.rpc.DaGuardClient;
import com.xwings.coin.station.rpc.response.TransferRequestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Pending Approvals")
public class PendingApprovalController extends BaseController {

    @PostMapping(RestEndpoints.APPROVE_PENDING_APPROVAL)
    @ApiOperation(value = "Approve Pending Approval Transfer Request")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "transferRequestId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path")
    })
    public TransferRequestResponse approvePendingApprovals() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.post(daGuardClient, getServletPath(), TransferRequestResponse.class);
    }

    @PostMapping(RestEndpoints.REJECT_PENDING_APPROVAL)
    @ApiOperation(value = "Reject Pending Approval Transfer Request")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "transferRequestId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path")
    })
    public TransferRequestResponse rejectPendingApprovals() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.post(daGuardClient, getServletPath(), TransferRequestResponse.class);
    }

}
