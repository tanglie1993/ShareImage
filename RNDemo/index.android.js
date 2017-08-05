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
            content: null,
        };
    }

    componentDidMount()
    {
        fetch('http://116.62.134.157:80/imageList?user_id=42')
            .then((response) => response.json())
            .then((responseJson) => {
                console.log("aaaaaa")
                this.setState({
                    content:responseJson
                });
            })
            .catch((error) => {
                console.error(error);
            });
    }

    render()
    {
        if (!this.state.content) {
            return this.renderLoadingView();
        }
        return this.renderContent(this.state.content);
    }

    renderLoadingView()
    {
        return (
            <View style={styles.container}>
              <Text>
                正在加载数据......
              </Text>
            </View>
        );
    }


    renderContent(text) {
        console.log("ddddddddddd")
        let uri = 'http://116.62.134.157:80/image/42/1501339989343.png';
        let height = 100;
        let width = 100;
        var pages = [];
        for (var i = 0; i < 10; i++) {
            pages.push(
                <Image
                    key={i}
                    source={{uri: uri}}
                    style={{width: width, height: height}} />
            );
        }
        return(
            <ScrollView>
              <Text style={{fontSize:16}}>"ssss"</Text>
              <View>
                  { pages }
              </View>
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
