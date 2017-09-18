import React from 'react';
import {Constants} from "./Constants";
import {Image, TextInput, View, Button} from 'react-native';

export class UploadScreen extends React.Component {

    HEIGHT = 300;
    WIDTH = 300;

    static navigationOptions = {
        title: 'Upload',
    };

    constructor(props) {
        super(props);
        this.state = {
            title: 'Title',
            content: 'Content'
        };
    }

    uploadPic(){
        let formData = new FormData();
        let file = {uri: this.props.navigation.state.params.param, type: 'multipart/form-data', name: '354365789'};

        formData.append("file",file);

        fetch(Constants.BASE_URL + "image?user_id=" + Constants.USER_ID,{
            method:'POST',
            headers:{
                'Content-Type':'multipart/form-data',
            },
            body:formData,
        })
            .then((response) => response.text() )
            .then((responseData)=>{

                console.log('responseData',responseData);
            })
            .catch((error)=>{console.error('error',error)});

    }

    render() {
        return (
            <View>
                <Image
                    style={{width: this.WIDTH, height: this.HEIGHT}}
                    source={{uri: this.props.navigation.state.params.param}}
                />

                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    onChangeText={(text) => this.state.title = text}
                    value={this.state.title}
                />

                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    onChangeText={(text) => this.state.content = text}
                    value={this.state.content}
                />

                <Button
                    onPress={() => this.uploadPic()}
                    title="Learn More"
                    color="#841584"
                    accessibilityLabel="Learn more about this purple button"
                />
            </View>

        );
    }
}