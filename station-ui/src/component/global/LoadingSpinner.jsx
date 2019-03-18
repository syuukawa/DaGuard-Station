import React, {Component} from 'react';
import connect from 'react-redux/es/connect/connect';
import {withRouter} from 'react-router-dom';

class PureLoadingSpinner extends Component {

    render() {
        return this.props.active && (
            <div className="loading-page">
                <div className="loading-spinner">
                    <div className="uk-spinner uk-icon">
                        <svg width="60" height="60" viewBox="0 0 30 30" xmlns="http://www.w3.org/2000/svg">
                            <circle fill="none" stroke="#000" cx="15" cy="15" r="14"/>
                        </svg>
                    </div>
                    <p>{this.props.labels["loading"]}...</p>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        // active: state.global.loadings.filter(p => (p instanceof Promise)).length > 0,
        labels: state.statics.labels,
    }
}

function mapDispatchToProps(dispatch, ownProps) {
    return {}
}

const LoadingSpinner = withRouter(connect(mapStateToProps, mapDispatchToProps)(PureLoadingSpinner));
export default LoadingSpinner;