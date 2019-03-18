package com.xwings.coin.station.service.cosign;

import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.constant.NetworkMode;
import com.xwings.coin.station.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;

/**
 * Created by ajax.wang on 11/27/2018.
 */
@Service
public class ETHCosigner implements CoinCosigner {
    private static final Logger LOG = LoggerFactory.getLogger(ETHCosigner.class);

    @Override
    public Coin getCoin() {
        return Coin.ETH;
    }

    @Override
    public String cosign(String walletPrivateKey, String tankPrivateKey, String encodedTxData, NetworkMode networkMode) throws IOException {
        // decode
        String json = new String(Base64.getDecoder().decode(encodedTxData));
        TransactionData txData = JsonUtils.parseAsObject(json, TransactionData.class);

        // cosign
        Credentials cosigner = Credentials.create(walletPrivateKey);
        byte[] unsignedMessageBytes = hashToAscii(Numeric.hexStringToByteArray(txData.getHex()));
        Sign.SignatureData signature = Sign.signPrefixedMessage(unsignedMessageBytes, cosigner.getEcKeyPair());
        if (txData.getV1() == null) {
            txData.setV1((int) signature.getV());
            txData.setR1(Numeric.toHexString(signature.getR()));
            txData.setS1(Numeric.toHexString(signature.getS()));
        } else {
            txData.setV2((int) signature.getV());
            txData.setR2(Numeric.toHexString(signature.getR()));
            txData.setS2(Numeric.toHexString(signature.getS()));
        }

        // encode and return
        String newJson = JsonUtils.convertToJson(txData);
        return Base64.getEncoder().encodeToString(newJson.getBytes());
    }

    @Override
    public boolean validatePubKey(String pubKey) {
        if (!pubKey.startsWith("0x") || pubKey.length() != 42) {
            LOG.error("Invalid ETH Public Key: {}", pubKey);

            return false;
        }

        try {
            final BigInteger number = new BigInteger(pubKey.substring(2), 16);
            return number.compareTo(BigInteger.ONE) > 0;
        } catch (NumberFormatException e) {
            LOG.error("Invalid ETH Public Key: {}", pubKey, e);
        }

        return false;
    }

    private byte[] hashToAscii(byte[] hash) {
        byte[] s = new byte[64];
        for (int i = 0; i < 32; i++) {
            int b = hash[i] & 0xff;
            byte hi = (byte) (b / 16);
            byte lo = (byte) (b - 16 * hi);
            s[2 * i] = toChar(hi);
            s[2 * i + 1] = toChar(lo);
        }

        return s;
    }

    private byte toChar(byte b) {
        if (b < 10)
            return (byte) (b + 0x30);
        else
            return (byte) (b + 0x57);
    }
}
