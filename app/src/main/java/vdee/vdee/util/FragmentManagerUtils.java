package vdee.vdee.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import vdee.vdee.R;

/**
 * Created by ben on 6/24/18.
 */

public class FragmentManagerUtils {

    public static final String HOME_FRAGMENT_TAG = "HOME_FRAGMENT";
    public static final String BIBLE_FRAGMENT_TAG = "BIBLE_FRAGMENT";
    public static final String BOOKS_FRAGMENT_TAG = "BOOKS_FRAGMENT_TAG";
    public static final String CONTACT_US_FRAGMENT_TAG = "CONTACT_US_FRAGMENT";
    public static final String VERSES_FRAGMENT_TAG = "VERSES_FRAGMENT";
    public static final String CHAPTERS_FRAGMENT_TAG = "CHAPTERS_FRAGMENT";

    static FragmentManagerUtils fragmentManagerUtils;

    static Activity mActivity;
    static FragmentTransaction fragmentTransaction;
    static FragmentManager fragmentManager;

    private FragmentManagerUtils() { }

    private static FragmentManagerUtils getFragmentManagerUtilsInstance() {
        if (fragmentManagerUtils == null) {
            fragmentManagerUtils = new FragmentManagerUtils();
        }
        return fragmentManagerUtils;
    }

    public static FragmentManagerUtils initializeFragmentManagerUtils(Activity activity) {
        mActivity = activity;
        fragmentManager = activity.getFragmentManager();
        return getFragmentManagerUtilsInstance();
    }

    public static FragmentManagerUtils getFragmentManagerUtils() {
        return fragmentManagerUtils;
    }

    public static void pushFragment(Fragment fragment, String fragmentTag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment, fragmentTag);
        if (fragmentTag != HOME_FRAGMENT_TAG && fragmentTag != BIBLE_FRAGMENT_TAG
                && fragmentTag != CONTACT_US_FRAGMENT_TAG) {
            fragmentTransaction.addToBackStack(null);
        } else {
            removeAll();
        }
        fragmentTransaction.commit();
    }

    public boolean isCurrentFragmentShown(String fragmentTag) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        return fragment != null && fragment.isVisible();
    }

    private static void removeAll() {
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
    }
}
