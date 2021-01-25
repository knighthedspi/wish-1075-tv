import React, { useEffect } from "react"
import { Image, StyleSheet, View } from "react-native"
import MusicPlayer from "./music-player.component"

const TvContainer = () => {
    useEffect(() => {
        // fetch programs
        // https://www.wish1075.com/api/programs/get_programs_by_day/
        // https://www.wish1075.com/api/programs/get_programs_by_day/?day=saturday

        // https://www.wish1075.com/api/get_recent_posts/?post_type=streaming
    }, [])

    return (
        <View style={styles.container}>
            <View style={styles.imageContainer}>
                <Image source={require('./../images/wish-logo.png')}></Image>
            </View>

            <MusicPlayer></MusicPlayer>
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