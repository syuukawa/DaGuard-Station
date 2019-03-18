package com.xwings.coin.station.rpc.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transfer extends Response {
    private String transferId;
    private String coin;
    private String walletId;
    private String txid;
    private Long createdTime;
    private Long lastModifiedTime;
    private Integer confirmations;
    private TransferState transferState;
    private Long confirmedTime;
    private String transferType;
    private String amount;
    private String fee;
    private String destinationAddress;
    private String sourceAddress;

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
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

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public TransferState getTransferState() {
        return transferState;
    }

    public void setTransferState(TransferState transferState) {
        this.transferState = transferState;
    }

    public Long getConfirmedTime() {
        return confirmedTime;
    }

    public void setConfirmedTime(Long confirmedTime) {
        this.confirmedTime = confirmedTime;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId='" + transferId + '\'' +
                ", coin='" + coin + '\'' +
                ", walletId='" + walletId + '\'' +
                ", txid='" + txid + '\'' +
                ", createdTime=" + createdTime +
                ", lastModifiedTime=" + lastModifiedTime +
                ", confirmations=" + confirmations +
                ", transferState=" + transferState +
                ", confirmedTime=" + confirmedTime +
                ", transferType='" + transferType + '\'' +
                ", amount='" + amount + '\'' +
                ", fee='" + fee + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", sourceAddress='" + sourceAddress + '\'' +
                '}';
    }
}