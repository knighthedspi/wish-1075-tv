package com.wishfm.mobileapp.famain;

import com.wishfm.mobileapp.famain.utils.LogUtil;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.event.commonevent.CommonEventData;
import ohos.event.commonevent.CommonEventManager;
import ohos.event.notification.NotificationRequest;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.common.Source;
import ohos.media.player.Player;
import ohos.powermanager.PowerManager;
import ohos.rpc.IRemoteObject;
import ohos.rpc.RemoteException;

public class PlayerServiceAbility extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "Demo");

    private Player mPlayer;
    private static String TAG = "Wish1075";
    String uri = "https://play.wish1075.com/radio/wish.m3u8";
    Source source = new Source(uri);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        NotificationRequest request = new NotificationRequest(1005);
        NotificationRequest.NotificationNormalContent content = new NotificationRequest.NotificationNormalContent();
        NotificationRequest.NotificationContent notificationContent = new NotificationRequest.NotificationContent(content);
        request.setContent(notificationContent);
        keepBackgroundRunning(1006,request);
        PowerManager powerManager = new PowerManager();
        PowerManager.RunningLock runningLock = powerManager.createRunningLock("test",PowerManager.RunningLockType.BACKGROUND);
        runningLock.lock(5000000);
    }

    @Override
    public void onBackground() {
        super.onBackground();
        LogUtil.warn(TAG, "--------onBackground-----");
    }

    @Override
    public void onCommand(Intent intent, boolean restart, int startId) {
        if (mPlayer != null) {
            LogUtil.warn(TAG, "Stop and release old player");
            if (mPlayer.isNowPlaying()) {
                mPlayer.stop();
            }
            mPlayer.release();
        }
        mPlayer = new Player(this);
        mPlayer.setPlayerCallback(iPlayerCallback);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!mPlayer.setSource(source)) {
                    LogUtil.warn(TAG, "Set audio source failed");
                }
                if (!mPlayer.prepare()) {
                    LogUtil.warn(TAG, "Prepare audio file failed");
                }
                if (mPlayer.play()) {
                    LogUtil.warn(TAG, "Play success");
                } else {
                    LogUtil.warn(TAG, "Play failed");
                }
            }
        }).start();

    }
    @Override
    public IRemoteObject onConnect(Intent intent) {
        LogUtil.warn(TAG, "----onConnect-----");

        return null;
    }

    @Override
    public void onDisconnect(Intent intent) {
        LogUtil.warn(TAG, "----onDisconnect-----");

        if (mPlayer != null) {
            if (mPlayer.isNowPlaying()) {
                mPlayer.pause();
            }
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        LogUtil.warn(TAG, "----Stop-----");

        if (mPlayer != null) {
            if (mPlayer.isNowPlaying()) {
                mPlayer.pause();
            }
            mPlayer.release();
        }
    }
    Player.IPlayerCallback iPlayerCallback = new Player.IPlayerCallback() {
        @Override
        public void onPrepared() {
            LogUtil.warn(TAG, "==============Complete the preparation");
        }

        @Override
        public void onMessage(int i, int i1) {
            LogUtil.warn(TAG, "==============Receive Messages");
        }

        @Override
        public void onError(int i, int i1) {
            LogUtil.warn(TAG, "==============throw error" );

        }

        @Override
        public void onResolutionChanged(int i, int i1) {
            LogUtil.warn(TAG, "==============onResolutionChanged" );
        }

        @Override
        public void onPlayBackComplete() {
            LogUtil.warn(TAG, "==============onPlayBackComplete" );
        }

        @Override
        public void onRewindToComplete() {
            LogUtil.warn(TAG, "==============onRewindToComplete" );
        }

        @Override
        public void onBufferingChange(int i) {
            LogUtil.warn(TAG, "==============onBufferingChange" );
        }

        @Override
        public void onNewTimedMetaData(Player.MediaTimedMetaData mediaTimedMetaData) {

        }

        @Override
        public void onMediaTimeIncontinuity(Player.MediaTimeInfo mediaTimeInfo) {
            try {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder().withAction("com.my.test").build();
                intent.setOperation(operation);
                CommonEventData eventData = new CommonEventData(intent);
                CommonEventManager.publishCommonEvent(eventData);
            } catch (RemoteException e) {
            }
        }
    };


}