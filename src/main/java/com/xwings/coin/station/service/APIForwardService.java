package com.xwings.coin.station.service;

import com.xwings.coin.station.rpc.DaGuardClient;
import com.xwings.coin.station.rpc.RpcException;
import com.xwings.coin.station.rpc.request.Request;
import com.xwings.coin.station.rpc.response.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class APIForwardService {

    public <T extends Response> T post(DaGuardClient daGuardClient, String url, Request<T> request) throws IOException, NoSuchAlgorithmException, InvalidKeyException, RpcException {

        return daGuardClient.post(url, request);
    }

    public <T extends Response> T post(DaGuardClient daGuardClient, String url, Class<T> responseType) throws IOException, NoSuchAlgorithmException, InvalidKeyException, RpcException {

        return daGuardClient.post(url, responseType);
    }

    public <T extends Response> T get(DaGuardClient daGuardClient, String url, Class<T> responseType) throws NoSuchAlgorithmException,
            InvalidKeyException, IOException, RpcException {

        return daGuardClient.get(url, responseType);
    }

    public <T extends Response> T delete(DaGuardClient daGuardClient, String url, Class<T> responseType) throws NoSuchAlgorithmException,
            InvalidKeyException, IOException, RpcException {

        return daGuardClient.delete(url, responseType);
    }

    public <T extends Response> T delete(DaGuardClient daGuardClient, String url, Request<T> request) throws IOException, NoSuchAlgorithmException, InvalidKeyException, RpcException {

        return daGuardClient.delete(url, request);
    }

}
