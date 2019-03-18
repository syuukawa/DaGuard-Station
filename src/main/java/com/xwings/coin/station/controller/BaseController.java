package com.xwings.coin.station.controller;

import com.xwings.coin.station.rpc.DaGuardClient;
import com.xwings.coin.station.constant.NetworkMode;
import com.xwings.coin.station.service.APIForwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(RestEndpoints.API_PREFIX)
class BaseController {
    private static final String API_KEY = "API-KEY";
    private static final String API_KEY_SECRET = "API-KEY-SECRET";
    private static final String API_KEY_PASSPHRASE = "API-KEY-PASSPHRASE";

    @Value("${daguard.url}")
    protected String url;

    @Value("${networkMode}")
    protected NetworkMode networkMode;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    protected HttpServletRequest httpServletRequest;

    @Autowired
    protected APIForwardService apiForwardService;

    protected String getIpAddress(HttpServletRequest httpServletRequest) {
        final String ipAddress = httpServletRequest.getHeader("x-real-ip");

        if (ipAddress == null) {
            return httpServletRequest.getRemoteAddr();
        }

        return ipAddress;
    }

    protected String getServletPath() {
        return httpServletRequest.getServletPath();
    }

    protected String getRequestURLWithParams() {
        final String queryString = httpServletRequest.getQueryString();

        if (queryString == null) {
            return getServletPath();
        }

        final StringBuilder requestURLBuilder = new StringBuilder(getServletPath());

        return requestURLBuilder.append("?").append(queryString).toString();
    }

    protected DaGuardClient getClient() {
        final String apiKey = httpServletRequest.getHeader(API_KEY);
        final String apiKeySecret = httpServletRequest.getHeader(API_KEY_SECRET);
        final String apiKeyPassphrase = httpServletRequest.getHeader(API_KEY_PASSPHRASE);
        final String ipAddress = getIpAddress(httpServletRequest);

        return new DaGuardClient(url, apiKey, apiKeySecret, apiKeyPassphrase, ipAddress, restTemplate);
    }

}
