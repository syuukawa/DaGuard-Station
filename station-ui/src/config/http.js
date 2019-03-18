import axios from 'axios';
import store from '../redux/store';
import globalActions from "../redux/global/actions";
import constants from "../data/constants";
import session from "./session";
import * as common from "../utility/common";

const ERROR_CODE = 'errorCode';
const ERROR_MESSAGE = 'errorMessage';
const HEADER = 'AUTH';
const HEADER_PREFIX = 'Bearer_';
const HEADER_TWO_FACTOR = 'TWO-FACTOR-CODE';

const instance = axios.create({
    baseURL: '/api/'
});

instance.defaults.withCredentials = true;

instance.defaults.headers.common['Content-Type'] = 'application/json';

instance.interceptors.response.use(function (response) {
    if (ERROR_MESSAGE in response.data) {
        throw {
            type: constants.message.error,
            code: response.data[ERROR_MESSAGE]
        };
    }

    if (ERROR_CODE in response.data) {
        throw {
            type: constants.message.error,
            code: response.data[ERROR_CODE]
        };
    }

    return response.data;
}, function (error) {
    let data = {
        type: constants.message.error,
        code: "UNKNOWN_ERR"
    };
    if (error.response) {
        switch (error.response.status) {
            case 401: {
                data.code = "UNAUTHORIZED, API_KEY Mismatch";
                break;
            }
            case 404: {
                data.code = "API_NOT_FOUND";
                break;
            }
            default: {
                break;
            }
        }

        if (error.response.data) {
            if (ERROR_MESSAGE in error.response.data) {
                data.code = error.response.data[ERROR_MESSAGE]
            } else if (ERROR_CODE in error.response.data) {
                data.code = error.response.data[ERROR_CODE]
            }
        }
    }

    return Promise.reject(data);
});

const getHeaders = (twoFactorCode, _headers = {}) => {
    let headers = {};
    const token = session.retrive();
    if (token) {
        headers[HEADER] = HEADER_PREFIX + token;
    }
    if (twoFactorCode) {
        headers[HEADER_TWO_FACTOR] = twoFactorCode;
    }
    return Object.assign(headers, _headers);
};

const defaultConfig = {
    loading: true,
    inlineError: false,
    data: {},
    twoFactorCode: null,
    successHandler: null,
    errorHandler: null
};

const request = (isPostRequest, url, config, headers = {}) => {
    const _config = common.merge(defaultConfig, config);
    const promise = instance.request({
        headers: getHeaders(_config.twoFactorCode, headers),
        method: isPostRequest ? 'post' : 'get',
        url: url,
        [isPostRequest ? "data" : "params"]: (_config.data || {})
    });
    if (_config.loading) {
        store.dispatch(globalActions.startLoading(promise));
    }
    promise.then(data => {
        common.excuteFunc(_config.successHandler, data);
        store.dispatch(globalActions.endLoading(promise));
    }).catch(error => {
        console.log(error);
        if (error && error.code) {
            if (error.code === "TWO_FACTOR_UNAUTHORIZED") {
                const requestParams = common.merge(_config, {
                    isPostRequest: isPostRequest,
                    url: url
                });
                store.dispatch(globalActions.toogleTwoFactorModal(requestParams));
            } else if (error.code !== "TWO_FACTOR_AUTH_NOT_ACTIVATED") {
                if (!_config.inlineError) {
                    store.dispatch(globalActions.alertMessage(error));
                }
                common.excuteFunc(_config.errorHandler, error);
            }
            store.dispatch(globalActions.endLoading(promise));
        }
    });
};


const post = (url, config) => {
    request(true, url, config);
};

const get = (url, config) => {
    request(false, url, config);
};


export default {
    post,
    get,
    request
};
