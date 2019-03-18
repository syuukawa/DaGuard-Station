package com.xwings.coin.station.rpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.xwings.coin.station.controller.RestEndpoints;
import com.xwings.coin.station.rpc.request.Request;
import com.xwings.coin.station.rpc.response.Response;
import com.xwings.coin.station.util.ApiKeySignGenerator;
import com.xwings.coin.station.util.JsonUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DaGuardClient {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static final String API_KEY = "API-KEY";
    public static final String API_SIGN = "API-SIGN";
    public static final String API_TIMESTAMP = "API-TIMESTAMP";
    public static final String API_PASSCODE = "API-PASSCODE";
    public static final String X_REAL_IP = "x-real-ip";

    private String url;
    private String apiKey;
    private String apiKeyPassphrase;
    private String apiSecret;
    private String ipAddress;
    private RestTemplate restTemplate;

    public DaGuardClient(String url, String apiKey, String apiSecret, String apiKeyPassphrase, String ipAddress, RestTemplate restTemplate) {
        this.url = url;
        this.apiKey = apiKey;
        this.apiKeyPassphrase = apiKeyPassphrase;
        this.apiSecret = apiSecret;
        this.ipAddress = ipAddress;
        this.restTemplate = restTemplate;
    }

    private String getUrl(String apiUrl) {
        return this.url + getServletUrl(apiUrl);
    }

    protected String getSignaturePath(String url) {
        final String servletUrl = "/v1" + getServletUrl(url);

        if (servletUrl.contains("?")) {
            return servletUrl.substring(0, servletUrl.indexOf('?'));
        }

        return servletUrl;
    }

    private String getServletUrl(String url) {
        if (url.contains(RestEndpoints.API_PREFIX)) {
            return url;
        }

        return RestEndpoints.API_PREFIX + url;
    }

    public HttpHeaders getAuthHeader(String url, HttpMethod httpMethod) throws InvalidKeyException, NoSuchAlgorithmException {
        final String path = getSignaturePath(url);
        final long timestamp = System.currentTimeMillis();
        final String sign = ApiKeySignGenerator.generateSignature(
                Base64.encodeBase64String(apiSecret.getBytes()), timestamp, httpMethod.name(), path);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(API_KEY, apiKey);
        headers.set(API_SIGN, sign);
        headers.set(API_TIMESTAMP, String.valueOf(timestamp));
        headers.set(API_PASSCODE, apiKeyPassphrase);
        headers.set(X_REAL_IP, ipAddress);

        return headers;
    }

    public <T extends Response> T post(String url, Request<T> request) throws IOException, NoSuchAlgorithmException, InvalidKeyException, RpcException {
        final HttpHeaders headers = getAuthHeader(url, HttpMethod.POST);

        HttpEntity<String> requestEntity = new HttpEntity<String>(JsonUtils.convertToJson(request), headers);
        ResponseEntity<String> response = restTemplate.exchange(getUrl(url), HttpMethod.POST, requestEntity, String.class);

        return returnOrThrow(response.getBody(), request.getResponseType());
    }

    public <T extends Response> T post(String url, Class<T> responseType) throws IOException, NoSuchAlgorithmException, InvalidKeyException, RpcException {
        HttpHeaders headers = getAuthHeader(url, HttpMethod.POST);
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(getUrl(url), HttpMethod.POST, requestEntity, String.class);

        return returnOrThrow(response.getBody(), responseType);
    }

    public <T extends Response> T get(String url, Class<T> responseType) throws NoSuchAlgorithmException,
            InvalidKeyException, IOException, RpcException {
        HttpHeaders headers = getAuthHeader(url, HttpMethod.GET);
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(getUrl(url), HttpMethod.GET, requestEntity, String.class);
        return returnOrThrow(response.getBody(), responseType);
    }

    public <T extends Response> T delete(String url, Class<T> responseType) throws NoSuchAlgorithmException,
            InvalidKeyException, IOException, RpcException {
        final HttpHeaders headers = getAuthHeader(url, HttpMethod.DELETE);
        final HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        final ResponseEntity<String> response = restTemplate.exchange(getUrl(url), HttpMethod.DELETE, requestEntity, String.class);

        return returnOrThrow(response.getBody(), responseType);
    }

    public <T extends Response> T delete(String url, Request<T> request) throws IOException, NoSuchAlgorithmException, InvalidKeyException, RpcException {
        HttpHeaders headers = getAuthHeader(url, HttpMethod.DELETE);
        HttpEntity<String> requestEntity = new HttpEntity<String>(JsonUtils.convertToJson(request), headers);
        ResponseEntity<String> response = restTemplate.exchange(getUrl(url), HttpMethod.DELETE, requestEntity, String.class);

        return returnOrThrow(response.getBody(), request.getResponseType());
    }

    private <T extends Response> T returnOrThrow(String response, Class<T> responseType) throws IOException, RpcException {
        final T result = objectMapper.readValue(response, responseType);

        if (!Strings.isNullOrEmpty(result.getErrorCode()) && !Strings.isNullOrEmpty(result.getErrorMessage())) {
            throw new RpcException(result.getErrorCode(), result.getErrorMessage());
        }

        if (!Strings.isNullOrEmpty(result.getErrorCode())) {
            throw new RpcException(result.getErrorCode());
        }

        return result;
    }
}
