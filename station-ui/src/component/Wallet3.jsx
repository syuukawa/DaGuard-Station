import React, { Component } from 'react';
import connect from 'react-redux/es/connect/connect';
import { withRouter } from 'react-router-dom';

const $ = window.jQuery;
class PureWallet3 extends Component {
    state = {
        isChecked1: false,
        isChecked2: false,
    }
    check1 = (e) => {
        this.setState({
            isChecked1: e.target.checked
        })
    }
    check2 = (e) => {
        this.setState({
            isChecked2: e.target.checked
        })
    }
    render() {
        const { parentState } = this.props;
        return (
            <div className="uk-width-5-6@m uk-width-1-1@s" uk-grid="true" style={parentState.show === 3 ? {} : { display: 'none' }}>
                <div className="uk-width-1-1 uk-margin-bottom">
                    <div className="uk-width-1-1 uk-margin-large-bottom" style={{ display: '-webkit-inline-box' }}>
                        <div style={{ width: "45px" }}>
                            <img style={{ width: "40px", marginRight: "10px" }} src={"/static/images/1.svg"} />
                        </div>
                        <div >
                            <div>
                                <p className="uk-margin-remove" style={{ fontWeight: '600' }}>The browser has automatically downloaded keycard's PDF for you.</p>
                                <p className="uk-margin-remove">Confirm that the PDF is OK.</p>
                            </div>
                        </div>
                    </div>
                    <div className="uk-width-1-1" style={{ display: '-webkit-inline-box' }} className="uk-margin-remove">
                        <div style={{ width: "45px" }}>
                            <img style={{ width: "40px", marginRight: "10px" }} src={"/static/images/2.svg"} />
                        </div>
                        <div >
                            <div>
                                <p className="uk-margin-remove" style={{ fontWeight: '600' }}>Print and store your keycard offline.</p>
                                <p className="uk-margin-remove">Secure your keycard by storing it offline in a safe location, then delete the downloaded keycard from your computer.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="uk-margin-top" >
                    <div uk-grid="true" className="uk-margin-remove-top">
                        <div><input className="uk-checkbox uk-margin-bottom" type="checkbox" onChange={(e) => this.check1(e)} /></div>
                        <div className="uk-padding-remove"><p className="uk-margin-bottom">&nbsp;I have printed my keycard and stored it in a safe location.</p></div></div>
                    <div uk-grid="true" className="uk-margin-remove-top">
                        <div><input className="uk-checkbox uk-margin-bottom" type="checkbox" onChange={(e) => this.check2(e)} /></div>
                        <div className="uk-padding-remove"><p className="uk-margin-bottom">&nbsp;I have deleted my keycard from my computer.</p></div>
                    </div>
                    <div className="uk-width-1-1 uk-grid-margin" uk-grid="true">
                        <div>
                            <button className="uk-button uk-button-success" disabled={this.state.isChecked1 && this.state.isChecked2 ? "" : "disabled"} type="button"
                                onClick={() => this.props.show(4)}>Next</button>
                        </div>
                        <div  style={{display: 'none'}}>
                            <button className="uk-button uk-button-normal" onClick={() => this.props.show(2)}
                                type="button"> Backup</button>
                        </div>
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

const Wallet3 = withRouter(connect(mapStateToProps, mapDispatchToProps)(PureWallet3));
export default Wallet3;