package vdee.vdee.mainScreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import vdee.vdee.R;
import vdee.vdee.analytics.Analytics;
import vdee.vdee.mediaPlayer.SimplePlayer;
import vdee.vdee.util.PerController;

/**
 * Main Controller holds the logic for most activity happening on Main Screen.
 */
class MainController
        implements MainLayout.MainLayoutListener {

    private MainActivity mMainActivity;
    private String radio_url;
    private Boolean prepared, radioInitialized;
    private Analytics mAnalytics;
    private SimplePlayer mSimplePlayer;
    private IntentReceiver mIntentReceiver;

    @Inject MainLayout mMainLayout;

    MainController(@NonNull MainActivity mainActivity) {
        mMainActivity = mainActivity;
        prepared = false;
        radioInitialized = false;
        mAnalytics = Analytics.getAnalytics();

        DaggerMainController_MainControllerComponent.builder()
                .mainControllerModule(new MainControllerModule(mMainActivity, this, mAnalytics))
                .build()
                .inject(this);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        mIntentReceiver = new IntentReceiver();
        mMainActivity.registerReceiver(mIntentReceiver, intentFilter);

        mSimplePlayer = SimplePlayer.initializeSimplePlayer(mMainActivity, mMainLayout);
        initLayout();
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
        } else {
            mMainLayout.play();
            mSimplePlayer.initPlayer();
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
            modules = MainControllerModule.class)
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
