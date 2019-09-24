package vdee.evalverde.vdee.analytics;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.LevelEndEvent;

/**
 * Analytic functions.
 */
public class Analytics {

    private static CustomEvents mCustomEvents;
    private static Analytics mAnalytics;
    private String time = "The Total Time Users Listen To Radio";

    public Analytics() { mCustomEvents = new CustomEvents(); }

    public static Analytics getAnalytics() {
        if (mAnalytics == null) {
            mAnalytics = new Analytics();
        }
        return mAnalytics;
    }

    public void homePageView() {
        Answers.getInstance().logContentView(
                new ContentViewEvent()
                        .putContentName("Home Page View")
                        .putContentId("homePageView")
        );
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

    public void onStopButtonTimer(double minutes){
        Answers.getInstance()
                .logLevelEnd(new LevelEndEvent()
                .putLevelName(time)
                .putScore(minutes));
    }

    public void onPhoneButtonClicked() {
        Answers.getInstance()
                .logCustom(mCustomEvents.phoneButtonClicked());
    }

    public void contactPageView() {
        Answers.getInstance().logContentView(
                new ContentViewEvent()
                .putContentName("Contact Us Page")
                .putContentId("contactUs"));
    }

    public void biblePageView() {
        Answers.getInstance().logContentView(
                new ContentViewEvent()
                .putContentName("Bible Page")
                .putContentId("bible"));
    }

    public void chaptersView() {
        Answers.getInstance().logContentView(
                new ContentViewEvent()
                .putContentName("Chapters Page")
                .putContentId("chapters"));
    }

    public void versesView() {
        Answers.getInstance().logContentView(
                new ContentViewEvent()
                .putContentName("Verses Page")
                .putContentId("verses"));
    }
}
