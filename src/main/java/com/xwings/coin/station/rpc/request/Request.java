package com.xwings.coin.station.rpc.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by zihao.long on 12/12/2018.
 */
public interface Request<T> {

    @JsonIgnore
    Class<T> getResponseType();
}
