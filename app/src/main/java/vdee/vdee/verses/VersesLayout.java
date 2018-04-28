package vdee.vdee.verses;

import butterknife.ButterKnife;
import vdee.vdee.R;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

public class VersesLayout {

    private ToolbarSupport mToolbar;

    VersesActivity mVersesActivity;

    public VersesLayout(VersesActivity versesActivity) {
        mVersesActivity = versesActivity;
        mVersesActivity.setContentView(R.layout.verses_layout);
        ButterKnife.bind(this, mVersesActivity);
        mToolbar = new BackArrowToolbar(mVersesActivity, "TEST");
    }

}
