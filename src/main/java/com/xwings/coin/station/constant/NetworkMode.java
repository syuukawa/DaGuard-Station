package com.xwings.coin.station.constant;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;

/**
 * Created by ajax.wang on 11/26/2018.
 */
public enum NetworkMode {
    MAINNET,
    TESTNET,
    REGTEST;

    public NetworkParameters toNetworkParameters() {
        if (this == NetworkMode.TESTNET) {
            return TestNet3Params.get();
        }

        if (this == NetworkMode.REGTEST) {
            return RegTestParams.get();
        }

        return MainNetParams.get();
    }

}
