package com.xwings.coin.station.service.cosign;

import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.constant.NetworkMode;

import java.io.IOException;

/**
 * Created by ajax.wang on 11/13/2018.
 */
public interface CoinCosigner {

    Coin getCoin();

    String cosign(String walletPrivateKey, String tankPrivateKey, String encodedTxData, NetworkMode networkMode) throws IOException;

    boolean validatePubKey(String pubKey);

}
