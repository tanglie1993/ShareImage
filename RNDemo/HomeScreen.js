import React, {Component} from 'react';
import {FlatList, Image, ScrollView, StyleSheet, Text, TouchableOpacity, View} from 'react-native';
import {Constants} from "./Constants";
import Orientation from 'react-native-orientation';
import ImagePicker from 'react-native-image-picker';
import { TouchableHighlight } from 'react-native'

HEIGHT = 100;
WIDTH = 100;
SCREEN_WIDTH = require('Dimensions').get('window').width;

export class HomeScreen extends Component {

    static navigationOptions = {
        title: 'Welcome',
    };

    constructor(props) {
        super(props);
        this.state = {
            listData : null
        };
    }

    componentWillMount() {
        // 只允许竖屏
        Orientation.lockToPortrait();
    }

    componentDidMount() {
        fetch(Constants.BASE_URL + 'imageList?user_id=' + Constants.USER_ID)
            .then((response) => response.json())
            .then((responseJson) => {
                this.setState({
                    listData:responseJson.list
                });
            })
            .catch((error) => {
                console.error(error);
            });
    }

    render(){
        if (!this.state.listData) {
            return this.renderLoadingView();
        }
        return this.renderContent(this.state.listData);
    }

    renderLoadingView(){
        return (
            <View style={styles.container}>
                <Text>
                    正在加载......
                </Text>
            </View>
        );
    }

    like(){
        console.log("like")
    }

    renderContent(listData) {
        const { navigate } = this.props.navigation;
        imageBaseUrl = Constants.BASE_URL + 'image/' + Constants.USER_ID + '/'
        return(
            <View>
                <ScrollView>
                        <View style={styles.listContainer}>
                            <FlatList
                                data={listData}
                                renderItem={({item}) =>
                                    <View style={styles.listItem}>
                                        <TouchableHighlight onPress={() => navigate('Detail', {param:  imageBaseUrl + item.imageUrl + '.png'})}>
                                            <Image
                                                style={{
                                                    width: SCREEN_WIDTH - 22,
                                                    height: 200,
                                                    resizeMode: 'cover'}}
                                                source={{uri: imageBaseUrl + item.imageUrl + '.png'}}
                                                />
                                        </TouchableHighlight>

                                        <View style={{
                                            width: SCREEN_WIDTH - 22,
                                            backgroundColor: "#808080",
                                            height: 1
                                        }}/>

                                        <View
                                            style={{
                                                width: SCREEN_WIDTH - 22,
                                                height: 50,
                                                flexDirection: 'row'}}>

                                            <TouchableHighlight style={styles.listItemButton}>
                                                <Text  style={styles.listItemButtonText}>
                                                    test
                                                </Text>
                                            </TouchableHighlight>

                                            <View style={{
                                                width: 1,
                                                backgroundColor: "#808080",
                                                height: 50
                                            }}/>

                                            <TouchableHighlight style={styles.listItemButton}>
                                                <Text  style={styles.listItemButtonText}>
                                                    test
                                                </Text>
                                            </TouchableHighlight>

                                        </View>
                                    </View>
                                }
                            />
                        </View>
                </ScrollView>

                <TouchableHighlight
                    style={styles.addButton}
                    underlayColor='#ff7043'
                    onPress={()=>{
                        options = {
                            title: 'Select Avatar',
                            customButtons: [
                                {name: 'fb', title: 'Choose Photo from Facebook'},
                            ],
                            storageOptions: {
                                skipBackup: true,
                                path: 'images'
                            }
                        };
                        ImagePicker.showImagePicker(options, (response)  => {
                            navigate('Upload', {param: response.uri})
                    });}}>
                    <Text style={{fontSize: 50, color: 'white'}}>+</Text>
                </TouchableHighlight>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    listContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    listItem: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        margin:10,
        borderWidth: 1,
        borderColor: "#123456",
    },
    listItemButton:{
        width: SCREEN_WIDTH / 2 - 12,
        borderColor: "#808080",
        height: 49,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor:"#ffffff"
    },
    listItemButtonText:{
        color :'#000000',
        fontSize: 25
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
    addButton: {
        backgroundColor: '#ff5722',
        borderColor: '#ff5722',
        borderWidth: 1,
        height: 80,
        width: 80,
        borderRadius: 40,
        alignItems: 'center',
        justifyContent: 'center',
        position: 'absolute',
        bottom: 20,
        right:20,
        shadowColor: "#000000",
        shadowOpacity: 0.8,
        shadowRadius: 5,
        shadowOffset: {
            height: 5,
            width: 5
        }
    }
});