package vdee.vdee.verses;

import android.util.Log;

import butterknife.ButterKnife;
import rx.Subscriber;
import vdee.vdee.R;
import vdee.vdee.data.module.versesResponse.VersesResponse;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

public class VersesLayout extends Subscriber<VersesResponse> {

    private ToolbarSupport mToolbar;

    VersesActivity mVersesActivity;

    public VersesLayout(VersesActivity versesActivity) {
        mVersesActivity = versesActivity;
        mVersesActivity.setContentView(R.layout.verses_layout);
        ButterKnife.bind(this, mVersesActivity);
        mToolbar = new BackArrowToolbar(mVersesActivity, "TEST");
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError(Throwable e) {
        Log.d("VERSES RESPONSE", e.getMessage());
    }

    @Override
    public void onNext(VersesResponse versesResponse) {
        Log.d("VERSES RESPONSE", versesResponse.toString());
    }
}
