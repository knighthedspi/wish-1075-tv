import React, { useEffect, useState } from "react"
import { Image, StyleSheet, View } from "react-native"
import MusicPlayer from "./music-player.component"
import { getHls, getPrograms } from './../services/api.service'
import { Program } from "../models/program"

const TvContainer = () => {
    const [hlsUri, setHlsUri] = useState('')
    const [programs, setPrograms] = useState<Program[]>([])
    const [hoveredProgram, setHoveredProgram] = useState<Program | null>(null)

    useEffect(() => {
        getHls().then(x => {
            setHlsUri(x.posts[0].excerpt)
        })

        getPrograms().then(x => {
            setPrograms(x.programs)
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