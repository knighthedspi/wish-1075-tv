import React from "react"
import { StyleSheet, Text, View } from "react-native"
import { Colors } from "../constants/Colors"
import { Program } from "../models/program"
import { decode } from 'html-entities'

interface Props {
    program: Program | null
}

const ProgramDetails = (props: Props) => {
    const { program } = props

    if (!program) {
        return null
    }

    return (
        <View style={styles.programDetailsContainer}>
            <View style={styles.titleContainer}>
                <Text style={styles.title}>{ decode(program.title) }</Text>
                {
                    program.live ?
                    <View style={styles.liveContainer}>
                        <Text style={styles.liveLabel}>NOW PLAYING</Text>
                    </View> : null
                }
            </View>

            <View style={styles.subtitleContainer}>
                <Text style={styles.subtitle}>{ program.dj ? `with ${program.dj}` : ''}</Text>
            </View>

            <View style={styles.detailsContainer}>
                <Text style={styles.details}>{ decode(program.excerpt) }</Text>
            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    programDetailsContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: 10,
    },
    titleContainer: {
        flexDirection: 'row',
    },
    title: {
        color: Colors.text,
        fontSize: 25,
        fontWeight: 'bold',
    },
    subtitleContainer: {
        marginBottom: 10,
    },
    subtitle: {
        color: Colors.text,
        fontSize: 11,
        textTransform: 'uppercase',
    },
    detailsContainer: {
        height: 25,
    },
    details: {
        color: Colors.text,
        fontSize: 10,
        width: 700,
        textAlign: 'center',
    },
    liveContainer: {
        marginLeft: 10,
        padding: 3,
        borderRadius: 3,
        backgroundColor: Colors.red,
        height: 20,
    },
    liveLabel: {
        color: Colors.text,
        fontSize: 10,
        fontWeight: 'bold',
    },
})

export default ProgramDetails