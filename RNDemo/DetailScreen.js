import React from 'react';
import {Image} from 'react-native';
import {StackNavigator} from 'react-navigation';
import {Constants} from "./Constants";

export class DetailScreen extends React.Component {

    HEIGHT = 100;
    WIDTH = 100;

    static navigationOptions = {
        title: 'Detail',
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