package com.xwings.coin.station.rpc.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScriptSig {
    private String asm;
    private String hex;

    public String getAsm() {
        return asm;
    }

    public void setAsm(String asm) {
        this.asm = asm;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    @Override
    public String toString() {
        return "ScriptSig{" +
                "asm='" + asm + '\'' +
                ", hex='" + hex + '\'' +
                '}';
    }
}
