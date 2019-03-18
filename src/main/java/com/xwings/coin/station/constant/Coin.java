package com.xwings.coin.station.constant;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Coin {

    BTC(CoinType.CRYPTO, 8),
    ETH(CoinType.CRYPTO, 18),
    USDT(CoinType.CRYPTO, 8),
    XRP(CoinType.CRYPTO, 6),
    XLM(CoinType.CRYPTO, 7),
    EOS(CoinType.CRYPTO, 4),
    USDC(CoinType.ETHEREUM_TOKEN, "0xbd2e17d7cbab6cdfed0d52d9676088b6059b1c20", 6);

    private static final Map<String, Coin> COINS = Arrays.asList(Coin.values()).stream()
            .collect(Collectors.toMap(Coin::name, coin -> coin));

    private final CoinType coinType;
    private final String contractAddress;
    private final int decimals;

    Coin(CoinType coinType, int decimals) {
        this.coinType = coinType;
        this.contractAddress = null;
        this.decimals = decimals;
    }

    Coin(CoinType coinType, String contractAddress, int decimals) {
        this.coinType = coinType;
        this.contractAddress = contractAddress;
        this.decimals = decimals;
    }

    public static final boolean isExist(String coin) {
        Assert.hasLength(coin, "Null Coin is unacceptable");

        return COINS.containsKey(coin);
    }

    public final Coin keyChainCoin() {
        if (this == Coin.USDT) {
            return Coin.BTC;
        }

        if (coinType == CoinType.ETHEREUM_TOKEN) {
            return Coin.ETH;
        }

        return this;
    }

    public boolean isSupportedAmount(String amount) {
        return NumberUtils.isParsable(amount) && new BigDecimal(amount).scale() <= this.decimals();
    }

    public CoinType coinType() {
        return coinType;
    }

    public int decimals() {
        return decimals;
    }

    public String contractAddress() {
        return contractAddress;
    }

}
