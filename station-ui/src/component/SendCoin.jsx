import React, {Component} from 'react';
import connect from 'react-redux/es/connect/connect';
import {withRouter} from 'react-router-dom';
import globalActions from "../redux/global/actions";
import AppNotification from "./global/AppNotification";
import LoadingSpinner from "./global/LoadingSpinner";
import http from "../config/http";

const $ = window.jQuery;

class PureSendCoin extends Component {
    coins = ["BTC", "USDT", "ETH", "USDC", "EOS", "XRP", "XLM"];
    state = {
        active: false,
        coin: "Select coin",
        errorMsg: "",
        tCoin: "",
        tTxid: "",
        tAddress: "",
        tAmount: "",
        tTransferRequestId: "",
        tTransferState: "",
        tMessage: ""
    }
    apiHeader = {
        apiKey: "",
        apiKeySecret: "",
        apiKeyPassphrase: "",
    }
    sendcoin = {
        coin: "",
        walletId: "",
        toAddress: "",
        walletPassphrase: "",
        amount: ""
    }
    show = (show, hide) => {
        var showDiv = "#page" + show;
        var hideDiv = "#page" + hide;
        $(showDiv).show();
        $(hideDiv).hide();
    }
    send = (e) => {
        e.preventDefault();
        this.apiHeader.apiKey = $("#api-key").val();
        this.apiHeader.apiKeySecret = $("#api-key-secret").val();
        this.apiHeader.apiKeyPassphrase = $("#api-key-passphrase").val();
        this.sendcoin.coin = this.state.coin;
        this.sendcoin.walletId = $("#wallet-id").val();
        this.sendcoin.toAddress = $("#wallet-toAddress").val();
        this.sendcoin.walletPassphrase = $("#wallet-passphrase").val();
        this.sendcoin.amount = $("#amount").val();
        if (this.sendcoin.coin === "Select coin") {
            this.setState({
                errorMsg: "Please select coin!"
            });
            return;
        }
        let vm = this;
        this.setState({
            active: true
        });
        this.setState({
            tCoin: "",
            tTxid: "",
            tAddress: "",
            tAmount: "",
            tTransferRequestId: "",
            tTransferState: "",
            tMessage: ""
        });
        http.request(true, this.sendcoin.coin + "/wallets/" + this.sendcoin.walletId + "/transferRequests", {
            data: this.sendcoin,
            successHandler: (response) => {
                vm.setState({
                    active: false
                });
                if (response) {
                    vm.setState({
                        tCoin: response.coin,
                        tTxid: response.txid,
                        tAddress: response.toAddress,
                        tAmount: response.amount,
                        tTransferRequestId: response.transferRequestId,
                        tTransferState: response.transferState,
                        tMessage: response.message
                    });
                }
                vm.show(2, 1);
            },
            errorHandler: (error) => {
                vm.setState({
                    active: false
                });
                if (error && error.response && error.response.data) {
                    vm.props.alertMessage(error.response.data.errorMessage ? error.response.data.errorMessage : error.response.data.errorCode);
                }
            }
        }, {
            "API-KEY": this.apiHeader.apiKey,
            "API-KEY-SECRET": this.apiHeader.apiKeySecret,
            "API-KEY-PASSPHRASE": this.apiHeader.apiKeyPassphrase
        });
    }

