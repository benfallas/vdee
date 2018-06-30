package vdee.vdee.parent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import vdee.vdee.util.FragmentManagerUtils;

/**
 * Parent of all Vdee Activities.
 */
public class ParentActivity extends Activity {

    private FragmentManager fragmentManager;
    private FragmentManagerUtils fragmentManagerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        fragmentManagerUtils = FragmentManagerUtils.initializeFragmentManagerUtils(this);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            fragmentManager.popBackStack();
            if (fragmentManager.getBackStackEntryCount() == 0) {
                super.onBackPressed();
            }
        }
    }


}
