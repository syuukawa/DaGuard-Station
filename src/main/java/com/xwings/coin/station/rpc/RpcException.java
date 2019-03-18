package com.xwings.coin.station.rpc;

/**
 * Created by ajax.wang on 8/27/2018.
 */
public class RpcException extends Exception {
    private static final long serialVersionUID = 201803161L;

    private String errorCode;
    private String errorMessage;

    public RpcException() {
        super();
    }

    public RpcException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public RpcException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "RpcException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