    render() {
        const digits = 0.0001;
        return (
            <div>
                <main className="uk-container uk-container-large main-content-container uk-margin-large-bottom">
                    <div
                        className="page-heading uk-flex uk-flex-middle uk-flex-between uk-flex-wrap uk-margin-top uk-margin-bottom">
                        <h1 className="uk-heading-primary uk-margin-remove">Send Coin</h1>
                    </div>
                    <div className="uk-grid-medium">
                        <div id="order-status-card" className="uk-width-1-1">
                            <div className="uk-card uk-card-default uk-card-body">
                                <div className="uk-overflow-auto uk-margin-medium-bottom">
                                    <div id="page1" className="uk-width-1-2">
                                        <form onSubmit={this.send}>
                                            {this.state.errorMsg &&
                                            <legend
                                                className="uk-text-danger uk-margin-medium-bottom title-text">{this.state.errorMsg}</legend>
                                            }
                                            <div className="uk-width-1-1">
                                                <button type="button" className="uk-input">
                                                    <span style={{float: 'left'}}>
                                                        {this.state.coin === "Select coin" ? "" :
                                                            <img style={{width: "16px", marginRight: "10px"}}
                                                                 src={"/static/images/ic_" + this.state.coin.toLowerCase() + ".svg"}/>}
                                                        {this.state.coin}</span>
                                                    <span style={{float: 'right'}} uk-icon="icon: triangle-down"/>
                                                </button>
                                                <div uk-dropdown="boundary: .uk-input;mode: click;"
                                                     className="uk-dropdown-scrollable">
                                                    <ul className="uk-nav uk-dropdown-nav">
                                                        {this.coins.map(p => {
                                                            return (
                                                                <li key={p} className="uk-dropdown-close">
                                                                    <a href="javascript:void(0)" onClick={() => {
                                                                        this.setState({coin: p});
                                                                    }}>
                                                                        <img
                                                                            style={{width: "16px", marginRight: "10px"}}
                                                                            src={"/static/images/ic_" + p.toLowerCase() + ".svg"}/>
                                                                        {p}
                                                                    </a>
                                                                </li>
                                                            );
                                                        })}
                                                    </ul>
                                                </div>
                                            </div>
                                            <div className="uk-width-1-1">
                                                <p className="uk-card-title uk-text-uppercase uk-text-small uk-text-bold">Wallet
                                                    ID</p>
                                                <input className='uk-input' id="wallet-id" placeholder="Wallet Id"
                                                       required/>
                                            </div>
                                            <div className="uk-width-1-1">
                                                <p className="uk-card-title uk-text-uppercase uk-text-small uk-text-bold">Wallet
                                                    passphrase</p>
                                                <input className='uk-input' id="wallet-passphrase" type="password"
                                                       placeholder="Wallet Passphrase" required/>
                                            </div>
                                            <div className="uk-width-1-1">
                                                <p className="uk-card-title uk-text-uppercase uk-text-small uk-text-bold">Address</p>
                                                <input className='uk-input' id="wallet-toAddress" placeholder="Address"
                                                       required/>
                                            </div>
                                            <div className="uk-width-1-1">
                                                <p className="uk-card-title uk-text-uppercase uk-text-small uk-text-bold">Amount</p>
                                                <input className='uk-input' id="amount" min={"" + digits} step={digits}
                                                       type="number" placeholder="Enter amount" required/>
                                            </div>
                                            <div className="uk-width-1-1">
                                                <p className="uk-card-title uk-text-uppercase uk-text-small uk-text-bold">Api
                                                    key</p>
                                                <input className='uk-input' id="api-key" placeholder="Enter Api key"
                                                       required/>
                                            </div>
                                            <div className="uk-width-1-1">
                                                <p className="uk-card-title uk-text-uppercase uk-text-small uk-text-bold">Api
                                                    key secret</p>
                                                <input className='uk-input' id="api-key-secret"
                                                       placeholder="Enter Api key secret" required/>
                                            </div>
                                            <div className="uk-width-1-1">
                                                <p className="uk-card-title uk-text-uppercase uk-text-small uk-text-bold">Api
                                                    key passphrase</p>
                                                <input className='uk-input' id="api-key-passphrase" type="password"
                                                       placeholder="Enter Api key passphrase" required/>
                                            </div>
                                            <div id="nc" className="uk-width-1-1 uk-grid-margin">
                                                <button className="uk-button uk-button-success uk-grid-margin"> Send
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                    <div id="page2" uk-grid="true" style={{display: 'none'}}>
                                        <div style={{marginLeft: '40%'}}>
                                            <img src="/static/images/ic_duigou.svg" alt="Daguard"/>
                                        </div>
                                        <div className="uk-width-1-1 uk-grid-margin">
                                            <table className="uk-table uk-table-divider uk-table-hover">
                                                <tbody>
                                                <tr>
                                                    <td>Coin</td>
                                                    <td>
                                                        <img style={{width: "16px", marginRight: "10px"}}
                                                             src={"/static/images/ic_" + this.state.tCoin.toLowerCase() + ".svg"}/>
                                                        {this.state.tCoin}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Txid</td>
                                                    <td>{this.state.tTxid}</td>
                                                </tr>
                                                <tr>
                                                    <td>Address</td>
                                                    <td>{this.state.tAddress}</td>
                                                </tr>
                                                <tr>
                                                    <td>Amount</td>
                                                    <td>{this.state.tAmount}</td>
                                                </tr>
                                                <tr>
                                                    <td>Transfer Request Id</td>
                                                    <td>{this.state.tTransferRequestId}</td>
                                                </tr>
                                                <tr>
                                                    <td>Transfer State</td>
                                                    <td>{this.state.tTransferState}</td>
                                                </tr>
                                                <tr>
                                                    <td>Message</td>
                                                    <td>{this.state.tMessage}</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div className="uk-width-1-1 uk-grid-margin" uk-grid="true">
                                            <div style={{marginLeft: '40%'}}>
                                                <button className="uk-button uk-button-success"
                                                        onClick={() => this.show(1, 2)}
                                                        type="button">Back to home page
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <LoadingSpinner active={this.state.active}/>
                <AppNotification/>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {}
}

function mapDispatchToProps(dispatch, ownProps) {
    return {
        alertMessage: function (code) {
            dispatch(globalActions.alertMessage({
                type: 'error',
                code: code
            }));
        }
    }
}

const SendCoin = withRouter(connect(mapStateToProps, mapDispatchToProps)(PureSendCoin));
export default SendCoin;