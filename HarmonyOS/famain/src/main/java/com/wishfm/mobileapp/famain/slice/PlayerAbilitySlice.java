package com.wishfm.mobileapp.famain.slice;

import com.wishfm.mobileapp.famain.ResourceTable;
import com.wishfm.mobileapp.famain.utils.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Image;
import ohos.agp.components.ProgressBar;
import ohos.app.dispatcher.task.Revocable;
import ohos.bundle.ElementName;
import ohos.data.DatabaseHelper;
import ohos.data.preferences.Preferences;
import ohos.event.commonevent.*;
import ohos.media.audio.AudioManager;
import ohos.media.audio.AudioRemoteException;
import ohos.multimodalinput.event.MmiPoint;
import ohos.multimodalinput.event.TouchEvent;
import ohos.rpc.RemoteException;
import ohos.telephony.RadioInfoManager;
import ohos.wifi.WifiDevice;

import java.net.HttpURLConnection;
import java.net.URL;


public class PlayerAbilitySlice extends AbilitySlice {
    public static final String APP_PREFERENCE_NAME = "currentPlayState";
    MmiPoint downPoint;
    MmiPoint movePoint;
    private Float endPositionX;
    private Float startPositionX;
    private Boolean moveRight = false;
    private String TAG = "PlayerPage";
    private Image imgPlayer;
    private Image imgVolumeLoud;
    private Image imgVolumeQuiet;
    private Image loadingBtn;
    private Image castBtn;
    private DirectionalLayout mainContent;
    private DirectionalLayout networkStatusContnt;
    private DirectionalLayout loadingImgContent;
    private ProgressBar perProgressBar;
    private Revocable revocable = null;
    private String currentStatus = null;
    private AudioManager audioManager = new AudioManager(this);
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    RadioInfoManager radioInfoManager = RadioInfoManager.getInstance(this);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_player);
        mainContent = (DirectionalLayout) findComponentById(ResourceTable.Id_main_content);
        imgPlayer = (Image) findComponentById(ResourceTable.Id_imgPlayer);
        imgVolumeLoud = (Image) findComponentById(ResourceTable.Id_imgVolumeLoud);
        loadingBtn = (Image) findComponentById(ResourceTable.Id_loading_img);
        imgVolumeQuiet = (Image) findComponentById(ResourceTable.Id_imgVolumeQuiet);
        castBtn = (Image) findComponentById(ResourceTable.Id_imgCast);
        loadingImgContent = (DirectionalLayout) findComponentById(ResourceTable.Id_loading_img_content);
        perProgressBar = (ProgressBar) findComponentById(ResourceTable.Id_VideoProgressBar);
        networkStatusContnt = (DirectionalLayout) findComponentById(ResourceTable.Id_network_status_content);
        //mainContent.setTouchEventListener(new touchHandler());  //removed by jacky
        animatorPropertyHandle();
        queryNetworkStatus();
        initMusicVolumn();
        currentStatus = getString("currentStatus");
        if (currentStatus != null && currentStatus.equals("pause")) {
            imgPlayer.setPixelMap(ResourceTable.Media_pause);
        }
        imgPlayer.setClickedListener(listener -> {
            controlMusicStatus();
        });
        imgVolumeLoud.setClickedListener(listener -> {
            setMusicVolumn(1);
        });
        imgVolumeQuiet.setClickedListener(listener -> {
            setMusicVolumn(-1);
        });
        castBtn.setClickedListener(listener -> {
            callCastAbility();
        });

        startPlayMusic();
    }

    private void callCastAbility() {
//        String bundleName = "com.wishfm.mobileapp";
//        String abilityName = "com.wishfm.mobileapp.famain.DeviceListAbility";
//        AZUtil.startLocalAbility(this, bundleName, abilityName); // Start the second page

        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withBundleName("com.wishfm.mobileapp")
                .withAbilityName("com.wishfm.mobileapp.famain.DeviceListAbility")
                .withAction(Intent.ACTION_PLAY)
                .build();
        intent.setOperation(operation);
        startAbilityForResult(intent, 7);
    }

    @Override
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == 7) {
            stopPlayMusic();
        }
    }


    @Override
    public void onActive() {
        super.onActive();
        getUITaskDispatcher().delayDispatch(() -> {
            mainContent.setVisibility(Component.VISIBLE);
        }, 1000);
    }

    private void queryNetworkStatus() {
        WifiDevice mWifiDevice = WifiDevice.getInstance(getContext());
        boolean isConnected = mWifiDevice.isConnected();
        if (!isConnected) {
            checkNetworkStatusByHttpRequest();
        }
    }

    private void checkNetworkStatusByHttpRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://www.wish1075.com/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(1000);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        LogUtil.error(TAG, "===========connected.");
                        networkStatusContnt.setVisibility(Component.INVISIBLE);
                        imgPlayer.setClickable(true);
                    }else {
                        LogUtil.error(TAG, "===========disconnected.");
                        networkStatusContnt.setVisibility(Component.VISIBLE);
                        imgPlayer.setClickable(false);
                    }
                } catch (Exception e) {
                    networkStatusContnt.setVisibility(Component.VISIBLE);
                    imgPlayer.setClickable(false);
                    e.printStackTrace();
                }
            }
        }).start();
    }
