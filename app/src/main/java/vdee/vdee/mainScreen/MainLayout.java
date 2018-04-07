package vdee.vdee.mainScreen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vdee.vdee.R;
import vdee.vdee.analytics.Analytics;
import vdee.vdee.mediaPlayer.RadioPlayerListener;

/**
 * MainLayout should only know about the Main Layout screen
 * Controls the view only. Logic for this layout can be found in {@link MainController}
 */
public class MainLayout
        implements RadioPlayerListener {

    private MainActivity mMainActivity;
    private MainLayoutListener mMainLayoutListener;
    private Analytics mAnalytics;

    @BindView(R.id.play_stop_button) Button mPlayButton;
    @BindView(R.id.progressBar) ProgressBar mLoadingDialog;
    @BindView(R.id.id_error_message) TextView mNetworkError;
    @BindView(R.id.main_toolbar) Toolbar mToolbar;
    @BindView(R.id.id__toolbar_title) TextView mToolbarTitle;
    @BindView(R.id.video_view) SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.share_button) ImageView ShareButton;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView mNavigationView;
    @BindView(R.id.radio_station_title) TextView mRadioStationTitle;

    MainLayout(
            @NonNull MainActivity mainActivity,
            @NonNull MainLayoutListener listener,
            @NonNull Analytics analytics) {
        mMainActivity = mainActivity;
        mMainLayoutListener = listener;
        mAnalytics = analytics;

        mMainActivity.setContentView(R.layout.activity_main);
        ButterKnife.bind(this, mainActivity);

        mToolbar.setTitle("");
        mToolbarTitle.setText(R.string.app_title);
        mMainActivity.setSupportActionBar(mToolbar);
        mAnalytics.homePageView();
        mNetworkError.setVisibility(View.GONE);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                mMainActivity,
                mDrawerLayout,
                mToolbar,
                R.string.open,
                R.string.close
        );

        mDrawerLayout.addDrawerListener(toggle);
        mMainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMainActivity.getSupportActionBar().setHomeButtonEnabled(true);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(new NavigationMenu(mMainActivity));

    }

    void isPlaying() {
        mPlayButton.setBackground(mMainActivity.getResources().getDrawable(R.drawable.stop_button));
    }

    public void play() {
        mAnalytics.onPlayButtonClicked();
        loading(true);
        mPlayButton.setBackground(mMainActivity.getResources().getDrawable(R.drawable.stop_button));
    }

    public void stop() {
        mAnalytics.onStopButtonClicked();
        loading(false);
        mPlayButton.setBackground(mMainActivity.getResources().getDrawable(R.drawable.play_button));
    }

    public void logTime(long minutes){
        mAnalytics.onStopButtonTimer(minutes);
    }

    /**
     * Listens to when play button is clicked.
     */
    @OnClick(R.id.play_stop_button)
    void onPlayButtonClicked() {
        mMainLayoutListener.onPlayButtonClicked(mPlayButton);
        mNetworkError.setVisibility(View.GONE);
    }

    @OnClick(R.id.previous_button)
    void onPreviousButtonClicked() {
        mMainLayoutListener.onPreviousButtonClicked();
    }

    @OnClick(R.id.next_button)
    void onNextButtonClicked() {
        mMainLayoutListener.onNextButtonClicked();
    }

    @OnClick(R.id.share_button)
    void onShareButtonClicked() {
        mMainLayoutListener.onShareButtonClicked();
    }


    /**
     * Update the radio station title 
     */
    public void updateRadioStationTitle(String title) {
        mRadioStationTitle.setText(title);
    }

    /**
     * Controls loading dialog accordingly
     *
     * @param load {@true} if show loading screen.
     *  {@false} other wise.
     */
    public void loading(boolean load) {
        if (load) {
            mPlayButton.setVisibility(View.GONE);
            mLoadingDialog.setVisibility(View.VISIBLE);
        } else {
            mPlayButton.setVisibility(View.VISIBLE);
            mLoadingDialog.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRadioPlayerReady() {
        loading(false);
        mPlayButton.setBackground(mMainActivity.getResources().getDrawable(R.drawable.stop_button));
        mAnalytics.onRadioNetworkSuccess();
    }

    @Override
    public void onRadioPlayerError() {
        loading(false);
        mNetworkError.setVisibility(View.VISIBLE);
        mAnalytics.onRadioNetworkError();
    }

    @Override
    public void onRadioPlayerBuffering() {
        loading(true);
    }

    @Override
    public void onReleaseRadio() {
        stop();
    }

    /**
     * Interface to communicate with {@link MainController}
     */
    interface MainLayoutListener {
        void onPlayButtonClicked(Button button);
        void onShareButtonClicked();
        void onPreviousButtonClicked();
        void onNextButtonClicked();
    }
}
