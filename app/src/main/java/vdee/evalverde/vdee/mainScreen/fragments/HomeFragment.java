package vdee.evalverde.vdee.mainScreen.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.concurrent.TimeUnit;

import dagger.Component;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.experiments.VdeeExperiments;
import vdee.evalverde.vdee.mediaPlayer.RadioPlayerListener;
import vdee.evalverde.vdee.mediaPlayer.RadioStationUrls;
import vdee.evalverde.vdee.mediaPlayer.SimplePlayer;
import vdee.evalverde.vdee.util.FragmentManagerUtils;
import vdee.evalverde.vdee.util.ParentFragment;
import vdee.evalverde.vdee.util.PerFragment;

import static android.view.View.VISIBLE;

public class HomeFragment extends ParentFragment implements View.OnClickListener, RadioPlayerListener {

    private Analytics mAnalytics;
    private RadioStationUrls mRadioStationUrls;
    private SimplePlayer mSimplePlayer;
    private Boolean isPaused;
    private long endNow;
    private long minute;
    private long startNow;

    private Button mPlayStopButton;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private SimpleExoPlayerView simpleExoPlayerView;
    private ImageView shareButton;
    private TextView radioStationTitle;
    private Button nextButton;
    private Button previousButton;
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
        mToolbar = getActivity().findViewById(R.id.main_toolbar);
        mToolbarTitle = getActivity().findViewById(R.id.id__toolbar_title);
        simpleExoPlayerView = getActivity().findViewById(R.id.video_view);
        shareButton = getActivity().findViewById(R.id.share_button);
        radioStationTitle = getActivity().findViewById(R.id.radio_station_title);
        nextButton = getActivity().findViewById(R.id.next_button);
        previousButton = getActivity().findViewById(R.id.previous_button);

        mPlayStopButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        mSimplePlayer = SimplePlayer.initializeSimplePlayer(getActivity(), this);
        mRadioStationUrls = RadioStationUrls.initRadioStationUrl();
        fragmentManagerUtils = FragmentManagerUtils.getFragmentManagerUtils();
        updateRadioStationTitle(mRadioStationUrls.getCurrentTrack().getRadioTitle());

        initLayout();
    }

    private void initLayout() {
        if (mSimplePlayer.isInitialized()) {
            isPlaying(VdeeExperiments.getInstance().isExperimentEnabled(VdeeExperiments.VDEE_MULTIPLE_SUPPORT_RADIO));
        }

        if (VdeeExperiments.getInstance().isExperimentEnabled(VdeeExperiments.VDEE_MULTIPLE_SUPPORT_RADIO)) {
            displayMultipleRadioStations();
        }
    }


    void isPlaying(boolean isMultipleRadioSupportEnabled) {
        mPlayStopButton.setBackground(getActivity().getResources().getDrawable(R.drawable.stop_button));
        if (isMultipleRadioSupportEnabled) {
            displayMultipleRadioStations();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous_button:
                onPreviousButtonClicked();
                break;
            case R.id.next_button:
                onNextButtonClicked();
                break;
            case R.id.play_stop_button:
                onPlayStopButtonClicked();
                break;
        }
    }

    /**
     * Update the radio station title.
     *
     * @param title The radio station title.
     */
    public void updateRadioStationTitle(String title) {
        radioStationTitle.setText(title);
    }

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
            hideDialog();
        } else {
            showDialog();
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

    private void onPreviousButtonClicked() {
        mRadioStationUrls.previousTrack();
        updatePlayer();
    }

    private void onNextButtonClicked() {
        mRadioStationUrls.nextTrack();
        updatePlayer();
    }

    @Override
    public void onRadioPlayerReady() {
        hideDialog();
        if (fragmentManagerUtils.isCurrentFragmentShown(FragmentManagerUtils.HOME_FRAGMENT_TAG)) {
            updateRadioStationTitle(mRadioStationUrls.getCurrentTrack().getRadioTitle());
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

    private void updatePlayer() {
        if (mSimplePlayer.isInitialized()) {
            mSimplePlayer.initPlayer();
        } else {
            updateRadioStationTitle(mRadioStationUrls.getCurrentTrack().getRadioTitle());
        }
    }

    private void displayMultipleRadioStations() {
        radioStationTitle.setVisibility(VISIBLE);
        previousButton.setVisibility(VISIBLE);
        nextButton.setVisibility(VISIBLE);
    }

    @PerFragment
    @Component(dependencies = ExperimentComponent.class)
    interface HomeComponent {
        void inject(HomeFragment homeFragment);
    }
}
