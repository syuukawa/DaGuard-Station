import React, {Component} from 'react';
import connect from 'react-redux/es/connect/connect';
import {withRouter} from 'react-router-dom';
import axios from 'axios';
import saveAs from 'file-saver'
import LoadingSpinner from "./global/LoadingSpinner";
import globalActions from "../redux/global/actions";
import Wallet1 from './Wallet1';
import Wallet2 from './Wallet2';
import Wallet3 from './Wallet3';
import Wallet4 from './Wallet4';
import SuccessPage from './SuccessPage';
import http from "../config/http";

const $ = window.jQuery;

class PureCreateWallet extends Component {
    apiHeader = {
        apiKey: "",
        apiKeySecret: "",
        apiKeyPassphrase: "",
    }
    state = {
        active: false,
        errorMsg: "",
        show: 1,
        walletId: "",
        label: ""

    }
    pdf = {
        id: "",
        backupPublicKey: "",
        walletPassphrase: "",
        label: ""
    }
    createWallet = {
        coin: "",
        label: "",
        id: ""
    };

    S4 = () => {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    };
    guid = () => {
        return (this.S4() + this.S4() + "-" + this.S4() + "-" + this.S4() + "-" + this.S4() + "-" + this.S4() + this.S4() + this.S4());
    };
    downLoadFile = (options) => {
        let vm = this;
        axios.get(options.url, {
            params: this.pdf,
            responseType: 'blob'
        }).then((response) => {
            const fileName =  response.headers['x-suggested-filename']
            if (fileName) {
                saveAs(response.data, fileName)
            } else {
                vm.props.alertMessage("INTERNAL_SERVER_ERROR");
            }
        })
    };
    setWallet1Data = (coin, walletName, passphrase) => {
        if (coin == "EOS") {
            var rex = new RegExp(/^[abcdefghijklmnopqrstuvwxyz12345]{12}$/, "g");
            if (!rex.test(walletName)) {
                this.setState({
                    errorMsg: 'EOS Account Only Contains:abcdefghijklmnopqrstuvwxyz12345, Length is 12'
                });
                return;
            }
        }

        if (coin === "Select coin") {
            this.setState({
                errorMsg: "Please select coin!"
            });
        } else if (walletName === "") {
            this.setState({
                errorMsg: "Wallet name is required!"
            });
        } else if (passphrase === "") {
            this.setState({
                errorMsg: "Wallet passphrase is required!"
            });
        } else {
            this.pdf.walletPassphrase = passphrase;
            this.pdf.label = walletName;
            this.createWallet.coin = coin;
            this.createWallet.label = walletName;
            this.setState({
                errorMsg: "",
                show: 2
            })
        }
    }
    setWallet2Data = (backupPublicKey) => {
        this.setState({
            active: true
        });
        let vm = this;
        if (backupPublicKey !== "") {
            axios.get(
                "/api/validate/" + this.createWallet.coin,
                {
                    params: {
                        backupPublicKey: backupPublicKey
                    }
                }
            ).then(
                function (response) {
                    vm.setState({
                        active: false
                    });
                    if (response.data.errorMessage) {
                        vm.props.alertMessage(response.data.errorMessage);
                    } else if (response.data.errorCode) {
                        vm.props.alertMessage(response.data.errorCode);
                    } else {
                        this.setState({
                            active: false
                        });
                        vm.downLoadPDF(backupPublicKey);
                    }
                }
            ).catch(function (error) {
                vm.setState({
                    active: false
                });
                if (error && error.response && error.response.data) {
                    vm.props.alertMessage(error.response.data.errorMessage ? error.response.data.errorMessage : error.response.data.errorCode);
                }
            });
        } else {
            this.setState({
                active: false
            });
            this.downLoadPDF(backupPublicKey);
        }
    };
    downLoadPDF = (backupPublicKey) => {
        let id = this.guid();
        this.pdf.backupPublicKey = backupPublicKey;
        this.pdf.id = id;
        this.createWallet.id = id;
        var options = {
            url: "/api/keycard/" + this.createWallet.coin,
            data: this.pdf
        };
        this.setState({
            show: 3
        });
        this.downLoadFile(options);
    };
    setWallet4Data = (apiKey, apiKeySecret, apiKeyPassphrase) => {
        if (apiKey === "") {
            this.setState({
                errorMsg: "Api Key is required!"
            });
        } else if (apiKeySecret === "") {
            this.setState({
                errorMsg: "Api Secret is required!"
            });
        } else if (apiKeyPassphrase === "") {
            this.setState({
                errorMsg: "Api Passphrase is required!"
            });
        } else {
            this.apiHeader.apiKey = apiKey;
            this.apiHeader.apiKeySecret = apiKeySecret;
            this.apiHeader.apiKeyPassphrase = apiKeyPassphrase;
            this.setState({
                errorMsg: ""
            });
            this.setState({
                active: true
            });
            let vm = this;
            http.request(true, this.createWallet.coin + '/wallets', {
                data: this.createWallet,
                successHandler: (response) => {
                    vm.setState({
                        active: false
                    });
                    if (response) {
                        vm.setState({
                            walletId: response.walletId,
                            label: response.label
                        });
                    }
                    vm.show(5);
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
    }
    show = (pageNum) => {
        this.setState({
            errorMsg: "",
            show: pageNum
        })
    }

    render() {
        return (
            <div>
                <main className="uk-container uk-container-large main-content-container uk-margin-large-bottom">
                    <div
                        className="page-heading uk-flex uk-flex-middle uk-flex-between uk-flex-wrap uk-margin-top uk-margin-bottom">
                        <h1 className="uk-heading-primary uk-margin-remove">Create Wallet</h1>
                    </div>
                    <div id="create-wallet" className="uk-grid-medium" uk-grid="true">
                        <div className="uk-width-1-1">
                            <div className="uk-card uk-card-default uk-card-body uk-width-1-1">
                                <div className="uk-overflow-auto uk-margin-medium-bottom uk-width-1-1">
                                    {this.state.errorMsg &&
                                    <legend
                                        className="uk-text-danger uk-margin-medium-bottom title-text">{this.state.errorMsg}</legend>
                                    }
                                    <Wallet1 parentState={this.state} setWallet1Data={this.setWallet1Data}/>
                                    <Wallet2 parentState={this.state} setWallet2Data={this.setWallet2Data}
                                             show={this.show}/>
                                    <Wallet3 parentState={this.state} show={this.show}/>
                                    <Wallet4 parentState={this.state} setWallet4Data={this.setWallet4Data}
                                             show={this.show}/>
                                    <SuccessPage parentState={this.state} show={this.show}/>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <LoadingSpinner active={this.state.active}/>
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

const CreateWallet = withRouter(connect(mapStateToProps, mapDispatchToProps)(PureCreateWallet));
export default CreateWallet;