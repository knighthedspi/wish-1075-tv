package com.wishfm.mobileapp.famain.utils;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;

public class AZUtil {

    public static final int REQUEST_CODE_TO_START_ABILITY = 1;

    public static void startLocalAbility(final AbilitySlice ability,
                                         final String bundleName, final String abilityName) {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withBundleName(bundleName)
                .withAbilityName(abilityName)
                .build();
        intent.setOperation(operation);
        ability.startAbility(intent);
    }

    public static void startRemoteAbility(final AbilitySlice ability, final String deviceId,
                                          final String bundleName, final String abilityName) {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId(deviceId)
                .withBundleName(bundleName)
                .withAbilityName(abilityName)
                .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE)
                .build();
        intent.setOperation(operation);
        ability.startAbility(intent);
    }

    public static void startRemoteAbility(final AbilitySlice ability, final String deviceId,
                                          final String bundleName, final String abilityName, final String actionName) {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId(deviceId)
                .withBundleName(bundleName)
                .withAbilityName(abilityName)
                .withAction(actionName)
                .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE)
                .build();
        intent.setOperation(operation);
        ability.startAbilityForResult(intent, REQUEST_CODE_TO_START_ABILITY);
    }

    public static void startLocalActivity(final AbilitySlice ability,
                                          final String packageName, final String activityName, final String actionName) {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withBundleName(packageName)
                .withAbilityName(activityName)
                .withAction(actionName)
                .withFlags(Intent.FLAG_NOT_OHOS_COMPONENT)
                .build();
        intent.setOperation(operation);
        ability.startAbility(intent);
    }

    public static void startRemoteActivity(final AbilitySlice ability, final String deviceId,
                                           final String packageName, final String activityName, final String actionName) {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId(deviceId)
                .withBundleName(packageName)
                .withAbilityName(activityName)
                .withAction(actionName)
                .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE)
                .build();
        intent.setOperation(operation);
        ability.startAbility(intent);
    }
}
