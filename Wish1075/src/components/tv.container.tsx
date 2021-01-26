import React, { useEffect, useState } from "react"
import { Image, StyleSheet, View } from "react-native"
import MusicPlayer from "./music-player.component"
import { getHls, getPrograms } from './../services/api.service'
import { Program } from "../models/program"

// https://www.figma.com/file/E8cbUpuCHZBpntbK2BwG3S/Wish-TV-App?node-id=2%3A71

const TvContainer = () => {
    const [hlsUri, setHlsUri] = useState('https://www.wish1075.com/radio/wish.m3u8')
    const [programs, setPrograms] = useState<Program[]>([])
    const [hoveredProgram, setHoveredProgram] = useState<Program | null>(null)

    useEffect(() => {
        getHls().then(x => {
            setHlsUri(x.posts[0].excerpt)
        }).catch(e => {
            console.log(e)
        })

        getPrograms().then(x => {
            setPrograms(x.programs)
        }).catch(e => {
            console.log(e)
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