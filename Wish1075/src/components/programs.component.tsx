import React from "react"
import { FlatList, Image, StyleSheet, Text, TouchableHighlight, View } from "react-native"
import { Colors } from "../constants/Colors"
import { Program } from "../models/program"
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
                style={isHovered ? styles.listItemHover : styles.listItem}
                onFocus={() => {
                    onHoverProgram(program)
                }}>
                <View>
                    <View style={styles.listItemHeader}>
                        {
                            false ?
                            <View style={styles.liveContainer}>
                                <Text style={styles.liveLabel}>NOW PLAYING</Text>
                            </View> : null
                        }
                        { program.live ? <Text style={styles.title}>NOW PLAYING</Text> : <Text>&nbsp;</Text> }
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
                                <Image source={{uri: program.logo}} style={styles.image}/> :
                                <Image source={require('./../images/wish-album.png')} style={styles.imageHover}/>
                            }
                        </View>
                    }
                    { isHovered ? <Text style={styles.footer}>{program.time_start} - {program.time_end} (GMT+8)</Text> : <Text>&nbsp;</Text> }
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
        marginTop: 5,
        minHeight: 200,
    },
    listItemHeader: {
        alignItems: 'center',
        position: 'relative',
        marginBottom: 2
    },
    listItemHover: {
        marginRight: 10,
        paddingTop: 5,
    },
    listItem: {
        marginRight: 10,
        paddingTop: 5,
    },
    imageContainer: {
        opacity: .5,
    },
    imageContainerHover: {
        padding: 3,
        opacity: 1,
    },
    image: {
        height: 180,
        width: 180,
        resizeMode: 'cover',
        opacity: 0.5,
        marginTop: 3,
    },
    imageHover: {
        height: 180,
        width: 180,
        resizeMode: 'cover',
    },
    detailsContainer: {
        paddingLeft: 20,
        paddingRight: 20,
    },
    title: {
        color: Colors.text,
        marginBottom: 2,
        fontSize: 12,
        textTransform: 'uppercase',
    },
    footer: {
        color: Colors.text,
        marginBottom: 2,
        fontSize: 10,
        textTransform: 'uppercase',
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