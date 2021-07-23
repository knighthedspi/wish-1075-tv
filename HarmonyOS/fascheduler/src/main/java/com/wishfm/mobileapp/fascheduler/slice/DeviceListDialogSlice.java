package com.wishfm.mobileapp.fascheduler.slice;

import com.wishfm.mobileapp.fascheduler.ResourceTable;
import com.wishfm.mobileapp.fascheduler.provider.DeviceItemProvider;
import com.wishfm.mobileapp.fascheduler.utils.AZUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.continuation.DeviceConnectState;
import ohos.aafwk.ability.continuation.IContinuationDeviceCallback;
import ohos.aafwk.ability.continuation.IContinuationRegisterManager;
import ohos.aafwk.ability.continuation.RequestCallback;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;
import ohos.agp.window.dialog.ToastDialog;
import ohos.distributedschedule.interwork.DeviceInfo;
import ohos.distributedschedule.interwork.DeviceManager;
import ohos.distributedschedule.interwork.IInitCallback;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.RemoteException;

import java.util.List;

import static com.wishfm.mobileapp.fascheduler.utils.AZUtil.REQUEST_CODE_TO_START_ABILITY;

public class DeviceListDialogSlice extends AbilitySlice implements DeviceItemProvider.Listener {
    private static final HiLogLabel LABEL = new HiLogLabel(3, 0xD001100, "DeviceListDialogSlice");


    private List<DeviceInfo> deviceInfoList;
    private boolean isPlaying = false;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_layout_ability_device_list);

        isPlaying = intent.getBooleanParam("PlayLiveStream", false);
        //register();   //Used for testing and can be ignored
        initListContainer();
    }

    private void initListContainer() {
        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_list_container);
        deviceInfoList =
                DeviceManager.getDeviceList(DeviceInfo.FLAG_GET_ONLINE_DEVICE);
        DeviceItemProvider deviceItemProvider = new DeviceItemProvider(deviceInfoList, this, this);
        listContainer.setItemProvider(deviceItemProvider);

        Text text = (Text) findComponentById(ResourceTable.Id_list_title);
        text.setText("Device List (total "+deviceInfoList.size()+")");
