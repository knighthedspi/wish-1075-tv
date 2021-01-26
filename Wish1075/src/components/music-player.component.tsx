import React, { useEffect, useState } from "react"
import { Image, StyleSheet, TouchableHighlight, View } from "react-native"
import { Colors } from "../constants/Colors"
import Video from 'react-native-video'
import Icon from 'react-native-ionicons'

interface Props {
    hlsUri: string
}

const MusicPlayer = (props: Props) => {
    const [playing, setPlaying] = useState(false)
    const { hlsUri } = props

    const videoError = (error: any) => {
        console.log(error)
        setPlaying(false)
    }

    const play = () => {
        setPlaying(true)
    }

    const pause = () => {
        setPlaying(false)
    }

    return (
        <View style={styles.musicContainer}>
            <View style={styles.buttonContainer}>
                <View style={styles.container}>
                    {
                        playing ?
                        <TouchableHighlight onPress={pause}>
                            <Icon name="pause" size={35} color={Colors.buttonColor} />
                        </TouchableHighlight> :
                        <TouchableHighlight onPress={play} disabled={!hlsUri} style={styles.buttonPlay}>
                            <Icon name="play" size={35} color={ !hlsUri ? Colors.buttonColorDisable : Colors.buttonColor } />
                        </TouchableHighlight>
                    }
                </View>
            </View>

            <Video source={{ uri: hlsUri }}
                paused={!playing || !hlsUri }
                audioOnly={true}
                playInBackground={false}
                playWhenInactive={false}
                ignoreSilentSwitch={'ignore'}
                onError={videoError}
                style={styles.backgroundVideo} />
        </View>
    )

}

const styles = StyleSheet.create({
    musicContainer: {
        width: '100%',
        height: 80,
        marginTop: 30
    },
    buttonContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        // borderWidth: 1,
        // borderColor: 'red'
    },
    backgroundVideo: {
        position: 'absolute',
        bottom: 0,
        right: 0,
        backgroundColor: Colors.text,
        width: 1,
        height: 1
    },
    container: {
        width: 60,
        height: 60,
        borderRadius: 60 / 2,
        backgroundColor: 'transparent',
        borderColor: '#B69665',
        borderWidth: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    buttonPlay: {
        marginLeft: 7
    }
})

export default MusicPlayer