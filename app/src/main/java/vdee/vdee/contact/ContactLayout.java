package vdee.vdee.contact;

import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vdee.vdee.R;
import vdee.vdee.analytics.Analytics;
import vdee.vdee.Animations.MyBounceInterpolator;
import vdee.vdee.mediaPlayer.RadioPlayerListener;
import vdee.vdee.mediaPlayer.SimplePlayer;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

/**
 * ContactLayout should only know about the Contact Layout screen
 * Controls the view only. Logic for this layout can be found in {@link ContactController}
 */
public class ContactLayout implements RadioPlayerListener {

    private ContactActivity mContactActivity;
    private ContactLayoutListener mContactLayoutListener;
    private Analytics mAnalytics;
    private ToolbarSupport mToolBar;
    private final Animation mAnimation;
    private MyBounceInterpolator mInterpolator;
    private SimplePlayer mSimplePlayer;

    @BindView(R.id.call_button) Button mCallButton;

    public ContactLayout(
            @NonNull ContactActivity contactActivity,
            @NonNull ContactLayoutListener listener,
            @NonNull Analytics analytics) {
        mContactActivity = contactActivity;
        mContactLayoutListener = listener;
        mAnalytics = analytics;

        mContactActivity.setContentView(R.layout.activity_contact);
        ButterKnife.bind(this, mContactActivity);
        mToolBar = new BackArrowToolbar(mContactActivity, R.string.contact);
        mAnalytics.contactPageView();
        mAnimation = AnimationUtils.loadAnimation(mContactActivity, R.anim.bounce);
        mInterpolator = new MyBounceInterpolator(0.2, 20);
        mAnimation.setInterpolator(mInterpolator);
        mSimplePlayer = SimplePlayer.getSimplePlayer();
    }

    public void call() {
        mAnalytics.onPhoneButtonClicked();
    }

    /**
     * Listens to when phone button is clicked.
     */
    @OnClick(R.id.call_button)
    void onPhoneButtonClicked() {
        if (mSimplePlayer.isInitialized()) {
            Toast.makeText(mContactActivity,
                    "Por Favor Pare La Música Antes de Llamar", Toast.LENGTH_SHORT).show();
        } else {
            mContactLayoutListener.onPhoneButtonClicked(mCallButton);
            mCallButton.startAnimation(mAnimation);
        }
    }

    @Override
    public void onRadioPlayerReady() {

    }

    @Override
    public void onRadioPlayerError() {

    }

    @Override
    public void onRadioPlayerBuffering() {

    }

    @Override
    public void onReleaseRadio() {
    }

    /**
     * Interface to communicate with {@link ContactController}
     */
    interface ContactLayoutListener {
        void onPhoneButtonClicked(Button button);
    }
}
