import authEvents from './events';
import globalActions from "../global/actions";
import http from "../../config/http";
import {apis} from "../../data/apis";
import session from "../../config/session";
import * as common from "../../utility/common";
import constants from "../../data/constants";
import * as urlUtil from "../../utility/url";

const authenticate = (data, dispatch) => {
    let token = data.sessionToken;
    session.store(token);
    dispatch(getUserDetails());
};

const register = (user, successHanlder, errorHandler) => {
    return (dispatch) => {
        http.post(apis.user.register, {
            data: user,
            successHandler: data => {
                common.excuteFunc(successHanlder, data);
                dispatch(globalActions.alertMessage({
                    type: constants.message.success,
                    code: data.messageCode,
                    data: {
                        email: user.email
                    }
                }));
            },
            errorHandler: errorHandler,
            inlineError: true
        });
    }
};

const activate = (data, errorHandler) => {
    return (dispatch) => {
        const url = urlUtil.getPathVariableUrl(apis.user.activate, data);
        http.get(url, {
            successHandler: data => authenticate(data, dispatch),
            errorHandler: errorHandler,
            inlineError: true
        });
    }
};

const login = (user, errorHandler) => {
    return (dispatch) => {
        http.post(apis.user.login, {
            data: user,
            successHandler: data => authenticate(data, dispatch),
            errorHandler: errorHandler,
            inlineError: true
        });
    }
};

const forgotPassword = (user, successHandler, errorHandler) => {
    return (dispatch) => {
        http.post(apis.user.forgotPassword, {
            data: user,
            successHandler: successHandler,
            errorHandler: errorHandler,
            inlineError: true
        });
    }
};

const verifyResetPassword = (user, successHandler, errorHandler) => {
    return (dispatch) => {
        http.post(apis.user.verifyForgotPassword, {
            data: user,
            successHandler: successHandler,
            errorHandler: errorHandler,
            inlineError: true
        });
    }
};

const resetPassword = (user, successHandler, errorHandler) => {
    return (dispatch) => {
        http.post(apis.user.resetPassword, {
            data: user,
            successHandler: successHandler,
            errorHandler: errorHandler,
            inlineError: true
        })
    }
};

const getUserDetails = (successHanlder, errorHandler) => {
    return (dispatch) => {
        http.get(apis.user.self, {
            successHandler: (data) => {
                dispatch({
                    type: authEvents.UPDATE_USER,
                    data: data
                });
                dispatch({
                    type: authEvents.USER_AUTHENTICATED,
                });
                common.excuteFunc(successHanlder, data);
            },
            errorHandler: errorHandler
        })
    };
};

const logout = () => {
    return (dispatch) => {
        dispatch({
            type: authEvents.USER_LOGOUT
        });
        if (session.retrive()) {
            http.post(apis.user.logout, {loading: false});
            session.remove();
        }
    }
};

export default {
    register,
    activate,
    login,
    logout,
    getUserDetails,
    forgotPassword,
    verifyResetPassword,
    resetPassword,

};
