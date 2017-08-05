/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
    TextInput,
    ScrollView,
    Image
} from 'react-native';

export default class RNDemo extends Component {
    constructor(props) {
        super(props);
        console.log("00000000")
        this.state = {
            content: "",
        };
    }

        let uri = 'http://116.62.134.157:80/image/42/1501339989343.png';
        let height = 100;
        let width = 100;
        return(
            <ScrollView>
              <Image source={{uri: uri}}
                  style={{width: width, height: height}} />
                <Image source={{uri: uri}}
                style={{width: width, height: height}} />
                <Image source={{uri: uri}}
                       style={{width: width, height: height}} />
                <Image source={{uri: uri}}
                       style={{width: width, height: height}} />
                <Image source={{uri: uri}}
                       style={{width: width, height: height}} />
              <Image source={{uri: uri}}
                     style={{width: width, height: height}} />
              <Image source={{uri: uri}}
                     style={{width: width, height: height}} />
              <Image source={{uri: uri}}
                     style={{width: width, height: height}} />
              <Image source={{uri: uri}}
                     style={{width: width, height: height}} />
              <Image source={{uri: uri}}
                     style={{width: width, height: height}} />
              <Image source={{uri: uri}}
                     style={{width: width, height: height}} />
              <Image source={{uri: uri}}
                     style={{width: width, height: height}} />
              <Image source={{uri: uri}}
                     style={{width: width, height: height}} />
              <Image source={{uri: uri}}
                     style={{width: width, height: height}} />
              <Image source={{uri: uri}}
                     style={{width: width, height: height}} />
              <Text style={{fontSize:80}}>React Native</Text>
            </ScrollView>
        );
    }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('RNDemo', () => RNDemo);
