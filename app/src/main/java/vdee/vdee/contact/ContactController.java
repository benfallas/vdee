package vdee.vdee.contact;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import vdee.vdee.VDEEApp;
import vdee.vdee.analytics.Analytics;
import vdee.vdee.component.ExperimentComponent;
import vdee.vdee.phoneCallReceiver.CallReceiver;
import vdee.vdee.util.PerController;

/**
 * Contact Controller holds the logic for most of the activities happening on Contact Us Screen.
 */
public class ContactController
        implements ContactLayout.ContactLayoutListener,
        CallReceiver.CallReceiverListener {

    private ContactActivity mContactActivity;
    private Analytics mAnalytics;

    private PhoneStateListener mPhoneStateListener;
    private TelephonyManager mTelephonyManager;


    @Inject ContactLayout mContactLayout;

    public ContactController(@NonNull ContactActivity contactActivity) {
        mContactActivity = contactActivity;
        mAnalytics = Analytics.getAnalytics();

        DaggerContactController_ContactControllerComponent.builder()
                .contactControllerModule(new ContactControllerModule(
                        mContactActivity,
                        this,
                        mAnalytics))
                .experimentComponent(((VDEEApp) contactActivity.getApplicationContext())
                        .getExpComponent())
                .build()
                .inject(this);

        mTelephonyManager = (TelephonyManager)
                mContactActivity.getSystemService(
                        mContactActivity.getApplicationContext().TELEPHONY_SERVICE);
        mPhoneStateListener = new CallReceiver(this);
        mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onPhoneButtonClicked(Button button) {

        if (checkPermission(android.Manifest.permission.CALL_PHONE)) {
            mContactLayout.call();
            dial();
        }else {
            Toast.makeText(mContactActivity,
                    "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(
                mContactActivity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void dial() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:8314220647"));
        mContactActivity.startActivity(callIntent);
    }

    @Override
    public void onIncomingCall() {}

    @Override
    public void onCallEnded() {

    }

    @PerController
    @Component(
            dependencies = ExperimentComponent.class, modules = ContactControllerModule.class)
    interface ContactControllerComponent {
        void inject(ContactController contactController);
    }

    @Module
    static class ContactControllerModule {
        private final ContactActivity mContactActivity;
        private final ContactLayout.ContactLayoutListener mContactLayoutListener;
        private final Analytics mAnalytics;

        ContactControllerModule(
                @NonNull ContactActivity contactActivity,
                @NonNull ContactLayout.ContactLayoutListener listener,
                @NonNull Analytics analytics) {
            mContactActivity = contactActivity;
            mContactLayoutListener = listener;
            mAnalytics = analytics;
        }

        @Provides
        @PerController
        ContactLayout providesContactLayout() {
            return new ContactLayout(mContactActivity, mContactLayoutListener, mAnalytics);
        }
    }
}
