package vdee.vdee.mainScreen;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vdee.vdee.R;
import vdee.vdee.analytics.Analytics;
import vdee.vdee.bibleScreen.BibleActivity;
import vdee.vdee.contact.ContactActivity;
import vdee.vdee.mediaPlayer.RadioPlayerListener;

/**
 * MainLayout should only know about the Main Layout screen
 * Controls the view only. Logic for this layout can be found in {@link MainController}
 */
public class MainLayout
        implements RadioPlayerListener,
            BottomNavigationView.OnNavigationItemSelectedListener {

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
    @BindView(R.id.navigation) BottomNavigationView mNavigationView;
    @BindView(R.id.radio_station_title) TextView mRadioStationTitle;
    @BindView(R.id.next_button) Button mNextButton;
    @BindView(R.id.previous_button) Button mPreviousButton;


    MainLayout(
            @NonNull MainActivity mainActivity,
            @NonNull MainLayoutListener listener,
            @NonNull Analytics analytics) {
        mMainActivity = mainActivity;
        mMainLayoutListener = listener;
        mAnalytics = analytics;

        mMainActivity.setContentView(R.layout.activity_main);
        ButterKnife.bind(this, mainActivity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setTitle("");
            mMainActivity.setActionBar(mToolbar);
        }
        mToolbarTitle.setText(R.string.app_title);
        mAnalytics.homePageView();
        mNetworkError.setVisibility(View.GONE);

        mNavigationView.setOnNavigationItemSelectedListener(this);
    }

    void isPlaying(boolean isMultipleRadioSupportEnabled) {
        mPlayButton.setBackground(mMainActivity.getResources().getDrawable(R.drawable.stop_button));
        if (isMultipleRadioSupportEnabled) {
            mRadioStationTitle.setVisibility(View.VISIBLE);
            mPreviousButton.setVisibility(View.VISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
        }
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
            mNextButton.setVisibility(View.GONE);
            mPreviousButton.setVisibility(View.GONE);
            mRadioStationTitle.setVisibility(View.GONE);
            mLoadingDialog.setVisibility(View.VISIBLE);
        } else {
            showStoppedState();
        }
    }

    public void showStoppedState() {
        boolean isMultilpleRadioSupportEnabled = MainController.getIsMultipleRadioSupportEnabled();
        if (isMultilpleRadioSupportEnabled) {
            mPlayButton.setVisibility(View.VISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            mPreviousButton.setVisibility(View.VISIBLE);
            mRadioStationTitle.setVisibility(View.VISIBLE);
            mLoadingDialog.setVisibility(View.GONE);
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
        mMainLayoutListener.onRadioPlayerReady();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.bible:
                intent = new Intent(mMainActivity, BibleActivity.class);
                mMainActivity.startActivity(intent);
                break;
            case R.id.contactItem:
                Intent contactIntent = new Intent(mMainActivity, ContactActivity.class);
                mMainActivity.startActivity(contactIntent);
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * Interface to communicate with {@link MainController}
     */
    interface MainLayoutListener {
        void onPlayButtonClicked(Button button);
        void onShareButtonClicked();
        void onPreviousButtonClicked();
        void onNextButtonClicked();
        void onRadioPlayerReady();
    }
}
