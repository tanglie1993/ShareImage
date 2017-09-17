import React from 'react';
import {Image, TextInput, View} from 'react-native';

export class UploadScreen extends React.Component {

    HEIGHT = 500;
    WIDTH = 500;

    static navigationOptions = {
        title: 'Upload',
    };

    constructor(props) {
        super(props);
        this.state = { text: 'Useless Placeholder' };
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
                    onChangeText={(text) => this.setState({text})}
                    value={this.state.text}
                />

                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    onChangeText={(text) => this.setState({text})}
                    value={this.state.text}
                />
            </View>

        );
    }
}