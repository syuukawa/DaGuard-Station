import {applyMiddleware, combineReducers, compose, createStore} from 'redux';
import thunk from 'redux-thunk';
import global from './global/reducer';
import statics from './statics/reducer';
import user from './user/reducer';

const reducers = combineReducers({
    global,
    statics,
    user
});

let composeEnhancers = compose;

if (!process.env.NODE_ENV || process.env.NODE_ENV === 'development') {
    composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
}
const store = createStore(reducers, {}, composeEnhancers(applyMiddleware(thunk)));

export default store;