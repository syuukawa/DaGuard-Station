import React, { Component } from 'react';
import connect from 'react-redux/es/connect/connect';
import { withRouter } from 'react-router-dom';
import AppNotification from "./global/AppNotification";
import DaguardHeader from "./global/DaguardHeader";

class PureContainer extends Component {

    render() {
        return (
            <div className="uk-height-1-1">
                <DaguardHeader />
                {this.props.appStatus && this.props.children}
                <AppNotification />
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        appStatus: state.global.status,
        loginStatus: state.user.loginStatus
    }
}

function mapDispatchToProps(dispatch, ownProps) {
    return {}
}

const Container = withRouter(connect(mapStateToProps, mapDispatchToProps)(PureContainer));
export default Container;