package vdee.vdee.verses;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import vdee.vdee.R;
import vdee.vdee.data.module.versesResponse.VersesPayload;
import vdee.vdee.data.module.versesResponse.VersesResponse;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

public class VersesLayout extends Subscriber<VersesResponse> {

    private ToolbarSupport mToolbar;
    private ArrayList<VersesPayload> mOriginalVerses;
    private VersesAdapter mVerseAdapter;

    VersesActivity mVersesActivity;

    @BindView(R.id.recycler_view_verses) RecyclerView mVersesView;

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
        Log.d("VERSES RESPONSE", e.getMessage());
    }

    @Override
    public void onNext(VersesResponse versesResponse) {
        mOriginalVerses = versesResponse.getResponse().getVersesPayload();
        displayVerses(mOriginalVerses);
    }

    private void displayVerses(ArrayList<VersesPayload> verses) {
        mVerseAdapter = new VersesAdapter(mVersesActivity, verses);
        mVersesView.setLayoutManager(new LinearLayoutManager(mVersesActivity.getApplicationContext()));
        mVersesView.setAdapter(mVerseAdapter);
    }
}
