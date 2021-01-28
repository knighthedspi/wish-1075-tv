import React, { useEffect } from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  useTVEventHandler,
  HWKeyEvent,
} from 'react-native';
import SplashScreen from 'react-native-splash-screen';

import 'react-native/tvos-types.d';
import TvContainer from './src/components/tv.container';

declare const global: {HermesInternal: null | {}};

const App = () => {
  const [lastEventType, setLastEventType] = React.useState('');
  const myTVEventHandler = (evt: HWKeyEvent) => {
    setLastEventType(evt.eventType);
  };
  useTVEventHandler(myTVEventHandler);

  useEffect(() => {
    SplashScreen.hide();
  }, [])

  return (
    <>
      <SafeAreaView>
        <ScrollView
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          <TvContainer></TvContainer>
        </ScrollView>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: '#170128',
    height: '100%'
  },
});

export default App;
