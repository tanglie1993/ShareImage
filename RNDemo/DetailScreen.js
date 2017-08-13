import React from 'react';
import {Text, View} from 'react-native';
import {StackNavigator} from 'react-navigation';

export class DetailScreen extends React.Component {
    static navigationOptions = {
        title: 'Chat with Lucy',
    };
    render() {
        return (
            <View>
                <Text>Chat with Lucy</Text>
            </View>
        );
    }
}