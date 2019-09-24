package vdee.evalverde.vdee.analytics;

import com.crashlytics.android.answers.CustomEvent;

public class CustomEvents {

    private String mHomePageView = "Viewed Home Page";
    private String mPlayButtonClicked = "Play Button Clicked";
    private String mPlayRadio = "Play Radio";
    private String mPlaySuccessErr = "Play Radio Successful vs Error";
    private String mNetworkErr = "Network Error";
    private String mNetworkSuccess = "Network Success";
    private String mStopButtonClicked = "Stop Button Clicked";
    private String mPhoneButtonClicked = "Phone Button Clicked";
    private String mEmailButtonClicked = "Email Button Clicked";
    private String mContactPageView = "Viewed Contact Us Page";

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

    CustomEvent phoneButtonClicked() {
        return new CustomEvent(mPhoneButtonClicked)
                .putCustomAttribute(mPhoneButtonClicked, mContactPageView);
    }
}
