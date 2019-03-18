package com.xwings.coin.station.service.cosign;

import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.constant.NetworkMode;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CosignHelperTest {
    private static final CosignHelper COSIGN_HELPER = new CosignHelper();
    private static final List<CoinCosigner> COIN_COSIGNERS = new ArrayList<>();

    private static final BTCCosigner BTC_COSIGNER = new BTCCosigner();
    private static final EOSCosigner EOS_COSIGNER = new EOSCosigner();
    private static final ETHCosigner ETH_COSIGNER = new ETHCosigner();
    private static final XRPCosigner XRP_COSIGNER = new XRPCosigner();
    private static final XLMCosigner XLM_COSIGNER = new XLMCosigner();

    @BeforeClass
    public static void setup() {
        ReflectionTestUtils.setField(BTC_COSIGNER, "networkMode", NetworkMode.TESTNET);
        COIN_COSIGNERS.addAll(Arrays.asList(BTC_COSIGNER, EOS_COSIGNER, ETH_COSIGNER, XRP_COSIGNER, XLM_COSIGNER));
        ReflectionTestUtils.setField(COSIGN_HELPER, "coinCosigners", COIN_COSIGNERS);
    }

    @Test
    public void validateBTCPubKey() {
        COSIGN_HELPER.validatePubKey(Coin.BTC, "03e1007afe724a26789d12ce33b71e0ade2ffaac640e5b46ccbb205dfa26fa9813");
    }

    @Test
    public void validateETHPubKey() {
        COSIGN_HELPER.validatePubKey(Coin.ETH, "0xa11d0f79df9f68da72a1d9f8c129eac0a07c4790");
    }

    @Test
    public void validateEOSPubKey() {
        COSIGN_HELPER.validatePubKey(Coin.EOS, "EOS6LcQaP5YWUircvfQAZcq3uX7CiJP5yAkDekarxx1kRcfzge2hn");
    }

    @Test
    public void validateXRPPubKey() {
        COSIGN_HELPER.validatePubKey(Coin.XRP, "ra5PKjnENTkYXH2BZ1VeNjQnU6aKrsobTs");
    }

    @Ignore
    @Test
    public void validateXLMPubKey() {
        COSIGN_HELPER.validatePubKey(Coin.XLM, "ms3dPbteuupS8Wov6GndAq8gwF7kJBNTpz");
    }

}