import React, {Component} from 'react';
import PropTypes from 'prop-types';
import { View, requireNativeComponent, ViewPropTypes } from 'react-native';

class WaverView extends Component {
    render() {
        return (
            <NativeWaverView
                {...this.props}
                style={[{
                    backgroundColor: 'transparent',
                }, this.props.style
                ]}
            />
        );
    }
}

WaverView.propTypes = {
    ...View.propTypes,
    value: PropTypes.number,
    color:PropTypes.string,
};

const NativeWaverView = requireNativeComponent('WaverView', WaverView);

module.exports = WaverView;