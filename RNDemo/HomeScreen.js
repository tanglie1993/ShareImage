import React, {Component} from 'react';
import {FlatList, Image, ScrollView, StyleSheet, Text, TouchableOpacity, View} from 'react-native';
import {Constants} from "./Constants";

export class HomeScreen extends Component {

    HEIGHT = 100;
    WIDTH = 100;

    static navigationOptions = {
        title: 'Welcome',
    };

    constructor(props) {
        super(props);
        this.state = {
            listData : null
        };
    }

    componentDidMount()
    {
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

    render()
    {
        if (!this.state.listData) {
            return this.renderLoadingView();
        }
        return this.renderContent(this.state.listData);
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

    renderContent(listData) {
        const { navigate } = this.props.navigation;
        return(
            <ScrollView>

                <View style={styles.container}>

                    <View style={styles.container}>
                        <FlatList
                            data={listData}
                            renderItem={({item}) =>
                                <TouchableOpacity onPress={() => navigate('Detail')}>
                                    <Image
                                        style={{width: this.WIDTH, height: this.HEIGHT}}
                                        source={{uri: Constants.BASE_URL + '/image/' + Constants.USER_ID + '/' + item.imageUrl + '.png'}}
                                    />
                                </TouchableOpacity>
                            }
                        />
                    </View>
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