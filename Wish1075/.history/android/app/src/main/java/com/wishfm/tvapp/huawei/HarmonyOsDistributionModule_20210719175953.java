package com.wishfm.tvapp.huawei;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.huawei.ohos.localability.AbilityUtils;

public class HarmonyOsDistributionModule extends ReactContextBaseJavaModule {
    HarmonyOsDistributionModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "HarmonyOsDistributionModule";
    }

    @ReactMethod
    public void showDevices(boolean isPlaying) {
        Intent intent = new Intent();
        intent.setClassName("com.wishfm.mobileapp",
                "com.wishfm.mobileapp.fascheduler.MainAbility");
        intent.setAction("action.fascheduler.DeviceListDialogSlice");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("PlayLiveStream", isPlaying);
        AbilityUtils.startAbility(getReactApplicationContext(), intent);
    }
}