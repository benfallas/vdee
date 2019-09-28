package vdee.evalverde.vdee.features.mainScreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.mediaPlayer.Constants;
import vdee.evalverde.vdee.mediaPlayer.ForegroundService;
import vdee.evalverde.vdee.mediaPlayer.SimplePlayer;
import vdee.evalverde.vdee.permissions.PermissionsManager;
import vdee.evalverde.vdee.phoneCallReceiver.CallReceiver;
import vdee.evalverde.vdee.util.PerController;


/**
 * Main Controller holds the logic for most activity happening on Main Screen.
 */
class MainController
        implements MainLayout.MainLayoutListener,
        CallReceiver.CallReceiverListener {


    private MainActivity mMainActivity;
    private Analytics mAnalytics;
    private SimplePlayer mSimplePlayer;
    private IntentReceiver mIntentReceiver;
    private PhoneStateListener mPhoneStateListener;
    private TelephonyManager mTelephonyManager;
    @Inject MainLayout mMainLayout;


    MainController(@NonNull MainActivity mainActivity) {
        mMainActivity = mainActivity;
        mAnalytics = Analytics.getAnalytics();

        DaggerMainController_MainControllerComponent.builder()
                .mainControllerModule(new MainControllerModule(mMainActivity, this, mAnalytics))
                .experimentComponent(((VDEEApp) mainActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        mIntentReceiver = new IntentReceiver();
        mSimplePlayer = SimplePlayer.getSimplePlayer();

        mMainActivity.registerReceiver(mIntentReceiver, intentFilter);

        if (PermissionsManager.PHONE_STATE_PERMISSION_GRANTED) {
            mMainActivity.getApplicationContext();
            mTelephonyManager = (TelephonyManager) mMainActivity.getSystemService(Context.TELEPHONY_SERVICE);
            mPhoneStateListener = new CallReceiver(this);
            mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        } }

    @Override
    public void onIncomingCall() {
        if (mSimplePlayer.isInitialized()) {
            mSimplePlayer.releasePlayer();
        }
    }

    /**
     * releases receiver.
     */
    void onPaused() {
        try {
            mMainActivity.unregisterReceiver(mIntentReceiver);
        } catch (IllegalArgumentException e) { }
    }

    @Override
    public void onPlayStopButtonClicked() {
        if (mSimplePlayer.isInitialized()) {
            mAnalytics.onStopButtonClicked();
            mSimplePlayer.releasePlayer();
            stopService();
        } else {
            mAnalytics.onPlayButtonClicked();
            startService();
            mSimplePlayer.initPlayer();
        }
    }

    private void startService() {
        Intent serviceIntent = new Intent(mMainActivity, ForegroundService.class);
        serviceIntent.setAction(Constants.ACTION.START_SERVICE);

        ContextCompat.startForegroundService(mMainActivity, serviceIntent);
    }

    private void stopService() {
        Intent serviceIntent = new Intent(mMainActivity, ForegroundService.class);
        serviceIntent.setAction(Constants.ACTION.STOP_SERVICE);

        ContextCompat.startForegroundService(mMainActivity, serviceIntent);
    }

    private class IntentReceiver extends BroadcastReceiver {

        private final String headsetReceiver = "HEADSET RECEIVER";
        private final String headsetPlugged = "Headset is plugged";
        private final String headsetUnplugged = "Headset is unplugged";
        private final String headsetErr = "Something went wrong";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        if (mSimplePlayer != null) {
                            mSimplePlayer.releasePlayer();
                        }
                        Log.d(headsetReceiver, headsetUnplugged);
                        break;
                    case 1:
                        Log.d(headsetReceiver, headsetPlugged);
                        break;
                    default:
                        Log.d(headsetReceiver, headsetErr);
                }
            }
        }
    }

    @PerController
    @Component(
           dependencies = ExperimentComponent.class, modules = MainControllerModule.class)
    interface MainControllerComponent {
        void inject(MainController mainController);
    }

    @Module
    static class MainControllerModule {
        private final MainActivity mMainActivity;
        private final MainLayout.MainLayoutListener mMainLayoutListener;
        private final Analytics mAnalytics;

        MainControllerModule(
                @NonNull MainActivity mainActivity,
                @NonNull MainLayout.MainLayoutListener listener,
                @NonNull Analytics analytics) {
            mMainActivity = mainActivity;
            mMainLayoutListener = listener;
            mAnalytics = analytics;
        }

        @Provides
        @PerController
        MainLayout providesMainLayout() {
            return new MainLayout(mMainActivity, mMainLayoutListener, mAnalytics);
        }
    }
}
