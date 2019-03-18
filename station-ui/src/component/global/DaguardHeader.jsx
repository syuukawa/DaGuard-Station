import React, { Component } from 'react';
import connect from 'react-redux/es/connect/connect';
import { Link, withRouter } from 'react-router-dom';
import DaguardLogo from "./DaguardLogo";

class PureDaguardHeader extends Component {

    items = ["createWallet", "sendCoin"];
    route = {
        createWallet: "/create-wallet",
        sendCoin: "/send-coin"
    }
    getMenuItem = (item) => {
        const { labels } = this.props;
        const path = this.route[item];
        return (
            <li key={item} className={this.props.history.location.pathname.startsWith(path) ? "uk-active" : ""}>
                <Link to={path}>{labels[item]}</Link>
            </li>
        );
    };

    render() {
        return (
            <header data-uk-sticky="true">
                <nav className="header-desktop uk-navbar-container  uk-visible" uk-navbar="true">
                    <div className="uk-navbar-left">
                        <DaguardLogo className="uk-navbar-item uk-logo" />
                        <ul className="uk-navbar-nav">
                            {this.items.map(i => {
                                return this.getMenuItem(i);
                            })}
                        </ul>
                    </div>
                </nav>
            </header>
        );
    }
}

function mapStateToProps(state) {
    return {
        labels: state.statics.labels,
        user: state.user.user || {}
    }
}

function mapDispatchToProps(dispatch, ownProps) {
    return {
        logout: function () {
        }
    }
}

const DaguardHeader = withRouter(connect(mapStateToProps, mapDispatchToProps)(PureDaguardHeader));
export default DaguardHeader;