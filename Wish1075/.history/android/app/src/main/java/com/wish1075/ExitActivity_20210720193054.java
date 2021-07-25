package com.wish1075;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class ExitActivity extends ReactActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        boolean isPlaying = intent.getBooleanExtra("PlayLiveStream", false);
        ReactContext context = getReactNativeHost().getReactInstanceManager().getCurrentReactContext();

        if (context != null) {
            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("StopLiveStream", null);
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntent);
        }

        finishActivity(1);
    }
}
