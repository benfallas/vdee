package vdee.vdee.verses;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import vdee.vdee.R;
import vdee.vdee.data.module.versesResponse.VersesPayload;
import vdee.vdee.data.module.versesResponse.VersesResponse;
import vdee.vdee.mainScreen.fragments.bibleFragments.verses.VersesAdapter;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

import static android.view.View.GONE;

public class VersesLayout extends Subscriber<VersesResponse> {

    private ToolbarSupport mToolbar;
    private ArrayList<VersesPayload> mOriginalVerses;
    private VersesAdapter mVerseAdapter;

    VersesActivity mVersesActivity;

    @BindView(R.id.recycler_view_verses) RecyclerView mVersesView;
    @BindView(R.id.loading_spineer) ProgressBar mLoadingSpinner;
    @BindView(R.id.cloud_image) ImageView mNetworkErrorImage;

    public VersesLayout(VersesActivity versesActivity) {
        mVersesActivity = versesActivity;
        mVersesActivity.setContentView(R.layout.verses_layout);
        ButterKnife.bind(this, mVersesActivity);
        mToolbar = new BackArrowToolbar(mVersesActivity, "");
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError(Throwable e) {
        mLoadingSpinner.setVisibility(GONE);
        mNetworkErrorImage.setVisibility(View.VISIBLE);
        mVersesView.setVisibility(GONE);
    }

    @Override
    public void onNext(VersesResponse versesResponse) {
        mLoadingSpinner.setVisibility(GONE);
        mNetworkErrorImage.setVisibility(GONE);
        mVersesView.setVisibility(View.VISIBLE);
        mOriginalVerses = versesResponse.getResponse().getVersesPayload();
        displayVerses(mOriginalVerses);
    }

    private void displayVerses(ArrayList<VersesPayload> verses) {

    }
}
