package vdee.vdee.maranatha;

import butterknife.ButterKnife;
import vdee.vdee.R;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

public class MaranathaLayout {

    private MaranathaActivity mMaranathaActivity;
    private ToolbarSupport mToolbar;

    public MaranathaLayout(MaranathaActivity maranathaActivity) {
        mMaranathaActivity = maranathaActivity;

        mMaranathaActivity.setContentView(R.layout.activity_maranatha);

        ButterKnife.bind(this, mMaranathaActivity);
        mToolbar = new BackArrowToolbar(mMaranathaActivity, R.string.maranatha);
    }
}
