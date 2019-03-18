package com.xwings.coin.station.service.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ajax.wang on 11/13/2018.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyEntry {
    private int version;
    private String id;
    @JsonProperty("pub")
    private String pub;
    private KeyCrypto crypto;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public KeyCrypto getCrypto() {
        return crypto;
    }

    public void setCrypto(KeyCrypto crypto) {
        this.crypto = crypto;
    }

}
