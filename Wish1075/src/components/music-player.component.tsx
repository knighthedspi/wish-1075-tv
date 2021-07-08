import React, { useEffect, useState } from "react"
import { Image, NativeModules, StyleSheet, TouchableHighlight, View } from "react-native"
import { Colors } from "../constants/Colors"
import Video from 'react-native-video'
import Icon from 'react-native-ionicons'

interface Props {
    hlsUri: string
}

const MusicPlayer = (props: Props) => {
    const [playing, setPlaying] = useState(false)
    const [focus, setFocus] = useState(false)
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

    const cast = () => {
        NativeModules.HarmonyOsDistributionModule.showDevices(playing);
    }

    return (
        <View style={styles.musicContainer}>
            <View style={styles.buttonContainer}>
                <View style={focus ? styles.customBorderBig : styles.customBorder}>
                    {
                        playing ?
                        <TouchableHighlight
                            underlayColor='transparent'
                            onPress={pause}
                            onFocus={() => setFocus(true)}
                            onBlur={() => setFocus(false)}>
                            <Icon name="pause" size={focus ? 45 : 35} color={Colors.buttonColor}  />
                        </TouchableHighlight> :
                        <TouchableHighlight
                            onPress={play}
                            onFocus={() => setFocus(true)}
                            onBlur={() => setFocus(false)}
                            disabled={!hlsUri}
                            underlayColor='transparent'
                            style={styles.buttonPlay}>
                            <Icon name="play" size={focus ? 45 : 35} color={ !hlsUri ? Colors.buttonColorDisable : Colors.buttonColor } />
                        </TouchableHighlight>
                    }
                </View>
                <View>
                    <TouchableHighlight onPress={cast}>
                        <Image source={require('./../images/cast.png')} style={styles.image}/>
                    </TouchableHighlight>
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
        height: 100,
        marginTop: 20,
    },
    buttonContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'column',
    },
    backgroundVideo: {
        position: 'absolute',
        bottom: 0,
        right: 0,
        backgroundColor: Colors.text,
        width: 1,
        height: 1
    },
    customBorder: {
        width: 60,
        height: 60,
        borderRadius: 60 / 2,
        backgroundColor: 'transparent',
        borderColor: '#B69665',
        borderWidth: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    customBorderBig: {
        width: 70,
        height: 70,
        borderRadius: 70 / 2,
        backgroundColor: 'transparent',
        borderColor: '#B69665',
        borderWidth: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    buttonPlay: {
        marginLeft: 7
    },
    image: {
        marginTop: 10,
        width: 30,
        height: 30,
    }
})

export default MusicPlayer