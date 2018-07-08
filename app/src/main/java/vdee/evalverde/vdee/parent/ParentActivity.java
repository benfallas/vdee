package vdee.evalverde.vdee.parent;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import vdee.evalverde.vdee.util.FragmentManagerUtils;

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
        }
    }


}
