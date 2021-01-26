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
        const isHovered = hoveredProgram?.id  === program.id && hoveredProgram?.time_start === program.time_start

        return (
            <TouchableHighlight
                key={program.id + program.time_start}
                style={styles.listItem}
                onFocus={() => {
                    onHoverProgram(program)
                }}>
                <View>
                    <View style={styles.listItemHeader}>
                        {
                            isHovered && program.live ?
                            <View style={styles.liveContainer}>
                                <Text style={styles.liveLabel}>LIVE</Text>
                            </View> : null
                        }
                        { isHovered ? <Text style={styles.title}>{program.time_start} - {program.time_end} (GMT+8)</Text> : <Text>&nbsp;</Text> }
                    </View>
                    {
                        isHovered ?
                        <LinearGradient
                            start={{x: 0.0, y: 0.0}}
                            end={{x: 1.0, y: 1.0}}
                            colors={['#FD3E86', '#FE6F01']}
                            style={styles.imageContainerHover}>
                            {
                                program.logo ?
                                <Image source={{uri: program.logo}} style={styles.imageHover}/> :
                                <Image source={require('./../images/wish-album.png')} style={styles.imageHover}/>
                            }
                        </LinearGradient> :
                        <View style={styles.imageContainer}>
                            {
                                program.logo ?
                                <Image source={{uri: program.logo}} style={styles.imageHover}/> :
                                <Image source={require('./../images/wish-album.png')} style={styles.imageHover}/>
                            }
                        </View>
                    }
                </View>
            </TouchableHighlight>
        )
    }

    return (
        <View style={styles.programContainer}>
            <FlatList
                data={programs}
                renderItem={({ item }: { item: Program }) => renderItem(item)}
                keyExtractor={(item: Program, index: number) => item.id + item.time_start}
                horizontal={true}
                contentContainerStyle={{ alignItems: 'center', alignContent: 'center' }}
            />
        </View>
    )
}

const styles = StyleSheet.create({
    programContainer: {
        marginLeft: 20,
        marginRight: 20,
        marginTop: 25,
        minHeight: 200,
    },
    listItemHeader: {
        alignItems: 'flex-end',
        position: 'relative',
        marginBottom: 2
    },
    listItem: {
        marginRight: 5,
        paddingTop: 5,
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
    },
    detailsContainer: {
        paddingLeft: 20,
        paddingRight: 20,
    },
    title: {
        color: Colors.text,
        fontSize: 9,
        marginBottom: 2,
    },
    listContainer: {
        flex: 1,
        backgroundColor: Colors.background,
    },
    list: {
        minHeight: 100,
    },
    liveContainer: {
        position: 'absolute',
        left: 0,
        top: -2,
        padding: 1,
        paddingLeft: 4,
        paddingRight: 4,
        borderRadius: 3,
        backgroundColor: '#FF003D',
    },
    liveLabel: {
        color: Colors.text,
        fontSize: 10,
        fontWeight: 'bold',
    },
})

export default ProgramList