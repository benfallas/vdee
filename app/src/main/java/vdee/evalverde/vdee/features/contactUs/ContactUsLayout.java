package vdee.evalverde.vdee.features.contactUs;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import vdee.evalverde.vdee.Animations.MyBounceInterpolator;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.analytics.Analytics;

public class ContactUsLayout {

    private ContactUsActivity contactUsActivity;

    private Animation animation;
    private MyBounceInterpolator interpolator;
    private Button callButton;
    private ContactUsLayoutListener contactUsLayoutListener;

    private Analytics analytics;


    ContactUsLayout(
            final ContactUsActivity contactUsActivity,
            final ContactUsLayoutListener contactUsLayoutListener) {
        this.contactUsActivity = contactUsActivity;

        analytics = Analytics.getAnalytics();
        this.contactUsLayoutListener = contactUsLayoutListener;
        analytics.contactPageView();
        animation = AnimationUtils.loadAnimation(contactUsActivity, R.anim.bounce);
        interpolator = new MyBounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);

        callButton = contactUsActivity.findViewById(R.id.call_button);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactUsLayoutListener.onDialClicked();
                analytics.onPhoneButtonClicked();

                callButton.startAnimation(animation);
            }
        });
    }

    interface ContactUsLayoutListener {
        void onDialClicked();
    }
}
