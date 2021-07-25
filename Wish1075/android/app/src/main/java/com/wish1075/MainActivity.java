package com.wish1075;

import android.content.Intent;
import android.os.Bundle;
import org.devio.rn.splashscreen.SplashScreen;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class MainActivity extends ReactActivity {

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "Wish1075";
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    SplashScreen.show(this);
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    boolean isPlaying = intent.getBooleanExtra("PlayLiveStream", false);

    ReactInstanceManager mReactInstanceManager = getReactNativeHost().getReactInstanceManager();
    mReactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
      public void onReactContextInitialized(ReactContext validContext) {
        if (isPlaying) {
          validContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                  .emit("PlayLiveStream", null);
        }
      }
    });
  }
}
