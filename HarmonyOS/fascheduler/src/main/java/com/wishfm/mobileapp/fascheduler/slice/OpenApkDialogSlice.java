package com.wishfm.mobileapp.fascheduler.slice;

import com.wishfm.mobileapp.fascheduler.ResourceTable;
import com.wishfm.mobileapp.fascheduler.utils.AZUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.StackLayout;

public class OpenApkDialogSlice extends AbilitySlice implements Component.ClickedListener {
    private boolean isPlaying = false;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_layout_slice_notice);
        isPlaying = intent.getBooleanParam("PlayLiveStream", false);

        initComponents();

        startLocalActivity();  // The FA is directly started by default.
    }

    private void initComponents(){
        Button mButton = (Button) findComponentById(ResourceTable.Id_button_confirm);
        mButton.setClickedListener(this);

        StackLayout mStackLayout = (StackLayout) findComponentById(ResourceTable.Id_stacklayout);
        mStackLayout.setVisibility(Component.INVISIBLE);
    }

    @Override
    public void onClick(Component component) {
        startLocalActivity();
    }

    private void startLocalActivity() {
        String packageName = "com.wish1075";
        String activityName = "com.wishfm.tvapp.MainActivity";
        String actionName = "android.intent.action.MAINACTIVITY";
        AZUtil.startLocalActivity(this, packageName, activityName, actionName, isPlaying);
    }
}
