import React from 'react';
import {AppRegistry} from 'react-native';
import {StackNavigator} from 'react-navigation';
import {HomeScreen} from './HomeScreen';
import {DetailScreen} from './DetailScreen';

const RNDemo = StackNavigator({
    Home: { screen: HomeScreen },
    Detail: { screen: DetailScreen },
});
AppRegistry.registerComponent('RNDemo', () => RNDemo);