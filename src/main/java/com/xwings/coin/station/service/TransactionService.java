package com.xwings.coin.station.service;

import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.rpc.DaGuardClient;
import com.xwings.coin.station.rpc.RpcException;
import com.xwings.coin.station.rpc.request.CreateTransferRequest;
import com.xwings.coin.station.rpc.request.SendTransferRequest;
import com.xwings.coin.station.rpc.response.KeyChainResponse;
import com.xwings.coin.station.rpc.response.TransferRequestResponse;
import com.xwings.coin.station.rpc.response.WalletResponse;
import com.xwings.coin.station.service.cosign.CosignHelper;
import com.xwings.coin.station.service.security.KeyCrypto;
import com.xwings.coin.station.service.security.KeyTool;
import com.xwings.coin.station.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class TransactionService {

    private static final String PENDING_APPROVAL = "PENDING_APPROVAL";

    @Autowired
    private CosignHelper cosignHelper;

    public TransferRequestResponse sendTransaction(DaGuardClient daGuardClient, Coin coin, String amount, String walletId, String address, String walletPassphrase) throws NoSuchAlgorithmException, InvalidKeyException, IOException, RpcException {
        final TransferRequestResponse transferRequestResponse = buildTransaction(daGuardClient, coin.name(), walletId, amount, address);

        final WalletResponse walletResponse = getWallet(daGuardClient, coin.name(), walletId);
        final KeyChainResponse keyChainData = getKeyChain(daGuardClient, coin.name(), walletResponse.getKeys().get(0));

        final String privateKey = KeyTool.decryptKey(JsonUtils.parseAsObject(keyChainData.getEncryptedPrivateKey(), KeyCrypto.class), walletPassphrase);

        final Coin signCoin = coin.keyChainCoin();
        final String txData = cosignHelper.cosign(signCoin.name(), privateKey, transferRequestResponse.getTxData());

        final TransferRequestResponse sendTransferResponse = sendTransaction(daGuardClient, coin.name(), txData, walletId, transferRequestResponse
                .getTransferRequestId());

        if (PENDING_APPROVAL.equals(transferRequestResponse.getTransferState())) {
            transferRequestResponse.setMessage("Need approval. ");
            return transferRequestResponse;
        }

        return sendTransferResponse;
    }

    private KeyChainResponse getKeyChain(DaGuardClient daGuardClient, String coin, String keyId) throws NoSuchAlgorithmException, InvalidKeyException, IOException, RpcException {
        final String url = "/" + coin + "/key/" + keyId;

        return daGuardClient.get(url, KeyChainResponse.class);
    }

    private TransferRequestResponse buildTransaction(DaGuardClient daGuardClient, String coin, String walletId, String amount, String toAddress) throws IOException, InvalidKeyException, NoSuchAlgorithmException, RpcException {
        final String url = "/" + coin + "/wallets/" + walletId + "/transferRequests";

        final CreateTransferRequest createTransferRequest = new CreateTransferRequest();
        createTransferRequest.setAmount(amount);
        createTransferRequest.setToAddress(toAddress);

        return daGuardClient.post(url, createTransferRequest);
    }

    private TransferRequestResponse sendTransaction(DaGuardClient daGuardClient, String coin, String txData, String walletId, String transferRequestId) throws IOException, InvalidKeyException, NoSuchAlgorithmException, RpcException {
        final String url = "/" + coin + "/wallets/" + walletId + "/transferRequests/" + transferRequestId + "/send";

        final SendTransferRequest sendTransferRequest = new SendTransferRequest();
        sendTransferRequest.setTxData(txData);

        return daGuardClient.post(url, sendTransferRequest);
    }

    private WalletResponse getWallet(DaGuardClient daGuardClient, String coin, String walletId) throws NoSuchAlgorithmException, InvalidKeyException,
            IOException, RpcException {
        final String url = "/" + coin + "/wallets/" + walletId;

        return daGuardClient.get(url, WalletResponse.class);
    }

}
