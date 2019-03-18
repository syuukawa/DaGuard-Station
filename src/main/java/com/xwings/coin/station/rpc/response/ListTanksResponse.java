package com.xwings.coin.station.rpc.response;

import java.util.List;

/**
 * Created by ajax.wang on 12/20/2018.
 */
public class ListTanksResponse extends Response {
    private List<TankResponse> tanks;

    public ListTanksResponse() {
    }

    public ListTanksResponse(List<TankResponse> tanks) {
        this.tanks = tanks;
    }

    public List<TankResponse> getTanks() {
        return tanks;
    }

    public void setTanks(List<TankResponse> tanks) {
        this.tanks = tanks;
    }

    @Override
    public String toString() {
        return "ListTanksResponse{" +
                "tanks=" + tanks +
                '}';
    }
}