/*
    private void queryNetworkStatus() {
        WifiDevice mWifiDevice = WifiDevice.getInstance(getContext());
        boolean isConnected = mWifiDevice.isConnected();
        LogUtil.warn(TAG, "======" + isConnected);

        if (!isConnected) {
            networkStatusContnt.setVisibility(Component.VISIBLE);
            imgPlayer.setClickable(false);
        } else {
            checkNetworkStatusByHttpRequest();
            imgPlayer.setClickable(true);
        }
    }

    private void checkNetworkStatusByHttpRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://www.baidu.com/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    int code = connection.getResponseCode();
                    networkStatusContnt.setVisibility(Component.INVISIBLE);
                    LogUtil.warn(TAG, "======connected");
                } catch (Exception e) {
                    LogUtil.warn(TAG, "=======disconnected");
                    networkStatusContnt.setVisibility(Component.VISIBLE);
                    e.printStackTrace();
                }
            }
        }).start();
    }
*/
    class touchHandler implements Component.TouchEventListener {
        @Override
        public boolean onTouchEvent(Component component, TouchEvent touchEvent) {

            if (touchEvent.getAction() == TouchEvent.PRIMARY_POINT_DOWN) {
                downPoint = touchEvent.getPointerScreenPosition(0);
                startPositionX = downPoint.getX();
            }
            if (touchEvent.getAction() == TouchEvent.POINT_MOVE) {
                movePoint = touchEvent.getPointerScreenPosition(0);
                endPositionX = movePoint.getX();
                if (startPositionX < endPositionX) {
                    moveRight = true;
                } else {
                    moveRight = false;
                }
                terminate();
            }

            return true;
        }
    }

    private void initMusicVolumn() {
        try {
            int maxVolumn = audioManager.getMaxVolume(AudioManager.AudioVolumeType.STREAM_MUSIC);
            perProgressBar.setMaxValue(maxVolumn);
            int minVolumn = audioManager.getMinVolume(AudioManager.AudioVolumeType.STREAM_MUSIC);
            perProgressBar.setMinValue(minVolumn);
            int defaultVolumn = audioManager.getVolume(AudioManager.AudioVolumeType.STREAM_MUSIC);
            perProgressBar.setProgressValue(defaultVolumn);
        } catch (AudioRemoteException e) {
            e.printStackTrace();
        }
    }

    private void setMusicVolumn(Integer index) {
        perProgressBar.setVisibility(Component.VISIBLE);
        audioManager.changeVolumeBy(AudioManager.AudioVolumeType.STREAM_MUSIC, index);
        try {
            int defaultVolumn = audioManager.getVolume(AudioManager.AudioVolumeType.STREAM_MUSIC);
            perProgressBar.setProgressValue(defaultVolumn);
        } catch (AudioRemoteException e) {
            e.printStackTrace();
        }
        if (revocable != null) {
            revocable.revoke();
        }
        revocable = getUITaskDispatcher().delayDispatch(() -> {
            perProgressBar.setVisibility(Component.INVISIBLE);
        }, 3000);

    }


    //Play or stop music
    private void controlMusicStatus() {
        currentStatus = getString("currentStatus");
        if (currentStatus != null && currentStatus.equals("pause")) {
            stopPlayMusic();
        } else {
            startPlayMusic();
        }
    }

    public void putString(String name, String string) {
        Preferences modifier = databaseHelper.getPreferences(APP_PREFERENCE_NAME);
        modifier.putString(name, string);
        modifier.flush();
    }

    public String getString(String name) {
        return databaseHelper.getPreferences(APP_PREFERENCE_NAME).getString(name, "");
    }

    private void startPlayMusic() {
        loadingImgContent.setVisibility(Component.VISIBLE);
        imgPlayer.setPixelMap(ResourceTable.Media_pause);
        putString("currentStatus", "pause");
        imgPlayer.setClickable(false);
        imgVolumeLoud.setClickable(false);
        imgVolumeQuiet.setClickable(false);

        Intent intent = new Intent();
        intent.setElement(new ElementName("", "com.wishfm.mobileapp", "com.wishfm.mobileapp.famain.PlayerServiceAbility"));
        startAbility(intent);

//        Receive Notifications
        String event = "com.my.test";
        MatchingSkills matchingSkills = new MatchingSkills();
        matchingSkills.addEvent(event); // 自定义事件
        CommonEventSubscribeInfo subscribeInfo = new CommonEventSubscribeInfo(matchingSkills);
        MyCommonEventSubscriber subscriber = new MyCommonEventSubscriber(subscribeInfo);
        try {
            CommonEventManager.subscribeCommonEvent(subscriber);
        } catch (RemoteException e) {
            LogUtil.error(TAG, "Exception occurred during subscribeCommonEvent invocation.");
        }
    }

    private void stopPlayMusic() {
        putString("currentStatus", "arrow");
        imgPlayer.setPixelMap(ResourceTable.Media_play);
        Intent intent = new Intent();
        intent.setElement(new ElementName("", "com.wishfm.mobileapp", "com.wishfm.mobileapp.famain.PlayerServiceAbility"));
        stopAbility(intent);
    }

    // animation
    private void animatorPropertyHandle() {
        AnimatorProperty mAnimatorProperty = loadingBtn.createAnimatorProperty();
        mAnimatorProperty.rotate(360).setDuration(2000).setLoopedCount(1000);
        loadingBtn.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                if (mAnimatorProperty != null) {
                    mAnimatorProperty.start();
                }
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {
            }
        });
    }

    class MyCommonEventSubscriber extends CommonEventSubscriber {
        MyCommonEventSubscriber(CommonEventSubscribeInfo info) {
            super(info);
        }

        @Override
        public void onReceiveEvent(CommonEventData commonEventData) {
            loadingImgContent.setVisibility(Component.INVISIBLE);
            imgPlayer.setClickable(true);
            imgVolumeLoud.setClickable(true);
            imgVolumeQuiet.setClickable(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!moveRight) {
            Intent intent = new Intent();
            intent.setElement(new ElementName("", "com.wishfm.mobileapp", "com.wishfm.mobileapp.famain.PlayerServiceAbility"));
            stopAbility(intent);
        }
    }
}
