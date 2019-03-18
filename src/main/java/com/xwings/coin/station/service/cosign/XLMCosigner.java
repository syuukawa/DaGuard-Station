package com.xwings.coin.station.service.cosign;

import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.constant.NetworkMode;
import com.xwings.coin.station.util.JsonUtils;
import com.xwings.coin.station.util.Numbers;
import org.springframework.stereotype.Service;
import org.stellar.sdk.Network;
import org.stellar.sdk.xdr.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * Created by ajax.wang on 2/22/2019.
 */
@Service
public class XLMCosigner implements CoinCosigner {

    @Override
    public Coin getCoin() {
        return Coin.XLM;
    }

    @Override
    public String cosign(String walletPrivateKey, String tankPrivateKey, String encodedTxData, NetworkMode networkMode) throws IOException {
        // decode txData
        String json = new String(Base64.getDecoder().decode(encodedTxData));
        TransactionData txData = JsonUtils.parseAsObject(json, TransactionData.class);

        // cosign
        byte[] unsignedBytes = Base64.getDecoder().decode(txData.getHex());
        TransactionEnvelope transactionEnvelope = TransactionEnvelope.decode(new XdrDataInputStream(new ByteArrayInputStream(unsignedBytes)));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Hashed NetworkID
        if (networkMode == NetworkMode.TESTNET)
            Network.useTestNetwork();
        else
            Network.usePublicNetwork();
        outputStream.write(Network.current().getNetworkId());
        // Envelope Type - 4 bytes
        outputStream.write(ByteBuffer.allocate(4).putInt(EnvelopeType.ENVELOPE_TYPE_TX.getValue()).array());
        // Transaction XDR bytes
        ByteArrayOutputStream txOutputStream = new ByteArrayOutputStream();
        XdrDataOutputStream xdrOutputStream = new XdrDataOutputStream(txOutputStream);
        org.stellar.sdk.xdr.Transaction.encode(xdrOutputStream, transactionEnvelope.getTx());
        outputStream.write(txOutputStream.toByteArray());
        byte[] hash = org.stellar.sdk.Transaction.fromEnvelopeXdr(transactionEnvelope).hash();

        // sign
        org.stellar.sdk.KeyPair keyPair = org.stellar.sdk.KeyPair.fromSecretSeed(walletPrivateKey);
        List<DecoratedSignature> signatures = new ArrayList<>(Arrays.asList(transactionEnvelope.getSignatures()));
        signatures.add(keyPair.signDecorated(hash));
        transactionEnvelope.setSignatures(signatures.toArray(new DecoratedSignature[signatures.size()]));

        // encode
        ByteArrayOutputStream codecOutputStream = new ByteArrayOutputStream();
        XdrDataOutputStream codecXdrOutputStream = new XdrDataOutputStream(codecOutputStream);
        org.stellar.sdk.xdr.TransactionEnvelope.encode(codecXdrOutputStream, transactionEnvelope);
        byte[] signedBytes = codecOutputStream.toByteArray();
        txData.setHex(Base64.getEncoder().encodeToString(signedBytes));

        // encode txData and return
        String newJson = JsonUtils.convertToJson(txData);
        return Base64.getEncoder().encodeToString(newJson.getBytes());
    }

    @Override
    public boolean validatePubKey(String pubKey) {
        return Numbers.isNumberOrCharacter(pubKey);
    }
}
