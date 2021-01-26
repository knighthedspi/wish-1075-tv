import React from "react"
import { StyleSheet, Text, View } from "react-native"
import { Colors } from "../constants/Colors"
import { Program } from "../models/program"

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
                <Text style={styles.title}>{ program.title }</Text>
            </View>

            <View style={styles.subtitleContainer}>
                <Text style={styles.subtitle}>With { program.dj }</Text>
            </View>

            <View style={styles.detailsContainer}>
                <Text style={styles.details}>{ program.excerpt }</Text>
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
    }
})

export default ProgramDetails