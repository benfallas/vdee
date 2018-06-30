package vdee.vdee.parent;

import android.app.Activity;
import android.os.Bundle;

import vdee.vdee.util.FragmentManagerUtils;

/**
 * Parent of all Vdee Activities.
 */
public class ParentActivity extends Activity {

    private FragmentManagerUtils fragmentManagerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManagerUtils = FragmentManagerUtils.initializeFragmentManagerUtils(this);
    }


}
