package com.xwings.coin.station.service.cosign;

import com.ripple.core.coretypes.AccountID;
import com.ripple.core.types.known.tx.signed.SignedTransaction;
import com.ripple.core.types.known.tx.txns.Payment;
import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.constant.NetworkMode;
import com.xwings.coin.station.util.JsonUtils;
import com.xwings.coin.station.util.Numbers;
import org.springframework.stereotype.Service;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by ajax.wang on 2/22/2019.
 */
@Service
public class XRPCosigner implements CoinCosigner {

    @Override
    public Coin getCoin() {
        return Coin.XRP;
    }

    @Override
    public String cosign(String walletPrivateKey, String tankPrivateKey, String encodedTxData, NetworkMode networkMode) throws IOException {
        // decode
        String json = new String(Base64.getDecoder().decode(encodedTxData));
        TransactionData txData = JsonUtils.parseAsObject(json, TransactionData.class);

        // cosign
        String txJson = new String(Numeric.hexStringToByteArray(txData.getHex()), "utf-8");
        String account = AccountID.fromSeedString(walletPrivateKey).address;
        Payment payment = (Payment) Payment.fromJSON(txJson);
        SignedTransaction signedTx = payment.signFor(account, walletPrivateKey);
        String newTxJson = signedTx.txn.toJSON().toString();
        String newTxHex = Numeric.toHexStringNoPrefix(newTxJson.getBytes("utf-8"));
        txData.setHex(newTxHex);

        // encode and return
        String newJson = JsonUtils.convertToJson(txData);
        return Base64.getEncoder().encodeToString(newJson.getBytes());
    }

    @Override
    public boolean validatePubKey(String pubKey) {
        return pubKey.startsWith("r") && !Numbers.containsAnyOfChars(pubKey, "0", "O", "l", "I")
                && Numbers.isNumberOrCharacter(pubKey) && Numbers.isLengthBetween(pubKey, 25, 35);
    }
}
