package vdee.vdee.analytics;

import com.crashlytics.android.answers.Answers;

/**
 * Analytic functions.
 */
public class Analytics {

    private static CustomEvents mCustomEvents;
    private static Analytics mAnalytics;

    public Analytics() { mCustomEvents = new CustomEvents(); }

    public static Analytics getAnalytics() {
        if (mAnalytics == null) {
            mAnalytics = new Analytics();
        }
        return mAnalytics;
    }

    public void homePageView() {
        Answers.getInstance().logCustom(mCustomEvents.homePageView());
    }

    public void onPlayButtonClicked() {
        Answers.getInstance().logCustom(mCustomEvents.onPlayButtonClicked());
    }

    public void onStopButtonClicked() {
        Answers.getInstance().logCustom(mCustomEvents.stopButtonClicked());
    }

    public void onRadioNetworkError() {
        Answers.getInstance().logCustom(mCustomEvents.radioNetworkError());
    }

    public void onRadioNetworkSuccess() {
        Answers.getInstance().logCustom(mCustomEvents.radioNetworkSuccess());
    }
}
