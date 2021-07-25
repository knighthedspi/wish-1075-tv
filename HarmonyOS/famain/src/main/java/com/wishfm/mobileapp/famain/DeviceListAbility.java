package com.wishfm.mobileapp.famain;

import com.wishfm.mobileapp.famain.slice.DeviceListAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class DeviceListAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(DeviceListAbilitySlice.class.getName());
    }
}
