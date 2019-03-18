package com.xwings.coin.station.service.cosign;

import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.constant.NetworkMode;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by zihao.long on 12/14/2018.
 */
@Service
public class CosignHelper {

    @Value("${networkMode}")
    private NetworkMode networkMode;

    @Autowired
    private List<CoinCosigner> coinCosigners;

    public String cosign(String coin, String privateKey, String txData) {
        Coin coinEnum = EnumUtils.getEnum(Coin.class, coin);
        Assert.notNull(coinEnum, "Invalid coin");

        final CoinCosigner coinCosigner = getCosigner(coinEnum);

        try {
            return coinCosigner.cosign(privateKey, null, txData, networkMode);
        } catch (Exception e) {
            throw new IllegalArgumentException("wallet passphrase mismatch", e);
        }
    }

    public void validatePubKey(Coin coin, String pubKey) {
        final CoinCosigner coinCosigner = getCosigner(coin);

        Assert.isTrue(coinCosigner.validatePubKey(pubKey), String.format("Invalid Public Key: %s", pubKey));
    }

    private CoinCosigner getCosigner(Coin coin) {
        Objects.requireNonNull(coin, "coin cannot be null!");

        Optional<CoinCosigner> optional = coinCosigners.stream().filter(coinService -> coinService.getCoin() == coin).findFirst();
        return optional.orElseThrow(
                () -> new IllegalArgumentException("Failed to get Cosign Service with coin: " + coin));
    }
}
