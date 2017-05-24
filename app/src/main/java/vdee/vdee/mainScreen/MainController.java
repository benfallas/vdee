package vdee.vdee.mainScreen;

import android.support.annotation.NonNull;
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
            mAnalytics.onStopButtonClicked();
            mMainLayout.loading(false);
            mSimplePlayer.releasePlayer();
            button.setBackground(mMainActivity.getResources().getDrawable(R.drawable.play_button));
        } else {
            mAnalytics.onPlayButtonClicked();
            mSimplePlayer.initPlayer();
            mMainLayout.loading(true);
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
