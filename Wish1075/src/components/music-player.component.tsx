import React, { useEffect, useState } from "react"
import { Image, StyleSheet, View } from "react-native"
import { Colors } from "../constants/Colors"
import Video from 'react-native-video'

interface Props {
    hlsUri: string
}

const MusicPlayer = (props: Props) => {
    const [playing, setPlaying] = useState(false)

    const videoError = (error: any) => {
        console.log(error)
        setPlaying(false)
    }

    useEffect(() => {
        setPlaying(true)
    }, [])

    const play = () => {
        setPlaying(true)
    }

    const pause = () => {
        setPlaying(false)
    }

    return (
        <View style={styles.musicContainer}>
            <Video source={{ uri: props.hlsUri }}
                paused={!playing }
                audioOnly={true}
                playInBackground={true}
                playWhenInactive={true}
                ignoreSilentSwitch={'ignore'}
                onError={videoError}
                style={styles.backgroundVideo} />
        </View>
    )

}

const styles = StyleSheet.create({
    musicContainer: {
        backgroundColor: 'red'
    },
    backgroundVideo: {
        position: 'absolute',
        bottom: 0,
        right: 0,
        backgroundColor: Colors.text,
        width: 1,
        height: 1
    },
})

export default MusicPlayer