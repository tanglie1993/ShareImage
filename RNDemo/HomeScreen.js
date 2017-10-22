import React, {Component} from 'react';
import {FlatList, Image, ScrollView, StyleSheet, Text, TouchableOpacity, View} from 'react-native';
import {Constants} from "./Constants";
import Orientation from 'react-native-orientation';
import ImagePicker from 'react-native-image-picker';
import { TouchableHighlight } from 'react-native';
import Prompt from 'react-native-prompt';

HEIGHT = 100;
WIDTH = 100;
ITEM_HORIZONTAL_MARGIN = 10;
SCREEN_WIDTH = require('Dimensions').get('window').width;

export class HomeScreen extends Component {

    static navigationOptions = {
        title: 'Welcome',
    };

    constructor(props) {
        super(props);
        this.state = {
            listData : null,
            message: '',
            promptVisible: false
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
        commentData = ["aaaaa", "sssss", "ssssa"];
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
                                                    width: SCREEN_WIDTH - 2 * ITEM_HORIZONTAL_MARGIN - 2,
                                                    height: 200,
                                                    resizeMode: 'cover'}}
                                                source={{uri: imageBaseUrl + item.imageUrl + '.png'}}
                                                />
                                        </TouchableHighlight>

                                        <View style={{
                                            width: SCREEN_WIDTH - ITEM_HORIZONTAL_MARGIN * 2 - 2,
                                            backgroundColor: "#808080",
                                            height: 1
                                        }}/>

                                        <View
                                            style={{
                                                width: SCREEN_WIDTH - ITEM_HORIZONTAL_MARGIN * 2 - 2,
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

                                            <TouchableHighlight style={styles.listItemButton} onPress={() => this.setState({ promptVisible: true })}>
                                                <Text  style={styles.listItemButtonText}>
                                                    Comment
                                                </Text>
                                            </TouchableHighlight>

                                        </View>

                                        <FlatList
                                            data={item.comments}
                                            renderItem={({item}) =>
                                                <View  style={styles.commentItem}>
                                                    <Text  style={styles.listItemButtonText}>
                                                        {item}
                                                    </Text>
                                                </View>
                                            }
                                        />
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

                <Prompt
                    title="Say something"
                    placeholder="Start typing"
                    defaultValue="Hello"
                    visible={this.state.promptVisible}
                    onCancel={() => this.setState({ promptVisible: false, message: "You cancelled" })}
                    onSubmit={(value) => this.setState({ promptVisible: false, message: `You said "${value}"` })}/>
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
    commentItem: {
        width: SCREEN_WIDTH - 2 * ITEM_HORIZONTAL_MARGIN - 2,
        borderTopWidth: 1,
        borderTopColor: "#123456",
        justifyContent: 'flex-start'
    },
    listItemButton:{
        width: SCREEN_WIDTH / 2 - ITEM_HORIZONTAL_MARGIN - 2,
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
    },
    row: {
        borderColor: 'grey',
        borderWidth: 1,
        padding: 20,
        backgroundColor: '#3a5795',
        margin: 5,
    },
    text: {
        alignSelf: 'center',
        color: '#fff',
    },
    scrollview: {
        flex: 1,
    },
});

//
// module.exports = RefreshControlExample;