//
//        if (deviceInfoList != null)
//            //Showing a toast for testing
//            new ToastDialog(getContext())
//                    .setText("Devices = " + deviceInfoList.size())
//                    .show();
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregister();  //Used for testing and can be ignored
        //terminateAbility(); //Used for testing and can be ignored
    }

    @Override
    public void onItemClick(int position) {
//        new ToastDialog(getContext())
//                .setText("Selected = " + component.getTag())
//                .show();
        if (deviceInfoList != null && !deviceInfoList.isEmpty()) {
            DeviceInfo deviceInfo = deviceInfoList.get(position);
            String deviceId = deviceInfo.getDeviceId();
            switch (deviceInfo.getDeviceType()) {
                case SMART_WATCH:
                    startRemoteAbilityOnSmartWatch(deviceId);
                    break;
                case SMART_TV:
                    startRemoteActivityWithTV(deviceId);
                    break;
                default:
                    if (AZUtil.isTV(this)) {
                        startRemoteActivityWithTV(deviceId);
                    } else {
                        startRemoteActivityOnOthers(deviceId);
                    }
                    break;
            }
        }
    }

    private void startRemoteAbilityOnSmartWatch(String deviceId) {

        try {
            DeviceManager.initDistributedEnvironment(deviceId, new IInitCallback() {
                @Override
                public void onInitSuccess(String s) {
                    HiLog.debug(LABEL, "onInitSuccess, deviceId="+s);
/*
            //Used for testing and can be ignored
                    Intent remoteIntent = new Intent();
                    Operation operation = new Intent.OperationBuilder()
                            .withDeviceId(deviceId)
                            .withBundleName("com.wishfm.mobileapp")
                            .withAbilityName("com.wishfm.mobileapp.famain.ServiceAbility")
                            .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE)
                            .build();
                    remoteIntent.setOperation(operation);
                    List<AbilityInfo> abilityInfoList = null;
                    try {
                        abilityInfoList = mContext.getBundleManager().queryAbilityByIntent(remoteIntent, 0, 0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    if (abilityInfoList != null && !abilityInfoList.isEmpty()) {
                        startAbility(remoteIntent);
                    }
*/
                    String bundleName = "com.wishfm.mobileapp";
                    String abilityName = "com.wishfm.mobileapp.famain.ServiceAbility";
                    AZUtil.startRemoteAbility(DeviceListDialogSlice.this, deviceId, bundleName, abilityName, isPlaying);

                    startExitActivity();
                }

                @Override
                public void onInitFailure(String s, int i) {
                    HiLog.debug(LABEL, "startRemoteActivity: onInitFailure, deviceId="+s+", i="+i);

                    new ToastDialog(DeviceListDialogSlice.this)
                            .setText("Distributed.onInitFailure")
                            .show();
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void startRemoteActivityWithTV(String deviceId) {
        HiLog.debug(LABEL, "Start distribution on device with deviceId=" + deviceId);
        String bundleName = "com.wishfm.mobileapp";
        String abilityName = "com.wishfm.mobileapp.fascheduler.MainAbility";
        String actionName = "action.fascheduler.OpenApkDialogSlice";
        AZUtil.startRemoteAbility(this, deviceId, bundleName, abilityName, actionName, isPlaying);

        startExitActivity();
    }

    private void startRemoteActivityOnOthers(String deviceId) {
        try {
            DeviceManager.initDistributedEnvironment(deviceId, new IInitCallback() {

                @Override
                public void onInitSuccess(String s) {
                    HiLog.debug(LABEL, "onInitSuccess, deviceId="+s);

                    String bundleName = "com.wishfm.mobileapp";
                    String abilityName = "com.wishfm.mobileapp.fascheduler.MainAbility";
                    String actionName = "action.fascheduler.OpenApkDialogSlice";
                    AZUtil.startRemoteAbility(DeviceListDialogSlice.this, deviceId, bundleName, abilityName, actionName, isPlaying);

                    startExitActivity();
                }

                @Override
                public void onInitFailure(String s, int i) {
                    HiLog.debug(LABEL, "startRemoteActivity: onInitFailure, deviceId="+s+", i="+i);

                    new ToastDialog(DeviceListDialogSlice.this)
                            .setText("Distributed.onInitFailure")
                            .show();
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultData) {
        HiLog.debug(LABEL, "onAbilityResult: requestCode="+requestCode+", i="+resultCode+", i="+resultData);
        switch (requestCode) {
            case REQUEST_CODE_TO_START_ABILITY:
                break;
            default:
                break;
        }
    }

    //*******************************************************
    //***** Used for testing and can be ignored ******
    //*******************************************************


    // 注册FA流转管理服务后返回的Ability token
    private int abilityToken;
    // 用户在设备列表中选择设备后返回的设备ID
    private String selectDeviceId;
    // 用户是否已发起可拉回流转流程
    private boolean isReversibly = false;
    // 获取FA流转管理服务管理类
    private IContinuationRegisterManager continuationRegisterManager;
    // 设置监听FA流转管理服务设备状态变更的回调
    private IContinuationDeviceCallback callback = new IContinuationDeviceCallback() {
        @Override
        public void onDeviceConnectDone(String deviceId,String deviceType) {
            // 在用户选择设备后设置设备ID
            selectDeviceId = deviceId;
            //迁移
            continueAbilityReversibly(deviceId);
            //拉起设备B的Slice B(config.json中需要配置action，Ability中使用addActionRoute设置action与Slice的对应关系)
//            goSlice(selectDeviceId,"action.goSlice.MainAbilitySlice2");
            //修改为流转结束状态
            updateConnectStatus(deviceId, DeviceConnectState.CONNECTED.getState());
        }

        @Override
        public void onDeviceDisconnectDone(String deviceId) {
            updateConnectStatus(deviceId, DeviceConnectState.IDLE.getState());
        }
    };

    private void updateConnectStatus(String deviceId,int status){
        continuationRegisterManager.updateConnectStatus(abilityToken,deviceId,status,null);
    }

    // 设置注册FA流转管理服务回调
    private RequestCallback requestCallback = new RequestCallback() {
        @Override
        public void onResult(int result) {
            abilityToken = result;
        }
    };

    private void register() {
        continuationRegisterManager = getContinuationRegisterManager();
        // 注册FA流转管理服务
        continuationRegisterManager.register(getBundleName(), null, callback, requestCallback);
    }

    private void unregister(){
        // 解注册FA流转管理服务
        continuationRegisterManager.unregister(abilityToken, requestCallback);
//        continuationRegisterManager.disconnect();
    }

    private void startExitActivity() {
        String packageName, activityName, actionName;
        if (AZUtil.isTV(this)) {
            packageName = "com.wish1075";
            activityName = "com.wish1075.ExitActivity";
            actionName = "android.intent.action.Exit";
        } else {
            packageName = "com.wishfm.mobileapp";
            activityName = "com.wishfm.mobileapp.ExitActivity";
            actionName = "com.huawei.activity.action.ExitActivity";
        }
        AZUtil.startLocalActivity(this, packageName, activityName, actionName, true);

        terminateAbility();
    }
}
