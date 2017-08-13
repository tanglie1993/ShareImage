import React from 'react';
import {AppRegistry} from 'react-native';
import {StackNavigator} from 'react-navigation';
import {HomeScreen} from './HomeScreen';
import {ChatScreen} from './ChatScreen';

const RNDemo = StackNavigator({
    Home: { screen: HomeScreen },
    Chat: { screen: ChatScreen },
});
AppRegistry.registerComponent('RNDemo', () => RNDemo);