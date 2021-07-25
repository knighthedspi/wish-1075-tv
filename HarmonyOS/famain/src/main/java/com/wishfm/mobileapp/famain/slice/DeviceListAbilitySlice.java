package com.wishfm.mobileapp.famain.slice;

import com.wishfm.mobileapp.famain.ResourceTable;
import com.wishfm.mobileapp.famain.provider.SampleItemProvider;
import com.wishfm.mobileapp.famain.utils.AZUtil;
import com.wishfm.mobileapp.famain.utils.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.window.dialog.ToastDialog;
import ohos.distributedschedule.interwork.DeviceInfo;
import ohos.distributedschedule.interwork.DeviceManager;
import ohos.distributedschedule.interwork.IInitCallback;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.RemoteException;

import java.util.List;

import static com.wishfm.mobileapp.famain.utils.AZUtil.REQUEST_CODE_TO_START_ABILITY;

public class DeviceListAbilitySlice extends AbilitySlice implements Component.ClickedListener {
    private static final HiLogLabel LABEL = new HiLogLabel(3, 0xD001100, "DeviceListAbilitySlice");

    private List<DeviceInfo> deviceInfoList;
    private List<DeviceInfo> deviceInfoList1;
    private List<DeviceInfo> deviceInfoList2;

    private AbilitySlice mContext;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_layout_ability_device_list);
        initListContainer();

        mContext = this;
    }

    private void initListContainer() {
        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_list_container);
        deviceInfoList =
                DeviceManager.getDeviceList(DeviceInfo.FLAG_GET_ONLINE_DEVICE);
        SampleItemProvider sampleItemProvider = new SampleItemProvider(deviceInfoList, this, this);
        listContainer.setItemProvider(sampleItemProvider);

//        if (deviceInfoList != null)
//            //Showing a toast for testing
//            new ToastDialog(getContext())
//                    .setText("Devices = " + deviceInfoList.size())
//                    .show();
    }

    @Override
    public void onClick(Component component) {

        int selectedIndex = (int) component.getTag();
        if (deviceInfoList != null && !deviceInfoList.isEmpty()) {
            DeviceInfo deviceInfo = deviceInfoList.get(selectedIndex);
//            new ToastDialog(getContext())
//                    .setText("Casted")
//                    .setAlignment(LayoutAlignment.HORIZONTAL_CENTER)
//                    .setDuration(3)
//                    .show();
            startRemoteAbility(deviceInfo.getDeviceId());
        }
    }

    private void startRemoteAbility(String deviceId) {

        try {
            DeviceManager.initDistributedEnvironment(deviceId, new IInitCallback() {
                @Override
                public void onInitSuccess(String s) {
                    HiLog.debug(LABEL, "connectAbility: onInitSuccess, deviceId="+s);

                    String bundleName = "com.wishfm.mobileapp";
                    String abilityName = "com.wishfm.mobileapp.fascheduler.MainAbility";
                    String actionName = "action.fascheduler.OpenApkDialogSlice";

                    AZUtil.startRemoteAbility(mContext, deviceId, bundleName, abilityName, actionName);
                    LogUtil.warn("WISH", "======terminate");

                    mContext.terminateAbility(7);
                    mContext.terminate();
                }

                @Override
                public void onInitFailure(String s, int i) {
                    HiLog.debug(LABEL, "startRemoteActivity: onInitFailure, deviceId="+s+", i="+i);

                    new ToastDialog(mContext)
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
}
