package com.xwings.coin.station.controller;

import com.xwings.coin.station.constant.ErrorCode;
import com.xwings.coin.station.rpc.RpcException;
import com.xwings.coin.station.rpc.response.Response;
import com.xwings.coin.station.service.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by zihao.long on 12/19/2018.
 */
@RestControllerAdvice
class RestExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = {RestClientException.class})
    public Response restClientException(RestClientException e, WebRequest req) {
        LOG.error("WebRequest: {}", req, e);

        throw e;
    }

    @ExceptionHandler(value = {RpcException.class})
    @ResponseStatus(HttpStatus.OK)
    public Response unknownException(RpcException e, WebRequest req) {
        LOG.error("WebRequest: {}", req, e);

        return new Response(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.OK)
    public Response unknownException(ValidationException e, WebRequest req) {
        LOG.error("WebRequest: {}", req, e);

        return new Response(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public Response unknownException(Exception e, WebRequest req) {
        LOG.error("WebRequest: {}", req, e);

        return new Response(ErrorCode.INTERNAL_SERVER_ERROR.name(), e.getMessage());
    }

}
