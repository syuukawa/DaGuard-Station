package com.xwings.coin.station.service.cosign;

import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.constant.NetworkMode;
import com.xwings.coin.station.util.JsonUtils;
import io.bigbearbro.eos4j.client.transaction.SignedTransactionToPush;
import io.bigbearbro.eos4j.client.transaction.TransactionToSign;
import io.bigbearbro.eos4j.crypto.EccTool;
import io.bigbearbro.eos4j.crypto.util.Ripemd160;
import io.bigbearbro.eos4j.eosio.chain.action.Action;
import io.bigbearbro.eos4j.eosio.chain.action.data.TransferActionData;
import io.bigbearbro.eos4j.utils.ByteUtils;
import org.bitcoinj.core.Base58;
import org.springframework.stereotype.Service;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * Created by ajax.wang on 1/29/2019.
 */
@Service
public class EOSCosigner implements CoinCosigner {

    @Override
    public Coin getCoin() {
        return Coin.EOS;
    }

    @Override
    public String cosign(String walletPrivateKey, String tankPrivateKey, String encodedTxData, NetworkMode networkMode) throws IOException {
        // decode
        final String json = new String(Base64.getDecoder().decode(encodedTxData));
        final TransactionData txData = JsonUtils.parseAsObject(json, TransactionData.class);

        // cosign
        String txJson = new String(Numeric.hexStringToByteArray(txData.getHex()), "utf-8");
        SignedTransactionToPush tx = JsonUtils.parseAsObject(txJson, SignedTransactionToPush.class);
        // convert actions
        List<Action> actions = tx.getTransaction().getActions();
        for (Action action : actions) {
            String mapJson = JsonUtils.convertToJson(action.getData());
            if ("transfer".equals(action.getName())) {
                TransferActionData transferData = JsonUtils.parseAsObject(mapJson, TransferActionData.class);
                action.setData(transferData);
            }
        }
        TransactionToSign txToSign = new TransactionToSign(tx.getChainId(), tx.getTransaction());
        String signature = EccTool.signTransaction(walletPrivateKey, txToSign);
        tx.getSignatures().add(signature);
        String txHex = Numeric.toHexStringNoPrefix(JsonUtils.convertToJson(tx).getBytes("utf-8"));
        txData.setHex(txHex);

        // encode and return
        String newJson = JsonUtils.convertToJson(txData);
        return Base64.getEncoder().encodeToString(newJson.getBytes());
    }

    @Override
    public boolean validatePubKey(String pubKey) {
        if (!pubKey.startsWith("EOS")) {
            return false;
        }

        final String publicKey = pubKey.replace("EOS", "");
        final byte[] pubBuffer = Base58.decode(publicKey);
        final byte[] key = ByteUtils.copy(pubBuffer, 0, pubBuffer.length - 4);

        final byte[] checkSum = Ripemd160.digest(key);

        final byte[] keyCheckSum = ByteUtils.copy(pubBuffer, pubBuffer.length - 4, 4);

        return Arrays.equals(keyCheckSum, ByteUtils.copy(checkSum, 0, 4));
    }

}
