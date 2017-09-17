import React from 'react';
import {AppRegistry} from 'react-native';
import {StackNavigator} from 'react-navigation';
import {HomeScreen} from './HomeScreen';
import {DetailScreen} from './DetailScreen';
import {UploadScreen} from './UploadScreen';

const RNDemo = StackNavigator({
    Home: { screen: HomeScreen },
    Detail: { screen: DetailScreen },
    Upload: { screen: UploadScreen }
});
AppRegistry.registerComponent('RNDemo', () => RNDemo);