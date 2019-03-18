package com.xwings.coin.station.service.cosign;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.constant.NetworkMode;
import com.xwings.coin.station.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.*;
import static org.bitcoinj.core.Utils.HEX;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by ajax.wang on 11/16/2018.
 */
@Service
public class BTCCosigner implements CoinCosigner {
    private static final Logger LOG = LoggerFactory.getLogger(BTCCosigner.class);

    @Value("${networkMode}")
    private NetworkMode networkMode;

    @Override
    public Coin getCoin() {
        return Coin.BTC;
    }

    @Override
    public String cosign(String walletPrivateKey, String tankPrivateKey, String encodedTxData, NetworkMode networkMode) throws IOException {
        // get transaction data
        String json = new String(Base64.getDecoder().decode(encodedTxData));
        TransactionData txData = JsonUtils.parseAsObject(json, TransactionData.class);
        // decide the network params
        NetworkParameters netParams = networkMode.toNetworkParameters();
        sign(txData, walletPrivateKey, tankPrivateKey, netParams);
        String newJson = JsonUtils.convertToJson(txData);

        return Base64.getEncoder().encodeToString(newJson.getBytes());
    }

    @Override
    public boolean validatePubKey(String pubKey) {
        try {
            final byte[] pubkey = HEX.decode(pubKey);
            final ECKey key = ECKey.fromPublicOnly(pubkey);
            final NetworkParameters networkParameters = networkMode.toNetworkParameters();
            final String address = LegacyAddress.fromKey(networkParameters, key).toBase58();

            return !Strings.isNullOrEmpty(address);
        } catch (Exception e) {
            LOG.error("Validate BTC Publick Key error: {}", pubKey, e);
        }

        return false;
    }

    public static void sign(TransactionData txData, String walletPrivateKey, String tankPrivateKey, NetworkParameters netParams) {
        Transaction tx;

        byte[] rawTx = HEX.decode(txData.getHex());
        tx = new Transaction(netParams, rawTx);
        for (int i = 0; i < tx.getInputs().size(); i++) {
            ECKey key = null;
            boolean tank = false;
            if (!txData.getVin().get(i).isTank()) {
                DeterministicKey parent = DeterministicKey.deserializeB58(walletPrivateKey, netParams);
                key = ECKey.fromPrivate(parent.getPrivKey());
            } else {
                if (StringUtils.isBlank(tankPrivateKey)) {
                    // not sign by tank private ke
                    continue;
                }
                DeterministicKey parent = DeterministicKey.deserializeB58(tankPrivateKey, netParams);
                key = ECKey.fromPrivate(parent.getPrivKey());
                tank = true;
            }

            TransactionInput txInput = tx.getInputs().get(i);
            if (!tank) {
                String rawRedeemScript = txData.getVin().get(i).getRedeemScript();
                byte[] redeemScriptBytes = HEX.decode(rawRedeemScript);
                Script redeemScript = new Script(redeemScriptBytes);
                if (txInput.getScriptSig().getChunks().size() > 0) {
                    Script lastScript = txInput.getScriptSig();
                    List<byte[]> signatures = new ArrayList<>();
                    for (int j = 1; j < lastScript.getChunks().size() - 1; j++) {
                        signatures.add(lastScript.getChunks().get(j).data);
                    }
                    TransactionSignature signature = tx.calculateSignature(i, key, redeemScript, Transaction.SigHash.ALL, false);
                    signatures.add(signature.encodeToBitcoin());
                    Script inputScript = ScriptBuilder.createMultiSigInputScriptBytes(signatures, redeemScriptBytes);
                    txInput.setScriptSig(inputScript);
                } else {
                    TransactionSignature signature = tx.calculateSignature(i, key, redeemScript, Transaction.SigHash.ALL, false);
                    Script inputScript = ScriptBuilder.createP2SHMultiSigInputScript(ImmutableList.of(signature), redeemScript);
                    txInput.setScriptSig(inputScript);
                }
            } else {
                Address address = Address.fromString(netParams, txData.getVin().get(i).getAddress());
                Script script = ScriptBuilder.createOutputScript(address);
                TransactionSignature signature = tx.calculateSignature(i, key, script, Transaction.SigHash.ALL, false);
                Script inputScript = ScriptBuilder.createInputScript(signature, key);
                txInput.setScriptSig(inputScript);
            }
        }
        String hex = HEX.encode(tx.bitcoinSerialize());
        txData.setHex(hex);
    }

}
