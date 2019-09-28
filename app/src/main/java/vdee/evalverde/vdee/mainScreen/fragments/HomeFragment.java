package vdee.evalverde.vdee.mainScreen.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.concurrent.TimeUnit;

import dagger.Component;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.mediaPlayer.Constants;
import vdee.evalverde.vdee.mediaPlayer.ForegroundService;
import vdee.evalverde.vdee.mediaPlayer.RadioPlayerListener;
import vdee.evalverde.vdee.mediaPlayer.SimplePlayer;
import vdee.evalverde.vdee.util.FragmentManagerUtils;
import vdee.evalverde.vdee.util.ParentFragment;
import vdee.evalverde.vdee.util.PerFragment;

import static vdee.evalverde.vdee.mediaPlayer.ForegroundService.START_SERVICE_FLAG;
import static vdee.evalverde.vdee.mediaPlayer.ForegroundService.START_STOP_KEY;
import static vdee.evalverde.vdee.mediaPlayer.ForegroundService.STOP_SERVICE_FLAG;

public class HomeFragment extends ParentFragment implements View.OnClickListener, RadioPlayerListener {

    private Analytics mAnalytics;
    private SimplePlayer mSimplePlayer;
    private Boolean isPaused;
    private long endNow;
    private long minute;
    private long startNow;

    private Button mPlayStopButton;
    private TextView mToolbarTitle;
    private SimpleExoPlayerView simpleExoPlayerView;
    FragmentManagerUtils fragmentManagerUtils;

    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vdee__home_fragment_view, viewGroup, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAttach();
    }

    private void onAttach() {
        DaggerHomeFragment_HomeComponent.builder()
                .experimentComponent((((VDEEApp) getActivity().getApplicationContext())).getExpComponent())
                .build()
                .inject(this);

        mAnalytics = Analytics.getAnalytics();
        mPlayStopButton = getActivity().findViewById(R.id.play_stop_button);
        mToolbarTitle = getActivity().findViewById(R.id.id__toolbar_title);
        simpleExoPlayerView = getActivity().findViewById(R.id.video_view);

        mAnalytics.homePageView();

        mPlayStopButton.setOnClickListener(this);

        mSimplePlayer = SimplePlayer.initializeSimplePlayer(getActivity(), this);
        fragmentManagerUtils = FragmentManagerUtils.getFragmentManagerUtils();

        initLayout();
    }

    private void initLayout() {
        if (mSimplePlayer.isInitialized()) {
            isPlaying();
        }
    }


    void isPlaying() {
        mPlayStopButton.setBackground(getActivity().getResources().getDrawable(R.drawable.stop_button));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_stop_button:
                onPlayStopButtonClicked();
                break;
            case R.id.share_button:
                onShareButtonClicked();
        }
    }

    private void onShareButtonClicked() { }

    private void play() {
        mAnalytics.onPlayButtonClicked();
        mPlayStopButton.setBackground(getActivity().getResources().getDrawable(R.drawable.play_button));
    }

    private void onPlayStopButtonClicked() {
        if (mSimplePlayer == null) {
            mSimplePlayer = SimplePlayer.initializeSimplePlayer(getActivity(), this);
        }

        if (mSimplePlayer.isInitialized()) {
            mSimplePlayer.releasePlayer();
            isPaused = true;
            endNow = android.os.SystemClock.uptimeMillis();
            minute = TimeUnit.MILLISECONDS.toMinutes(endNow - startNow);
            logTime(minute);
            stopService();
            hideDialog();
        } else {
            showDialog();
            startService();
            play();
            mSimplePlayer.initPlayer();
            isPaused = false;
            startNow = android.os.SystemClock.uptimeMillis();
        }
    }

    private void stop() {
        mAnalytics.onStopButtonClicked();
        mPlayStopButton.setBackground(getActivity().getResources().getDrawable(R.drawable.play_button));
    }

    private void logTime(long minute) {
        mAnalytics.onStopButtonTimer(minute);
    }

    @Override
    public void onRadioPlayerReady() {
        hideDialog();
        if (fragmentManagerUtils.isCurrentFragmentShown(FragmentManagerUtils.HOME_FRAGMENT_TAG)) {
            mPlayStopButton.setBackground(getActivity().getResources().getDrawable(R.drawable.stop_button));
        }
        mAnalytics.onRadioNetworkSuccess();
    }

    @Override
    public void onRadioPlayerError() {
        showNetworkError();
        mAnalytics.onRadioNetworkError();
    }

    @Override
    public void onRadioPlayerBuffering() {
        showDialog();
    }

    @Override
    public void onReleaseRadio() {
        hideDialog();
        stop();
    }

    private void startService() {
        Intent serviceIntent = new Intent(getActivity(), ForegroundService.class);
        serviceIntent.setAction(Constants.ACTION.START_SERVICE);

        ContextCompat.startForegroundService(getActivity(), serviceIntent);
    }

    private void stopService() {
        Intent serviceIntent = new Intent(getActivity(), ForegroundService.class);
        serviceIntent.setAction(Constants.ACTION.STOP_SERVICE);

        ContextCompat.startForegroundService(getActivity(), serviceIntent);
    }

    @PerFragment
    @Component(dependencies = ExperimentComponent.class)
    interface HomeComponent {
        void inject(HomeFragment homeFragment);
    }
}
