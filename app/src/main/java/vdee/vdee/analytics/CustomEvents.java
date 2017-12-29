package vdee.vdee.analytics;

import android.util.Log;

import com.crashlytics.android.answers.CustomEvent;

public class CustomEvents {

    private String mHomePageView = "Viewed Home Page";
    private String mPlayButtonClicked = "Play Button Clicked";
    private String mPlayRadio = "Play Radio";
    private String mPlaySuccessErr = "Play Radio Successful vs Error";
    private String mNetworkErr = "Network Error";
    private String mNetworkSuccess = "Network Success";
    private String mStopButtonClicked = "Stop Button Clicked";

    CustomEvent homePageView() {
        return new CustomEvent(mHomePageView)
                .putCustomAttribute(mHomePageView, mHomePageView);
    }

    CustomEvent onPlayButtonClicked() {
        return new CustomEvent(mPlayButtonClicked)
                .putCustomAttribute(mPlayButtonClicked, mPlayButtonClicked);
    }

    CustomEvent stopButtonClicked() {
        return new CustomEvent(mPlayButtonClicked)
                .putCustomAttribute(mPlayButtonClicked, mStopButtonClicked);
    }

    CustomEvent radioNetworkError() {
        return new CustomEvent(mPlayRadio)
                .putCustomAttribute(mPlaySuccessErr, mNetworkErr);
    }

    CustomEvent radioNetworkSuccess() {
        return new CustomEvent(mPlayRadio)
                .putCustomAttribute(mPlaySuccessErr, mNetworkSuccess);
    }
}
