package com.wishfm.mobileapp.slice;

import com.wishfm.mobileapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.element.ShapeElement;
import ohos.data.distributed.common.KvManagerConfig;
import ohos.data.distributed.common.KvManagerFactory;
import ohos.data.distributed.device.DeviceInfo;
import ohos.multimodalinput.event.KeyEvent;
import ohos.utils.net.Uri;

public class MainAbilitySlice extends AbilitySlice {
    private static final int CORNER_RADIUS = 20;
    private static final int FOCUS_BACKGROUND_COLOR = 0xE5CCCCCC;
    private static final int UNFOCUS_BACKGROUND_COLOR = 0x0000FFFF;
    private String APK_PACKAGE_NAME;
    private String ACTION_VIEW;
    private String APP_GALLERY_PACKAGE_NAME;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        setupUI();
        setupLink();
    }

    private void setupUI() {
        super.setUIContent(ResourceTable.Layout_ability_main);
        Image storeButton = (Image) findComponentById(ResourceTable.Id_store_button);
        storeButton.setFocusable(Component.FOCUS_ENABLE);
        storeButton.setFocusChangedListener((component, hasFocus) -> {
            ShapeElement shapeElement = new ShapeElement();
            shapeElement.setCornerRadius(CORNER_RADIUS);
            if (hasFocus) {
                shapeElement.setRgbColor(RgbColor.fromArgbInt(FOCUS_BACKGROUND_COLOR));
                storeButton.setPixelMap(ResourceTable.Media_app_gallery);
            } else {
                shapeElement.setRgbColor(RgbColor.fromArgbInt(UNFOCUS_BACKGROUND_COLOR));
                storeButton.setPixelMap(ResourceTable.Media_app_gallery_unfocus);
            }
            storeButton.setBackground(shapeElement);
        });
        storeButton.setKeyEventListener((component, keyEvent) -> {
            if (keyEvent.isKeyDown()) {
                int keyCode = keyEvent.getKeyCode();
                if (keyCode == KeyEvent.KEY_ENTER || keyCode == KeyEvent.KEY_DPAD_CENTER) {
                    openAppGallery();
                }
            }
            return false;
        });
        storeButton.setClickedListener(component -> openAppGallery());
    }

    private void setupLink() {
        boolean isTV = isTV();
        APK_PACKAGE_NAME = isTV ? "com.wish1075" : "com.wishfm.mobileapp";
        ACTION_VIEW = "android.intent.action.VIEW";
        APP_GALLERY_PACKAGE_NAME = isTV ? "com.huawei.appmarket.tv" : "com.huawei.appmarket";
    }

    private boolean isTV() {
        DeviceInfo deviceInfo = KvManagerFactory.getInstance()
                .createKvManager(new KvManagerConfig(this))
                .getLocalDeviceInfo();
        // smart TV device type is 156
        return deviceInfo.getType().equals("156");
    }

    private void openAppGallery() {
        Uri uri = Uri.parse("appmarket://details?id=" + APK_PACKAGE_NAME);
        Intent intent = new Intent();
        Intent.OperationBuilder builder = new Intent.OperationBuilder();
        builder.withAction(ACTION_VIEW);
        builder.withUri(uri);
        builder.withBundleName(APP_GALLERY_PACKAGE_NAME);
        builder.withFlags(Intent.FLAG_ABILITY_NEW_MISSION);
        intent.setOperation(builder.build());
        startAbility(intent);
    }
}
