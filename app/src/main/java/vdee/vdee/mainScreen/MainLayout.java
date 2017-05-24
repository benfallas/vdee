package vdee.vdee.mainScreen;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.id__toolbar_title) TextView mToolbarTitle;
    @BindView(R.id.video_view) SimpleExoPlayerView simpleExoPlayerView;

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
    }

    void isPlaying() {
        mPlayButton.setBackground(mMainActivity.getResources().getDrawable(R.drawable.stop_button));
    }

    /**
     * Listens to when play button is clicked.
     */
    @OnClick(R.id.play_stop_button)
    void onPlayButtonClicked() {
        mMainLayoutListener.onPlayButtonClicked(mPlayButton);
        mNetworkError.setVisibility(View.GONE);
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

    /**
     * Interface to communicate with {@link MainController}
     */
    interface MainLayoutListener {
        void onPlayButtonClicked(Button button);
    }
}
