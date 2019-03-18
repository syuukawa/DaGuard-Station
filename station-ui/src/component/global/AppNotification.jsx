import React, {Component} from 'react';
import connect from 'react-redux/es/connect/connect';
import {withRouter} from 'react-router-dom';
import constants from "../../data/constants";
import globalActions from "../../redux/global/actions";
import * as messageUtil from "../../utility/message";

const UIkit = window.UIkit;

class PureAppNotification extends Component {

    getMessageClass = (messageType) => {
        switch (messageType) {
            case constants.message.warning:
                return "uk-notification-message-warning";
            case constants.message.error:
                return "uk-notification-message-danger";
            case constants.message.success:
                return "uk-notification-message-success";
            default:
                return "uk-notification-message-primary";
        }
    };

    dismissMessage = (message) => {
        this.props.dismissMessage(message);
    };

    render() {
        const {templates, messages} = this.props;
        return (
            <div className="global-message uk-notification uk-notification-top-center">
                {messages.map((m, i) => {
                    if (m.code) {
                        const className = "uk-notification-message " + this.getMessageClass(m.type);
                        const message = messageUtil.getTemplateString(templates, m.code, m.data);
                        return (
                            <div key={i} className={className}>
                                <a className="uk-notification-close" uk-close="true"
                                   onClick={() => {this.dismissMessage(m)}}/>
                                <p dangerouslySetInnerHTML={{__html: message}}/>
                            </div>
                        );
                    } else {
                        return null;
                    }
                })}
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        messages: state.global.messages || [],
        templates: state.statics.templates || {},
    }
}

function mapDispatchToProps(dispatch, ownProps) {
    return {
        dismissMessage: (message) => {
            dispatch(globalActions.dismissMessage(message));
        }
    }
}

const AppNotification = withRouter(connect(mapStateToProps, mapDispatchToProps)(PureAppNotification));
export default AppNotification;