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
        this.state = {text: ''};
    }

    render() {
        return(
            <ScrollView>
              <Text style={{fontSize:96}}>Scroll me plz</Text>
              <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                  style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
                <Image source={{uri: 'http://n1.itc.cn/img8/wb/recom/2016/05/23/146397295100845261.PNG'}}
                       style={{width: 100, height: 100}} />
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
