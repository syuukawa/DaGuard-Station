package com.xwings.coin.station.rpc.response;

/**
 * Created by zihao.long on 12/4/2018.
 */
public enum TransferState {
    CONFIRMED,
    UNCONFIRMED,
    PENDING_APPROVAL,
    REJECTED;

    public static TransferState getState(int confirmations, int targetConfirmations) {
        if (confirmations >= targetConfirmations) {
            return CONFIRMED;
        }

        return UNCONFIRMED;
    }
}
