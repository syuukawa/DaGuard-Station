import React, { Component } from 'react';
import connect from 'react-redux/es/connect/connect';
import { withRouter } from 'react-router-dom';
import globalActions from "../redux/global/actions";

const $ = window.jQuery;
class PureSuccessPage extends Component {
    render() {
        const { parentState } = this.props;
        return (
            <div uk-grid="true" style={parentState.show === 5 ? {} : { display: 'none' }}>
                <div style={{ marginLeft: '40%' }}>
                    <img src="/static/images/ic_duigou.svg" alt="Daguard" />
                </div>
                <div className="uk-width-1-1 uk-grid-margin" >
                    <table className="uk-table uk-table-divider uk-table-hover">
                        <thead>
                            <tr>
                                <th>Wallte Name</th>
                                <th>Wallet ID</th>

                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td >{parentState.label}</td>
                                <td >{parentState.walletId}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div className="uk-width-1-1 uk-grid-margin" uk-grid="true">
                    <div style={{ marginLeft: '40%' }}>
                        <button className="uk-button uk-button-success" onClick={() => this.props.show(1)}
                            type="button">Back to home page</button>
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
        alertMessage: function (code) {
            dispatch(globalActions.alertMessage({
                type: 'error',
                code: code
            }));
        }
    }
}

const SuccessPage = withRouter(connect(mapStateToProps, mapDispatchToProps)(PureSuccessPage));
export default SuccessPage;