package com.xwings.coin.station.rpc.response;

public class TransferRequest extends Response {
    private String transferRequestId;
    private String coin;
    private String walletId;
    private String toAddress;
    private String amount;
    private String comment;
    private String transferState;
    private Integer approvalsRequired;
    private Long createdTime;
    private String createdBy;
    private String transferId;
    private String txid;
    private String txData;

    public String getTransferRequestId() {
        return transferRequestId;
    }

    public void setTransferRequestId(String transferRequestId) {
        this.transferRequestId = transferRequestId;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getApprovalsRequired() {
        return approvalsRequired;
    }

    public void setApprovalsRequired(Integer approvalsRequired) {
        this.approvalsRequired = approvalsRequired;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getTxData() {
        return txData;
    }

    public void setTxData(String txData) {
        this.txData = txData;
    }

    public String getTransferState() {
        return transferState;
    }

    public void setTransferState(String transferState) {
        this.transferState = transferState;
    }

    @Override
    public String toString() {
        return "TransferRequest{" +
                "transferRequestId='" + transferRequestId + '\'' +
                ", coin='" + coin + '\'' +
                ", walletId='" + walletId + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", amount='" + amount + '\'' +
                ", comment='" + comment + '\'' +
                ", transferState='" + transferState + '\'' +
                ", approvalsRequired=" + approvalsRequired +
                ", createdTime=" + createdTime +
                ", createdBy='" + createdBy + '\'' +
                ", transferId='" + transferId + '\'' +
                ", txid='" + txid + '\'' +
                ", txData='" + txData + '\'' +
                '}';
    }
}
