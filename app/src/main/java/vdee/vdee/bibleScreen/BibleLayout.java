package vdee.vdee.bibleScreen;

import butterknife.ButterKnife;
import vdee.vdee.R;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

/**
 *Bible Layout holds the logic for most activity happening on Main Screen.
 */
public class BibleLayout {

    private BibleActivity mBibleActivity;
    private ToolbarSupport mToolbar;

    public BibleLayout(BibleActivity bibleActivity) {
        mBibleActivity = bibleActivity;
        
        bibleActivity.setContentView(R.layout.activity_bible);
        ButterKnife.bind(this, mBibleActivity);
        mToolbar = new BackArrowToolbar(mBibleActivity, R.string.biblia);
    }
}
