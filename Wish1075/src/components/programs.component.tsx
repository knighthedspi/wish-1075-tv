import React, { useEffect, useState } from "react"
import { Dimensions, FlatList, Image, StyleSheet, Text, TouchableHighlight, View } from "react-native"
import { Colors } from "../constants/Colors"
import { Program } from "../models/program"
import { decode } from 'html-entities'
import LinearGradient from 'react-native-linear-gradient'

interface Props {
    programs: Program[]
    hoveredProgram: Program | null
    onHoverProgram: (program: Program) => void
}

const ProgramList = (props: Props) => {
    const { programs, hoveredProgram, onHoverProgram } = props

    const renderItem = (program: Program ) => {
        const isHovered = hoveredProgram?.id === program.id

        return (
            <TouchableHighlight
                key={program.id}
                style={styles.listItem}
                onFocus={() => {
                    onHoverProgram(program)
                }}>
                <View>
                    <View style={{ alignItems: 'flex-end' }}>
                        { isHovered ? <Text style={styles.title}>{program.time_start} - {program.time_end} (GMT+8)</Text> : <Text>&nbsp;</Text> }
                    </View>
                    {
                        isHovered ?
                        <LinearGradient
                            // start={{x: 0, y: 0}} end={{x: 1, y: 0}}
                            start={{x: 0.0, y: 0.0}}
                            end={{x: 1.0, y: 1.0}}
                            colors={['#FD3E86', '#FE6F01']}
                            style={styles.imageContainerHover}>
                            <Image source={{uri: program.logo}} style={styles.imageHover}/>
                        </LinearGradient> :
                        <View style={styles.imageContainer}>
                            <Image source={{uri: program.logo}} style={styles.image}/>
                        </View>
                    }
                </View>
            </TouchableHighlight>
        )
        // border-image-source: linear-gradient(135.58deg, #FF34AE -0.15%, #BA7051 55.14%, #FF7A00 100%);
    }

    return (
        <View style={styles.programContainer}>
            <FlatList
                data={programs}
                renderItem={({ item }: { item: Program }) => renderItem(item)}
                keyExtractor={(item: Program, index: number) => item.id}
                horizontal={true}
                contentContainerStyle={{ alignItems: 'center', alignContent: 'center' }}
            />
        </View>
    )
}

const styles = StyleSheet.create({
    programContainer: {
        // backgroundColor: 'black',
        marginLeft: 20,
        marginRight: 20,
        marginTop: 25,
    },
    listItem: {
        marginRight: 5,
    },
    imageContainer: {
        padding: 7,
        opacity: .5,
    },
    imageContainerHover: {
        padding: 7,
        opacity: 1,
    },
    image: {
        height: 180,
        width: 180,
        resizeMode: 'cover',
        opacity: 0.5
    },
    imageHover: {
        height: 170,
        width: 170,
        resizeMode: 'cover',
        // border-image-source: linear-gradient(135.58deg, #FF34AE -0.15%, #BA7051 55.14%, #FF7A00 100%);
    },
    detailsContainer: {
        paddingLeft: 20,
        paddingRight: 20,
    },
    title: {
        color: Colors.text,
        fontSize: 10,
        marginBottom: 2,
    },
    listContainer: {
        flex: 1,
        backgroundColor: Colors.background,
    },
    list: {
        minHeight: 100,
    }
})

export default ProgramList