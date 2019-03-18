import * as common from "../../utility/common";
import events from "./events";

const initialState = {
    loginStatus: false,
    details: {}
};

export default (state = initialState, action = {}) => {

    switch (action.type) {
        case events.USER_AUTHENTICATED: {
            return common.merge(state, {
                loginStatus: true,
            });
        }
        case events.UPDATE_USER: {
            return common.merge(state, {
                details: action.data
            });
        }
        case events.USER_LOGOUT: {
            return common.merge(state, {
                loginStatus: false,
                details: {}
            })
        }
        default: {
            return state;
        }
    }
};