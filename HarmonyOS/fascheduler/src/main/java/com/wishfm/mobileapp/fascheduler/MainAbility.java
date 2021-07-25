package com.wishfm.mobileapp.fascheduler;

import com.wishfm.mobileapp.fascheduler.slice.DeviceListDialogSlice;
import com.wishfm.mobileapp.fascheduler.slice.OpenApkDialogSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbPalette;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.PopupDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.agp.window.service.WindowManager;
import ohos.bundle.IBundleManager;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;
import static ohos.security.SystemPermission.DISTRIBUTED_DATASYNC;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);

        initWindow();

        super.setMainRoute(DeviceListDialogSlice.class.getName());
        initAliceActionRoute();
        requestPermission();
    }

    private void initWindow() {
/*
        WindowManager.getInstance().getTopWindow().ifPresent(window -> {
            window.setWindowLayout(1000, 600);
            window.setLayoutAlignment(LayoutAlignment.CENTER);
            window.setTransparent(true);
        });
*/
        getWindow().setTransparent(true);
        //getWindow().setBackgroundColor(RgbPalette.TRANSPARENT);
    }

    private void requestPermission() {
        if (verifySelfPermission(DISTRIBUTED_DATASYNC) != IBundleManager.PERMISSION_GRANTED) {
            if (canRequestPermission(DISTRIBUTED_DATASYNC)) {
                requestPermissionsFromUser(new String[]{"ohos.permission.DISTRIBUTED_DATASYNC"}, 0);
            }
        }
    }

    private void initAliceActionRoute() {
        addActionRoute("action.fascheduler.DeviceListDialogSlice", DeviceListDialogSlice.class.getName());
        addActionRoute("action.fascheduler.OpenApkDialogSlice", OpenApkDialogSlice.class.getName());
    }
}
