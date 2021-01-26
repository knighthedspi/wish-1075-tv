import React, { useEffect, useState } from "react"
import { Image, StyleSheet, View } from "react-native"
import MusicPlayer from "./music-player.component"
import { getHls, getPrograms } from './../services/api.service'
import { Program } from "../models/program"
import ProgramList from "./programs.component"
import LinearGradient from "react-native-linear-gradient"
import ProgramDetails from "./program-details.component"

import { DateTimeFormatter, LocalDateTime, ZonedDateTime, ZoneId } from '@js-joda/core'
import '@js-joda/timezone' // Just needs to be imported; registers itself automatically
import { Locale } from '@js-joda/locale_en-us' // Get `Locale` from the prebuilt package of your choice

// https://www.figma.com/file/E8cbUpuCHZBpntbK2BwG3S/Wish-TV-App?node-id=2%3A71

const TvContainer = () => {
    const [hlsUri, setHlsUri] = useState('https://www.wish1075.com/radio/wish.m3u8')
    const [programs, setPrograms] = useState<Program[]>([])
    const [hoveredProgram, setHoveredProgram] = useState<Program | null>(null)

    const date = ZonedDateTime.now(ZoneId.of('UTC+08:00'))
    // const day = date.format(DateTimeFormatter.ofPattern('E'))
    const formatter = DateTimeFormatter.ofPattern('e').withLocale(Locale.US)
    const day = date.format(formatter)
    let dayString = 'monday'

    switch(day) {
        case '2':
            dayString = 'tuesday'
            break
        case '3':
            dayString = 'wednesday'
            break
        case '4':
            dayString = 'thursday'
            break
        case '5':
            dayString = 'friday'
            break
        case '6':
            dayString = 'saturday'
            break
        case '7':
            dayString = 'sunday'
            break
    }

    useEffect(() => {
        getHls().then(x => {
            setHlsUri(x.posts[0].excerpt)
        }).catch(e => {
            console.log(e)
        })

        getPrograms(dayString).then(x => {
            setPrograms(x.programs[0])
        }).catch(e => {
            console.log(e)
        })
    }, [])

    return (
        <View style={styles.container}>
            <LinearGradient
                colors={['#533E08', '#260142', '#170128']}
            >
                <View style={styles.imageContainer}>
                    <Image source={require('./../images/wish-logo.png')}></Image>
                </View>

                <ProgramList programs={programs} hoveredProgram={hoveredProgram} onHoverProgram={setHoveredProgram}></ProgramList>

                <ProgramDetails program={hoveredProgram}></ProgramDetails>

                <MusicPlayer hlsUri={hlsUri}></MusicPlayer>
            </LinearGradient>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    imageContainer: {
        paddingTop: 10,
        paddingLeft: 20,
    }
})

export default TvContainer