import React, {Component} from 'react';
import connect from 'react-redux/es/connect/connect';
import {withRouter} from 'react-router-dom';

const $ = window.jQuery;

class PureWallet2 extends Component {
    state = {
        backupPublicKey: "",
        publicKeyisNeed: false
    }

    render() {
        const {parentState} = this.props;
        return (
            <div className="uk-width-1-1 uk-margin-remove-top" style={parentState.show === 2 ? {} : {display: 'none'}}>
                <p className="uk-card-title ">Backup Public Key</p>
                <div className="uk-width-1-1 uk-margin-remove" uk-grid="true">
                    <div onClick={() => {
                        this.setState({
                            backupPublicKey: "",
                            publicKeyisNeed: false
                        })
                    }}
                         className={"uk-width-2-5@m uk-width-1-1@s uk-card uk-card-body uk-margin-bottom uk-padding-remove-top wallet-public-key-card-" + (this.state.publicKeyisNeed ? "default" : "checked")}>
                        <h3 className="uk-card-title">For Small Balances</h3>
                        <p>Create and print a backup key from your browser. Easiest and quickest but the most vulnerable
                            to loss or theft. Use this method only if you plan on storing small balances..</p>
                    </div>
                    <div onClick={() => {
                        this.setState({
                            publicKeyisNeed: true
                        })
                    }}
                         className={"uk-width-2-5@m uk-width-1-1@s uk-card uk-card-body uk-margin-bottom uk-padding-remove-top uk-margin-left wallet-public-key-card-" + (this.state.publicKeyisNeed ? "checked" : "default")}>
                        <h3 className="uk-card-title">I have my own Backup Key</h3>
                        <p>Do you already have an unused private/public key you created offline? Provide your public key
                            so we can create your secure wallet.</p>
                    </div>
                </div>
                <input className='uk-input uk-margin-top' placeholder="Enter backup public key"
                       value={this.state.backupPublicKey} onChange={e => {
                    this.setState({
                        backupPublicKey: e.target.value
                    });
                }}
                       style={this.state.publicKeyisNeed ? {} : {display: 'none'}}/>
                <div className="uk-width-1-1 uk-grid-margin" uk-grid="true">
                    <div>
                        <button className="uk-button uk-button-success"
                                disabled={this.state.publicKeyisNeed && this.state.backupPublicKey === "" ? "disabled" : ""}
                                type="button"
                                onClick={() => this.props.setWallet2Data(this.state.backupPublicKey)}>Next
                        </button>
                    </div>
                    <div style={{display: 'none'}}>
                        <button className="uk-button uk-button-normal" onClick={() => this.props.show(1)}
                                type="button"> Backup
                        </button>
                    </div>
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

const Wallet2 = withRouter(connect(mapStateToProps, mapDispatchToProps)(PureWallet2));
export default Wallet2;