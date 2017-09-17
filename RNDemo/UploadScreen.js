import React from 'react';
import {Image} from 'react-native';
import {StackNavigator} from 'react-navigation';
import {Constants} from "./Constants";

export class UploadScreen extends React.Component {

    HEIGHT = 500;
    WIDTH = 500;

    static navigationOptions = {
        title: 'Upload',
    };

    render() {
        return (
            <Image
                style={{width: this.WIDTH, height: this.HEIGHT}}
                source={{uri: this.props.navigation.state.params.param}}
            />
        );
    }
}