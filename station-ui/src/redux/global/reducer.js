import events from "./events";
import * as common from "../../utility/common";

const initialState = {
    status: false,
    messages: [],
    loadings: [],
    twofactors: [],
};

export default (state = initialState, action = {}) => {
    switch (action.type) {
        case events.APP_READY: {
            return common.merge(state, {
                status: true
            });
        }
        case events.START_LOADING: {
            let loadings = state.loadings.slice(0);
            loadings.push(action.promise);
            return common.merge(state, {
                loadings: loadings
            });
        }
        case events.END_LOADING: {
            let loadings = state.loadings.slice(0);
            const offset = loadings.indexOf(action.promise);
            loadings.splice(offset, 1);
            return common.merge(state, {
                loadings: loadings
            });
        }
        case events.ALERT_MESSAGE: {
            let messages = state.messages.slice(0);
            messages.push(action.data);
            return common.merge(state, {
                messages: messages
            });
        }
        case events.DISMISS_MESSAGE: {
            let messages = state.messages.slice(0);
            const offset = messages.indexOf(action.data);
            messages.splice(offset, 1);
            return common.merge(state, {
                messages: messages
            });
        }
        case events.CLEAR_MESSAGES: {
            return common.merge(state, {
                messages: []
            });
        }
        case events.SHOW_TWO_FACTOR: {
            let twofactors = state.twofactors.slice(0);
            if (twofactors.indexOf(action.data) < 0) {
                twofactors.push(action.data);
            }
            return common.merge(state, {
                twofactors: twofactors
            });
        }
        case events.CLEAR_TWO_FACTORS: {
            return common.merge(state, {
                twofactors: []
            });
        }
        case events.REMOVE_TWO_FACTOR: {
            let twofactors = state.twofactors.slice(0);
            const idx = twofactors.indexOf(action.data);
            if (idx > -1) {
                twofactors.splice(idx, 1);
            }
            return common.merge(state, {
                twofactors: twofactors
            });
        }
        default: {
            return state;
        }
    }
};