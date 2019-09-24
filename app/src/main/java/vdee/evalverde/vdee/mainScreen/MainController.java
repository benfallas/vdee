package vdee.evalverde.vdee.mainScreen;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.mainScreen.fragments.HomeFragment;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.mainScreen.fragments.bibleFragments.BibleFragment;
import vdee.evalverde.vdee.mainScreen.fragments.contactUs.ContactUs;
import vdee.evalverde.vdee.mediaPlayer.SimplePlayer;
import vdee.evalverde.vdee.permissions.PermissionsManager;
import vdee.evalverde.vdee.phoneCallReceiver.CallReceiver;
import vdee.evalverde.vdee.util.FragmentManagerUtils;
import vdee.evalverde.vdee.util.PerController;

import static vdee.evalverde.vdee.util.FragmentManagerUtils.BIBLE_FRAGMENT_TAG;
import static vdee.evalverde.vdee.util.FragmentManagerUtils.CONTACT_US_FRAGMENT_TAG;
import static vdee.evalverde.vdee.util.FragmentManagerUtils.HOME_FRAGMENT_TAG;

/**
 * Main Controller holds the logic for most activity happening on Main Screen.
 */
class MainController
        implements MainLayout.MainLayoutListener,
        CallReceiver.CallReceiverListener {

    private int cacheExpiration;
    private static String experiment;
    private static String mMultipleRadiosupport;
    private Boolean FBExpName;
    private static Boolean mMultipleRadioSupport;
    private Boolean isPaused;

    private MainActivity mMainActivity;
    private Analytics mAnalytics;
    private SimplePlayer mSimplePlayer;
    private IntentReceiver mIntentReceiver;
    private PhoneStateListener mPhoneStateListener;
    private TelephonyManager mTelephonyManager;
    private FragmentManager mFragmentManager;
    private FragmentManagerUtils fragmentManagerUtils;
    private Fragment mFragment;

    @Inject MainLayout mMainLayout;
    private PermissionsManager mPermissionsManager;

    private long startNow;
    private long endNow;
    private long minutes;

    MainController(@NonNull MainActivity mainActivity) {
        mMainActivity = mainActivity;
        mAnalytics = Analytics.getAnalytics();
        mPermissionsManager = PermissionsManager.getPermissionsManager();
        fragmentManagerUtils = FragmentManagerUtils.getFragmentManagerUtils();

        DaggerMainController_MainControllerComponent.builder()
                .mainControllerModule(new MainControllerModule(mMainActivity, this, mAnalytics))
                .experimentComponent(((VDEEApp) mainActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        mIntentReceiver = new IntentReceiver();

        mMainActivity.registerReceiver(mIntentReceiver, intentFilter);

        if (mPermissionsManager.PHONE_STATE_PERMISSION_GRANTED) {
            mTelephonyManager = (TelephonyManager) mMainActivity.getSystemService(mMainActivity.getApplicationContext().TELEPHONY_SERVICE);
            mPhoneStateListener = new CallReceiver(this);
            mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

        onAttach();
    }

    private void onAttach() {
    }

    @Override
    public boolean onNavigationItemSelected(int itemId) {
        String id = "";
        switch (itemId) {
            case R.id.homeItem:
                mFragment = new HomeFragment();
                id = HOME_FRAGMENT_TAG;
                break;
            case R.id.bible:
                mFragment = new BibleFragment();
                id = BIBLE_FRAGMENT_TAG;
                break;
            case R.id.contactItem:
                mFragment = new ContactUs();
                id = CONTACT_US_FRAGMENT_TAG;
                break;
            default:
                return false;
        }
        fragmentManagerUtils.pushFragment(mFragment, id);
        return true;
    }

    @Override
    public void onIncomingCall() {
        mSimplePlayer.releasePlayer();
    }

    @Override
    public void onCallEnded() {
        if(FBExpName && isPaused) {
            mSimplePlayer.releasePlayer();
        } else
            mSimplePlayer.initPlayer();
    }

    /**
     * releases receiver.
     */
    public void onPaused() {
        try {
            mMainActivity.unregisterReceiver(mIntentReceiver);
        } catch (IllegalArgumentException e) {

        }
    }

    private class IntentReceiver extends BroadcastReceiver {

        private final String headsetReceiver = "HEADSET RECEIVER";
        private final String headsetPlugged = "Headset is plugged";
        private final String headsetUnplugged = "Headset is unplugged";
        private final String headsetErr = "Something went wrong";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        if (mSimplePlayer != null) {
                            mSimplePlayer.releasePlayer();
                        }
                        Log.d(headsetReceiver, headsetUnplugged);
                        break;
                    case 1:
                        Log.d(headsetReceiver, headsetPlugged);
                        break;
                    default:
                        Log.d(headsetReceiver, headsetErr);
                }
            }
        }
    }

    @PerController
    @Component(
           dependencies = ExperimentComponent.class, modules = MainControllerModule.class)
    interface MainControllerComponent {
        void inject(MainController mainController);
    }

    @Module
    static class MainControllerModule {
        private final MainActivity mMainActivity;
        private final MainLayout.MainLayoutListener mMainLayoutListener;
        private final Analytics mAnalytics;

        MainControllerModule(
                @NonNull MainActivity mainActivity,
                @NonNull MainLayout.MainLayoutListener listener,
                @NonNull Analytics analytics) {
            mMainActivity = mainActivity;
            mMainLayoutListener = listener;
            mAnalytics = analytics;
        }

        @Provides
        @PerController
        MainLayout providesMainLayout() {
            return new MainLayout(mMainActivity, mMainLayoutListener, mAnalytics);
        }
    }
}
