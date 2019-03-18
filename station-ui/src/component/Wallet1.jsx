import React, {Component} from 'react';
import connect from 'react-redux/es/connect/connect';
import {withRouter} from 'react-router-dom';

const $ = window.jQuery;

class PureWallet1 extends Component {
    coins = ["BTC", "USDT", "ETH", "EOS", "XRP", "XLM"];
    state = {
        coin: "Select coin",
        walletName: "",
        passphrase: ""
    }

    render() {
        const {parentState} = this.props;
        return (
            <div className="uk-width-1-2" style={parentState.show === 1 ? {} : {display: 'none'}}>
                <div className="uk-width-1-1 uk-grid-margin">
                    <button type="button" className="uk-input">
                        <span style={{float: 'left'}}>
                            {this.state.coin === "Select coin" ? "" : <img style={{width: "16px", marginRight: "10px"}}
                                                                           src={"/static/images/ic_" + this.state.coin.toLowerCase() + ".svg"}/>}
                            {this.state.coin}</span>
                        <span style={{float: 'right'}} uk-icon="icon: triangle-down"/>
                    </button>
                    <div uk-dropdown="boundary: .uk-input;mode: click;" className="uk-dropdown-scrollable">
                        <ul className="uk-nav uk-dropdown-nav">
                            {this.coins.map(p => {
                                return (
                                    <li key={p} className="uk-dropdown-close">
                                        <a href="javascript:void(0)" onClick={() => {
                                            this.setState({
                                                    coin: p
                                                },
                                            );
                                        }}>
                                            <img style={{width: "16px", marginRight: "10px"}}
                                                 src={"/static/images/ic_" + p.toLowerCase() + ".svg"}/>
                                            {p}
                                        </a>
                                    </li>
                                );
                            })}
                        </ul>
                    </div>
                </div>
                <div className="uk-width-1-1 uk-grid-margin">
                    <p className="uk-card-title uk-text-uppercase uk-text-small uk-text-bold">Name your Wallet</p>
                    <input className='uk-input' placeholder="Wallet name" value={this.state.walletName} onChange={e => {
                        this.setState({
                            walletName: e.target.value
                        });
                    }}/>
                </div>
                <div style={{marginTop: 5}}>{this.state.coin == 'EOS' &&
                <legend style={{fontSize:13}}
                    className="uk-text-left@m uk-margin-medium-bottom">EOS Account and Only Contains: abcdefghijklmnopqrstuvwxyz12345, Length is 12</legend>
                }</div>
                <div className="uk-width-1-1 uk-grid-margin">
                    <p className="uk-card-title uk-text-uppercase uk-text-small uk-text-bold">Passphrase</p>
                    <input className='uk-input' type="password" placeholder="Wallet Passphrase"
                           value={this.state.passphrase} onChange={e => {
                        this.setState({
                            passphrase: e.target.value
                        });
                    }}/>
                </div>
                <div className="uk-width-1-1 uk-grid-margin">
                    <button className="uk-button uk-button-success uk-grid-margin"
                            disabled={this.state.coin === "" || this.state.walletName === "" || this.state.passphrase === "" ? "disabled" : ""}
                            onClick={() => {
                                this.props.setWallet1Data(this.state.coin, this.state.walletName, this.state.passphrase);
                            }}> Next
                    </button>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {}
}

function mapDispatchToProps(dispatch, ownProps) {
    return {}
}

const Wallet1 = withRouter(connect(mapStateToProps, mapDispatchToProps)(PureWallet1));
export default Wallet1;