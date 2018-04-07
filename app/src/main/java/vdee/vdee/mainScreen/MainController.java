package vdee.vdee.mainScreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import vdee.vdee.R;
import vdee.vdee.VDEEApp;
import vdee.vdee.analytics.Analytics;
import vdee.vdee.component.ExperimentComponent;
import vdee.vdee.mediaPlayer.RadioStationUrls;
import vdee.vdee.mediaPlayer.SimplePlayer;
import vdee.vdee.permissions.PermissionsManager;
import vdee.vdee.phoneCallReceiver.CallReceiver;
import vdee.vdee.util.PerController;

/**
 * Main Controller holds the logic for most activity happening on Main Screen.
 */
class MainController
        implements MainLayout.MainLayoutListener,
        CallReceiver.CallReceiverListener {

    private int cacheExpiration;
    private static String experiment;
    private Boolean FBExpName;
    private Boolean isPaused;

    private MainActivity mMainActivity;
    private Analytics mAnalytics;
    private SimplePlayer mSimplePlayer;
    private IntentReceiver mIntentReceiver;
    private PhoneStateListener mPhoneStateListener;
    private TelephonyManager mTelephonyManager;
    private RadioStationUrls mRadioStationUrls;

    @Inject FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Inject MainLayout mMainLayout;
    private PermissionsManager mPermissionsManager;

    private long startNow;
    private long endNow;
    private long minutes;

    MainController(@NonNull MainActivity mainActivity) {
        mMainActivity = mainActivity;
        mAnalytics = Analytics.getAnalytics();
        mPermissionsManager = PermissionsManager.getPermissionsManager();

        DaggerMainController_MainControllerComponent.builder()
                .mainControllerModule(new MainControllerModule(mMainActivity, this, mAnalytics))
                .experimentComponent(((VDEEApp) mainActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        mIntentReceiver = new IntentReceiver();

        mMainActivity.registerReceiver(mIntentReceiver, intentFilter);

        if (mPermissionsManager.PHONE_STATE_PERMISSION_GRANTED) {
            mTelephonyManager = (TelephonyManager) mMainActivity.getSystemService(mMainActivity.getApplicationContext().TELEPHONY_SERVICE);
            mPhoneStateListener = new CallReceiver(this);
            mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

        mSimplePlayer = SimplePlayer.initializeSimplePlayer(mMainActivity, mMainLayout);
        mRadioStationUrls = RadioStationUrls.initRadioStationUrl();
        initLayout();
        onAttach();
        mMainLayout.updateRadioStationTitle(mRadioStationUrls.getCurrentTrack().getRadioTitle());
    }

    private void onAttach() {
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        experiment = mMainActivity.getString(R.string.music_bug);
                        FBExpName = mFirebaseRemoteConfig.getBoolean(experiment);
                    }
                });

        FirebaseAnalytics.getInstance(mMainActivity).setUserProperty(String.valueOf(FBExpName), experiment);
    }

    private void initLayout() {
        if (mSimplePlayer.isInitialized()) {
            mMainLayout.isPlaying();
        }
    }

    @Override
    public void onPlayButtonClicked(Button button) {

        if (mSimplePlayer.isInitialized()) {
            mSimplePlayer.releasePlayer();
            isPaused = true;
            endNow = android.os.SystemClock.uptimeMillis();
            minutes = TimeUnit.MILLISECONDS.toMinutes(endNow - startNow);
            mMainLayout.logTime(minutes);
        } else {
            mMainLayout.play();
            mSimplePlayer.initPlayer();
            isPaused = false;
            startNow = android.os.SystemClock.uptimeMillis();
        }
    }

    @Override
    public void onShareButtonClicked() {
        String vdeeShareLink = mMainActivity.getString(R.string.vdee_link);
        String description = mMainActivity.getString(R.string.vdee_share_content_description);
        String title = mMainActivity.getString(R.string.vdee_share_title);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, description + vdeeShareLink);
        sendIntent.setType("text/plain");
        mMainActivity.startActivity(Intent.createChooser(sendIntent, title));
    }

    @Override
    public void onPreviousButtonClicked() {
        mRadioStationUrls.previousTrack();
        updatePlayer();
    }

    @Override
    public void onNextButtonClicked() {
        mRadioStationUrls.nextTrack();
        updatePlayer();
    }

    @Override
    public void onRadioPlayerReady() {
        mMainLayout.updateRadioStationTitle(mRadioStationUrls.getCurrentTrack().getRadioTitle());
    }

    @Override
    public void onIncomingCall() {
        mSimplePlayer.releasePlayer();
    }

    @Override
    public void onCallEnded() {
        if(FBExpName && isPaused) {
            mSimplePlayer.releasePlayer();
        } else
            mSimplePlayer.initPlayer();
    }

    private void updatePlayer() {
        if(mSimplePlayer.isInitialized()){
            mSimplePlayer.initPlayer();
        }else{
            mMainLayout.updateRadioStationTitle(mRadioStationUrls.getCurrentTrack().getRadioTitle());
        }
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
                        mSimplePlayer.releasePlayer();
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
