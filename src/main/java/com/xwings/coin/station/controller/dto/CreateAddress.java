package com.xwings.coin.station.controller.dto;


import javax.validation.constraints.NotBlank;

/**
 * Created by zihao.long on 12/4/2018.
 */
public class CreateAddress {
    @NotBlank
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "CreateAddressRequest{" +
                "label='" + label + '\'' +
                '}';
    }
}
