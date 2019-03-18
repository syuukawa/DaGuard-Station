package com.xwings.coin.station.rpc.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xwings.coin.station.rpc.response.WalletResponse;

/**
 * Created by zihao.long on 12/12/2018.
 */
public class CreateWalletRequest implements Request<WalletResponse> {
    private String label;
    private String userKey;
    private String backupKey;
    private String passphraseEncryptionCode;

    @JsonIgnore
    @Override
    public Class<WalletResponse> getResponseType() {
        return WalletResponse.class;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getBackupKey() {
        return backupKey;
    }

    public void setBackupKey(String backupKey) {
        this.backupKey = backupKey;
    }

    public String getPassphraseEncryptionCode() {
        return passphraseEncryptionCode;
    }

    public void setPassphraseEncryptionCode(String passphraseEncryptionCode) {
        this.passphraseEncryptionCode = passphraseEncryptionCode;
    }
}
