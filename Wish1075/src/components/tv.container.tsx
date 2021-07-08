import React, { useEffect, useState } from "react"
import { Image, StyleSheet, View } from "react-native"
import MusicPlayer from "./music-player.component"
import { getHls, getPrograms } from './../services/api.service'
import { Program } from "../models/program"
import ProgramList from "./programs.component"
import LinearGradient from "react-native-linear-gradient"
import ProgramDetails from "./program-details.component"

import { DateTimeFormatter, Period, ZonedDateTime, ZoneId } from '@js-joda/core'
import '@js-joda/timezone'
import { Locale } from '@js-joda/locale_en-us'

// https://www.figma.com/file/E8cbUpuCHZBpntbK2BwG3S/Wish-TV-App?node-id=2%3A71

const TvContainer = () => {
    const [hlsUri, setHlsUri] = useState('https://play.wish1075.com/radio/wish.m3u8')
    const [programs, setPrograms] = useState<Program[]>([])
    const [hoveredProgram, setHoveredProgram] = useState<Program | null>(null)

    const dayString = getCurrentWeekDay()

    useEffect(() => {
        getHls().then(x => {
            setHlsUri(x.posts[0].excerpt)
        }).catch(e => {
            console.log(e)
        })

        getPrograms(dayString).then(x => {
            const programs = x.programs[0]
            setLiveProgram(programs)

            const liveProgram = programs.find((x: Program) => x.live)
            setHoveredProgram(liveProgram)

            setPrograms(programs)
        }).catch(e => {
            console.log(e)
        })
    }, [])

    const hoverProgram = (program: Program) => {
        // const clonedProgram = [...programs]
        // setLiveProgram(clonedProgram)
        // setPrograms(clonedProgram)
        setHoveredProgram(program)
    }

    return (
        <View style={styles.container}>
            <LinearGradient
                // colors={['#533E08', '#260142', '#170128']} // brown gradient
                colors={['#531A08', '#170128']} // orange gradient
                // colors={['#53084B', '#170128']} // violet gradient
            >
                <View style={styles.imageContainer}>
                    <Image source={require('./../images/wish-logo.png')}></Image>
                </View>

                <ProgramList programs={programs} hoveredProgram={hoveredProgram} onHoverProgram={hoverProgram}></ProgramList>

                <ProgramDetails program={hoveredProgram}></ProgramDetails>

                <MusicPlayer hlsUri={hlsUri}></MusicPlayer>
            </LinearGradient>
        </View>
    )
}

const getCurrentWeekDay = () => {
    const day = getCurrentPhtDateTime('E')
    let dayString = 'monday'

    switch(day) {
        case 'Tue':
        case '2':
            dayString = 'tuesday'
            break
        case 'Wed':
        case '3':
            dayString = 'wednesday'
            break
        case 'Thu':
        case '4':
            dayString = 'thursday'
            break
        case 'Fri':
        case '5':
            dayString = 'friday'
            break
        case 'Sat':
        case '6':
            dayString = 'saturday'
            break
        case 'Sun':
        case '7':
            dayString = 'sunday'
            break
    }

    return dayString
}

const setLiveProgram = (programs: Program[]) => {
    programs.forEach(program => {
        const { time_start, time_end } = program
        const myDate = '2020-01-23T'
        const start = myDate + convertTime12to24(time_start)
        const end = myDate + convertTime12to24(time_end, true)

        const currentTime = getCurrentPhtDateTime('HH:mm')
        const current = myDate + currentTime

        const startDate = new Date(start) 
        const endDate = new Date(end)
        const currentDate = new Date(current)

        program.live = (startDate === currentDate || endDate === currentDate || (startDate < currentDate && endDate > currentDate))
    })
}

const getCurrentPhtDateTime = (format: string) => {
    const date = ZonedDateTime.now(ZoneId.of('UTC+08:00'))
    const formatter = DateTimeFormatter.ofPattern(format).withLocale(Locale.US)
    const currentTime = date.format(formatter)

    return currentTime;
}

const convertTime12to24 = (time12h: string, isEnd: boolean = false) => {
    const [time, modifier] = time12h.split(' ');
  
    let [hours, minutes] = time.split(':');

    if (time12h === '12:00 AM' && isEnd) {
        hours = '23'
        minutes = '59'
    }

    if (hours === '12') {
      hours = '00';
    }
  
    if (modifier === 'PM') {
      hours = (parseInt(hours, 10) + 12) + '';
    }
  
    return `${hours}:${minutes}`;
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