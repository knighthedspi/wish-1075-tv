import React, { useEffect, useState } from "react"
import { Image, StyleSheet, View } from "react-native"
import MusicPlayer from "./music-player.component"
import { getHls } from './../services/api.service'

const TvContainer = () => {
    const [hlsUri, setHlsUri] = useState('')

    useEffect(() => {
        // fetch programs
        // https://www.wish1075.com/api/programs/get_programs_by_day/
        // https://www.wish1075.com/api/programs/get_programs_by_day/?day=saturday

        getHls().then(x => {
            setHlsUri(x.posts[0].excerpt)
        })
    }, [])

    return (
        <View style={styles.container}>
            <View style={styles.imageContainer}>
                <Image source={require('./../images/wish-logo.png')}></Image>
            </View>

            <MusicPlayer hlsUri={hlsUri}></MusicPlayer>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        // backgroundColor: '#533E08',
        flex: 1,
    },
    imageContainer: {
        padding: 10,
    }
})

export default TvContainer