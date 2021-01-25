import React, { useEffect, useState } from "react"
import { FlatList, Image, StatusBar, StyleSheet, Text, TouchableHighlight, View } from "react-native"
import { Colors } from "../constants/Colors"
import { Program } from "../models/program"
import { decode } from 'html-entities'

interface Props {
    programs: Program[]
}

const ProgramList = (props: Props) => {
    const renderItem = ({ item }: { item: Program }) => {
        return (
            <TouchableHighlight
              style={styles.listItem}>
                <View>
                  <View style={styles.imageContainer}>
                    <Image source={{uri: item.logo}} style={styles.image}/>
                  </View>
                  <View style={styles.detailsContainer}>
                    <Text style={styles.title}>{decode(item.title)}</Text>
                  </View>
                </View>
            </TouchableHighlight>
        )
    }

    return (
        <View style={styles.musicContainer}>
            <FlatList
                data={props.programs}
                renderItem={renderItem}
                keyExtractor={item => item.id}
            />
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
    listItem: {

    },
    imageContainer: {

    },
    image: {

    },
    detailsContainer: {

    },
    title: {

    },
    subtitle: {

    }
})

export default ProgramList