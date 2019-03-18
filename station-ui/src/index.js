import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from "react-redux";
import {Route, Router, Switch} from "react-router-dom";
import store from "./redux/store";
import history from './config/history';
import Container from "./component/Container";
import * as register from "./config/register";
import routes from "./data/routes";
import SendCoin from "./component/SendCoin";
import CreateWallet from "./component/CreateWallet";

register.init();

const App = (
    <Provider store={store}>
        <Router history={history}>
            <Container>
                <Switch>
                    <Route exact path={routes.dashboard} component={CreateWallet}/>
                    <Route path={routes.sendCoin} component={SendCoin}/>
					<Route path={routes.createWallet} component={CreateWallet}/>
                </Switch>
            </Container>
        </Router>
    </Provider>
);

ReactDOM.render(App, document.getElementById('root'));
