package com.xwings.coin.station.controller;

import com.google.common.base.Strings;
import com.google.zxing.WriterException;
import com.xwings.coin.station.constant.Coin;
import com.xwings.coin.station.constant.ErrorCode;
import com.xwings.coin.station.controller.dto.CreateAddress;
import com.xwings.coin.station.controller.dto.CreateTransfer;
import com.xwings.coin.station.controller.dto.CreateWallet;
import com.xwings.coin.station.controller.dto.CreateWalletAll;
import com.xwings.coin.station.rpc.DaGuardClient;
import com.xwings.coin.station.rpc.RpcException;
import com.xwings.coin.station.rpc.request.CreateAddressRequest;
import com.xwings.coin.station.rpc.response.*;
import com.xwings.coin.station.service.TransactionService;
import com.xwings.coin.station.service.ValidationException;
import com.xwings.coin.station.service.WalletService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

/**
 * Created by zihao.long on 12/11/2018.
 */
@RestController
@Api(tags = "Wallets")
public class WalletController extends BaseController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    /**
     * used by station-ui only
     *
     * @param coin
     * @param walletPassphrase
     * @param backupPublicKey
     * @param id
     * @param label
     * @return
     * @throws IOException
     * @throws WriterException
     * @throws GeneralSecurityException
     */
    @GetMapping("/keycard/{coin}")
    @ApiIgnore
    @ResponseHeader(description = "Returns PDF", responseContainer = "{\"type\": \"file\"}")
    public ResponseEntity<byte[]> createKeyCard(@PathVariable String coin,
                                                @RequestParam(required = false) String walletPassphrase,
                                                @RequestParam(required = false) String backupPublicKey,
                                                @RequestParam(required = false) String id,
                                                @RequestParam(required = false) String label) throws IOException, WriterException, GeneralSecurityException {
        Assert.hasLength(coin, "Coin is required");
        Assert.isTrue(Coin.isExist(coin), "Coin " + coin + " Not Supported for now");
        Assert.hasLength(walletPassphrase, "walletPassphrase is required");
        Assert.hasLength(id, "id is required");
        Assert.hasLength(label, "wallet name is required");

        final byte[] bytes = walletService.createUserKey(coin, walletPassphrase, backupPublicKey, id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        final String filename = coin + "_" + label + "_KeyCard.pdf";
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build());
        headers.set("x-suggested-filename", filename);

        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }

    /**
     * used by station-ui only
     *
     * @param createWallet
     * @param coin
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     * @throws WriterException
     * @throws ValidationException
     * @throws RpcException
     */
    @PostMapping(RestEndpoints.LIST_WALLETS)
    @ApiIgnore
    public WalletResponse createWallet(@RequestBody CreateWallet createWallet, @PathVariable String coin) throws IOException, GeneralSecurityException, WriterException, ValidationException, RpcException {
        final String label = createWallet.getLabel();
        final String id = createWallet.getId();

        final DaGuardClient daGuardClient = getClient();

        return walletService.createWallet(daGuardClient, coin, label, id);
    }

    @PostMapping(RestEndpoints.CREATE_WALLETS)
    @ApiOperation(value = "Create Wallet")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createWallet", value = "{\n" +
                    "\"label\" : \"ETH\",\n" +
                    "\"coin\" : \"ETH\",\n" +
                    "\"walletPassphrase\" : \"Xwings2019\"\n" +
                    "}", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header")
    })
    public CreateWalletResponse createWalletAll(@RequestBody CreateWalletAll createWallet) throws IOException, GeneralSecurityException, ValidationException, RpcException {
        final String coin = createWallet.getCoin();
        final String walletPassphrase = createWallet.getWalletPassphrase();
        final String backupPublicKey = createWallet.getBackupPublicKey();
        final String label = createWallet.getLabel();

        Assert.hasLength(coin, "Coin is required");
        Assert.isTrue(Coin.isExist(coin), "Coin " + coin + " Not Supported for now");
        Assert.hasLength(walletPassphrase, "walletPassphrase is required");
        Assert.hasLength(label, "label is required");

        final String id = UUID.randomUUID().toString();
        final UserKey userKey = walletService.getUserKey(coin, walletPassphrase, backupPublicKey, id);

        final WalletResponse walletResponse = walletService.createWallet(getClient(), coin, label, id);

        if (Strings.isNullOrEmpty(walletResponse.getErrorCode()) && Strings.isNullOrEmpty(walletResponse.getErrorMessage())) {
            return new CreateWalletResponse(walletResponse, userKey);
        }

        return new CreateWalletResponse(walletResponse.getErrorCode(), walletResponse.getErrorMessage());
    }

    @GetMapping("/validate/{coin}")
    @ApiOperation(value = "validate backup public key")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "backupPublicKey", value = "AAA", required = false, dataType = "String", paramType = "query")
    })
    public ResponseEntity<String> validateBackupPublicKey(@PathVariable String coin, @RequestParam String backupPublicKey) {
        Assert.hasLength(coin, "Coin is required");
        Assert.isTrue(Coin.isExist(coin), "Coin " + coin + " Not Supported for now");

        walletService.validatePubKey(coin, backupPublicKey);

        return ResponseEntity.ok("pass");
    }

    @GetMapping(RestEndpoints.GET_WALLET)
    @ApiOperation(value = "Get Wallet")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "walletId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path")
    })
    public WalletResponse getWallet() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getServletPath(), WalletResponse.class);
    }

    @GetMapping(RestEndpoints.LIST_WALLETS)
    @ApiOperation(value = "Get All Wallets")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path")
    })
    public ListWalletsResponse getAllWallets() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getServletPath(), ListWalletsResponse.class);
    }

    @PostMapping(RestEndpoints.CREATE_ADDRESS)
    @ApiOperation(value = "Create Wallet Address")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createAddress", value = "{\n" +
                    "  \"label\": \"user-1000001\"\n" +
                    "}", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "walletId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path")
    })
    public WalletAddressResponse createAddress(@Valid @RequestBody CreateAddress createAddress) throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.post(daGuardClient, getServletPath(), new CreateAddressRequest(createAddress.getLabel()));
    }

    @GetMapping(RestEndpoints.GET_ADDRESS)
    @ApiOperation(value = "Get Wallet Address")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "walletId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "address", value = "2N2myjnpodTWeB8Vt9EZ8Awwc7kWKd1VkMd", required = true, dataType = "String", paramType = "path")
    })
    public WalletAddressResponse getAddress() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getServletPath(), WalletAddressResponse.class);
    }

    @GetMapping(RestEndpoints.LIST_ADDRESSES)
    @ApiOperation(value = "Get Wallet All Addresses")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "walletId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path")
    })
    public ListWalletAddressesResponse listAddresses() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getServletPath(), ListWalletAddressesResponse.class);
    }

    @PostMapping(RestEndpoints.CREATE_TRANSFER_REQUEST)
    @ApiOperation(value = "Create Transfer Request")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createTransfer", value = "{\n" +
                    "  \"walletPassphrase\": \"123456\"\n" +
                    "  \"amount\": \"1.0\"\n" +
                    "  \"toAddress\": \"2NEWJAhPM7Ssvyq9RJvSi1uVvc6W9MB4ige\"\n" +
                    "}", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "walletId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path")
    })
    public TransferRequestResponse createTransferRequest(@PathVariable(value = "coin") String coin,
                                                         @PathVariable(value = "walletId") String walletId,
                                                         @Valid @RequestBody CreateTransfer createTransfer) throws Exception {
        final Coin coinEnum = EnumUtils.getEnum(Coin.class, coin);
        if (coinEnum == null) {
            throw new ValidationException(ErrorCode.INVALID_COIN.name(), "Invalid coin");
        }

        if (!coinEnum.isSupportedAmount(createTransfer.getAmount())) {
            throw new ValidationException(ErrorCode.INVALID_AMOUNT.name(), "Invalid amount");
        }

        final String amount = createTransfer.getAmount();
        final String address = createTransfer.getToAddress();
        final String walletPassphrase = createTransfer.getWalletPassphrase();

        final DaGuardClient daGuardClient = getClient();

        return transactionService.sendTransaction(daGuardClient, coinEnum, amount, walletId, address, walletPassphrase);
    }

    @GetMapping(RestEndpoints.GET_TRANSFER_REQUEST)
    @ApiOperation(value = "Get Transfer Request")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "walletId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "transferRequestId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path")
    })
    public TransferRequestResponse getTransferRequest() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getServletPath(), TransferRequestResponse.class);
    }

    @GetMapping(RestEndpoints.LIST_TRANSFER_REQUESTS)
    @ApiOperation(value = "Get Wallet All Transfer Requests")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "walletId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "transferState", value = "PENDING_APPROVAL", required = false, dataType = "String", paramType = "query")
    })
    public ListTransferRequestResponse listTransferRequestResponse() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getRequestURLWithParams(), ListTransferRequestResponse.class);
    }

    @GetMapping(RestEndpoints.GET_TRANSFER)
    @ApiOperation(value = "Get Wallet Transfer")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "walletId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "transferId", value = "22b7ebcca1f34c11b737bc3ecc430382", required = true, dataType = "String", paramType = "path")
    })
    public TransferResponse getTransfer() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getServletPath(), TransferResponse.class);
    }

    @GetMapping(RestEndpoints.LIST_TRANSFERS)
    @ApiOperation(value = "Get Wallet All Transfers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "walletId", value = "7378a51735364602be93b80c2dd3d8dc", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "txid", value = "22b7ebcca1f34c11b737bc3ecc430382", required = false, dataType = "String", paramType = "query")
    })
    public ListTransfersResponse listTransfers() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getRequestURLWithParams(), ListTransfersResponse.class);
    }

    @GetMapping(RestEndpoints.GET_KEY_CHAIN)
    @ApiOperation(value = "Get KeyChain")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "keyId", value = "a06b7adc789a768ba256486898cde859", required = true, dataType = "String", paramType = "path"),
    })
    public KeyChainResponse getKeyChain() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getServletPath(), KeyChainResponse.class);
    }

    @GetMapping(RestEndpoints.LIST_COINS)
    @ApiOperation(value = "Get Coins")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header")
    })
    public ListCoinResponse listCoin() throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getServletPath(), ListCoinResponse.class);
    }

    @GetMapping(RestEndpoints.GET_COIN)
    @ApiOperation(value = "Get Coin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "API-KEY", value = "359c26a316f97e17bf6b17169e6bd816", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-SECRET", value = "x6NzNCWvCNSNnM/60QxKyRBqR/cl9gVQoDSv3ssn9ZM=", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "API-KEY-PASSPHRASE", value = "Xwings2019", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "coin", value = "BTC", required = true, dataType = "String", paramType = "path")
    })
    public CoinResponse getCoin(@PathVariable(name = "coin") String coin) throws Exception {
        final DaGuardClient daGuardClient = getClient();

        return apiForwardService.get(daGuardClient, getServletPath(), CoinResponse.class);
    }

}
