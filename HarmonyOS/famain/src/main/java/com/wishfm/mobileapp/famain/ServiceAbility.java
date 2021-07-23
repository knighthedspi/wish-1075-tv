package com.wishfm.mobileapp.famain;

import com.wishfm.mobileapp.famain.slice.PlayerAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.bundle.IBundleManager;

import static ohos.security.SystemPermission.DISTRIBUTED_DATASYNC;

public class ServiceAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(PlayerAbilitySlice.class.getName());

        requestPermission();
    }

    private void requestPermission() {
        if (verifySelfPermission(DISTRIBUTED_DATASYNC) != IBundleManager.PERMISSION_GRANTED) {
            if (canRequestPermission(DISTRIBUTED_DATASYNC)) {
                requestPermissionsFromUser(new String[]{"ohos.permission.DISTRIBUTED_DATASYNC"},1008);
            }
        }
    }
}
