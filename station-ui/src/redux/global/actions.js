import events from './events';
import * as common from "../../utility/common";
import http from "../../config/http";


const timers = {};


const startLoading = (promise) => {
    return (dispatch) => {
        dispatch({
            type: events.START_LOADING,
            promise: promise
        });
    };
};
const endLoading = (promise) => {
    return (dispatch) => {
        dispatch({
            type: events.END_LOADING,
            promise: promise
        });
    };
};


const setMessageTimer = (dispatch, key, data) => {
    timers[key] = setTimeout(() => {
        dispatch(dismissMessage(key, data))
    }, 5000);
};

const alertMessage = (data) => {
    return (dispatch) => {
        const key = JSON.stringify(data);
        if (!(key in timers)) {
            dispatch({
                type: events.ALERT_MESSAGE,
                data: data,
            });
        } else {
            const timer = timers[key];
            clearTimeout(timer);
        }
        setMessageTimer(dispatch, key, data);
    };
};
const dismissMessage = (key, data) => {
    return (dispatch) => {
        dispatch({
            type: events.DISMISS_MESSAGE,
            data: data,
        });
        if (key in timers) {
            delete timers[key];
        }
    };
};

const clearMessages = () => {
    return (dispatch) => {
        dispatch({
            type: events.CLEAR_MESSAGES
        });
        Object.keys(timers).map(key => {
                const timer = timers[key];
                clearTimeout(timer);
                delete timers[key];
            }
        );
    };
};
const toogleTwoFactorModal = (config) => {
    return (dispatch) => {
        dispatch({
            type: events.SHOW_TWO_FACTOR,
            data: config
        });
    };
};

const clearTwoFactorQueue = () => {
    return (dispatch) => {
        dispatch({
            type: events.CLEAR_TWO_FACTORS,
        });
    };
};

const processTwoFactorQueue = (twoFactorCode, twoFactorQueue) => {
    return (dispatch) => {

        for (let config of twoFactorQueue) {
            dispatch({
                type: events.REMOVE_TWO_FACTOR,
                data: config
            });
            const promise = new Promise(() => {});
            dispatch(startLoading(promise));
            setTimeout(() => {
                if (config.url) {
                    const _config = common.merge(config, {
                        twoFactorCode: twoFactorCode
                    });
                    if (config.isPostRequest) {
                        http.post(config.url, _config);
                    } else {
                        http.get(config.url, _config);
                    }
                }
                dispatch(endLoading(promise));
            }, 500);
        }
    };
};


export default {
    startLoading,
    endLoading,
    alertMessage,
    dismissMessage,
    clearMessages,
    toogleTwoFactorModal,
    clearTwoFactorQueue,
    processTwoFactorQueue,
};