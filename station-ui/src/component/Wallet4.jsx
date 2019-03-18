import React, { Component } from 'react';
import connect from 'react-redux/es/connect/connect';
import { withRouter } from 'react-router-dom';

const $ = window.jQuery;
class PureWallet4 extends Component {
    state = {
        apiKey: "",
        apiSecret: "",
        apiKeyPassphrase: "",
    }
    render() {
        const { parentState } = this.props;
        return (
            <div className="uk-width-1-2" uk-grid="true" style={parentState.show === 4 ? {} : { display: 'none' }}>
                <div className="uk-width-1-1">
                    <p className="uk-card-title uk-text-uppercase uk-text-small uk-text-bold">Api Key</p>
                    <input className='uk-input' placeholder="Api Key" value={this.state.apiKey} onChange={e => {
                        this.setState({
                            apiKey: e.target.value
                        });
                    }} />
                </div>
                <div className="uk-width-1-1 uk-grid-margin">
                    <p className="uk-card-title uk-text-uppercase uk-text-small uk-text-bold">Api Key Secret</p>
                    <input className='uk-input' placeholder="Api Secret" value={this.state.apiSecret} onChange={e => {
                        this.setState({
                            apiSecret: e.target.value
                        });
                    }} />
                </div>
                <div className="uk-width-1-1 uk-grid-margin">
                    <p className="uk-card-title uk-text-uppercase uk-text-small uk-text-bold">Api Passphrase</p>
                    <input className='uk-input' type="password" placeholder="Api Passphrase" value={this.state.apiKeyPassphrase} onChange={e => {
                        this.setState({
                            apiKeyPassphrase: e.target.value
                        });
                    }} />
                </div>
                <div className="uk-width-1-1 uk-grid-margin" uk-grid="true">
                    <div>
                        <button className="uk-button uk-button-success"
                            disabled={this.state.apiKey === "" || this.state.apiSecret === "" || this.state.apiKeyPassphrase === "" ? "disabled" : ""}
                            onClick={() => this.props.setWallet4Data(this.state.apiKey, this.state.apiSecret, this.state.apiKeyPassphrase)}
                            type="button">Create Wallet</button>
                    </div>
                    <div style={{display: 'none'}}>
                        <button className="uk-button uk-button-normal" onClick={() => this.props.show(3)}
                            type="button"> Backup</button>
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
    return {
    }
}

const Wallet4 = withRouter(connect(mapStateToProps, mapDispatchToProps)(PureWallet4));
export default Wallet4;