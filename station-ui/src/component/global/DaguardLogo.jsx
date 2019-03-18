import React, {Component} from 'react';
import connect from 'react-redux/es/connect/connect';
import {Link, withRouter} from 'react-router-dom';

class PureDaguardLogo extends Component {

    render() {
        return (
            <Link to={"/"} className={this.props.className}>
                <img src="/static/images/daguard-logo.svg" alt="Daguard" height="132" width="160"/>
            </Link>
        );
    }
}

function mapStateToProps(state) {
    return {
    }
}

function mapDispatchToProps(dispatch, ownProps) {
    return {}
}

const DaguardLogo = withRouter(connect(mapStateToProps, mapDispatchToProps)(PureDaguardLogo));
export default DaguardLogo;