package vdee.evalverde.vdee.features.mainScreen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.mediaPlayer.RadioPlayerListener;
import vdee.evalverde.vdee.mediaPlayer.SimplePlayer;

/**
 * MainLayout should only know about the Main Layout screen
 * Controls the view only. Logic for this layout can be found in {@link MainController}
 */
public class MainLayout
        implements View.OnClickListener, RadioPlayerListener {

    private Analytics mAnalytics;
    private Button mPlayStopButton;
    private ImageView shareButton;
    private MainActivity mMainActivity;
    private MainLayoutListener mMainLayoutListener;
    private SimplePlayer mSimplePlayer;
    private SimpleExoPlayerView simpleExoPlayerView;


    MainLayout(
            @NonNull MainActivity mainActivity,
            @NonNull MainLayoutListener listener,
            @NonNull Analytics analytics) {
        mMainActivity = mainActivity;
        mMainLayoutListener = listener;
        mAnalytics = analytics;

        shareButton = mMainActivity.findViewById(R.id.share_button);
        mPlayStopButton = mMainActivity.findViewById(R.id.play_stop_button);
        simpleExoPlayerView = mMainActivity.findViewById(R.id.video_view);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareButtonClicked();
            }
        });

        analytics.homePageView();

        mPlayStopButton.setOnClickListener(this);

        mSimplePlayer = SimplePlayer.initializeSimplePlayer(mMainActivity, this);

        if (mSimplePlayer.isInitialized()) {
            mPlayStopButton.setBackground(mMainActivity.getResources().getDrawable(R.drawable.stop_button));
        } else {
            mPlayStopButton.setBackground(mMainActivity.getResources().getDrawable(R.drawable.play_button));

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_stop_button:
                onPlayStopButtonClicked();
                break;
            case R.id.share_button:
                onShareButtonClicked();
        }
    }

    private void onPlayStopButtonClicked() {
        mMainLayoutListener.onPlayStopButtonClicked();
    }

    @Override
    public void onRadioPlayerReady() {
        if (mMainActivity != null) {
            mMainActivity.hideDialog();
            mPlayStopButton.setBackground(mMainActivity.getResources().getDrawable(R.drawable.stop_button));
            mAnalytics.onRadioNetworkSuccess();
        }

    }

    @Override
    public void onRadioPlayerError() {
        if (mMainActivity != null) {
            mMainActivity.hideDialog();
            mMainActivity.showNetworkError(mMainActivity.getString(R.string.network_error));
            mPlayStopButton.setBackground(mMainActivity.getResources().getDrawable(R.drawable.play_button));
            mAnalytics.onRadioNetworkError();
        }

    }

    @Override
    public void onRadioPlayerBuffering() {
        if (mMainActivity != null && !mMainActivity.isFinishing()) {
            mMainActivity.showDialog();
        }
    }

    @Override
    public void onReleaseRadio() {
        if (mMainActivity != null) {
            mMainActivity.hideDialog();
            mPlayStopButton.setBackground(mMainActivity.getResources().getDrawable(R.drawable.play_button));
        }
    }

    private void onShareButtonClicked() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=vdee.evalverde.vdee");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Escucha Predicaciones!");
        intent.setType("text/plain");
        mMainActivity.startActivity(intent);
    }

    /**
     * Interface to communicate with {@link MainController}
     */
    interface MainLayoutListener {
        void onPlayStopButtonClicked();
    }
}
