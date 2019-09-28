package vdee.evalverde.vdee.features.mainScreen.fragments.bibleFragments.verses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.Component;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.data.module.versesResponse.VersesPayload;
import vdee.evalverde.vdee.data.module.versesResponse.VersesResponse;
import vdee.evalverde.vdee.util.ParentFragment;
import vdee.evalverde.vdee.util.PerFragment;
import vdee.evalverde.vdee.vdeeApi.VdeeApi;

import static vdee.evalverde.vdee.features.mainScreen.fragments.bibleFragments.chapter.ChaptersFragment.CHAPTER_ID;
import static vdee.evalverde.vdee.features.mainScreen.fragments.bibleFragments.chapter.ChaptersFragment.VERSES_TITLE;

public class VersesFragment extends ParentFragment implements VersesResponseListener.Listener {

    private Analytics mAnalytics;
    private RecyclerView mRecyclerView;
    private VersesAdapter mAdapter;
    private VersesResponseListener mListener;
    private ArrayList<VersesPayload> mOriginalVerses;
    private String mVersesTitle;
    private TextView mVersesTitleView;
    private TextView mVersesCopyright;

    @Inject Retrofit mRetrofit;

    public VersesFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        return inflater.inflate(R.layout.verses_layout, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onAttach();
    }

    private void onAttach() {
        mAnalytics = Analytics.getAnalytics();
        mRecyclerView = getActivity().findViewById(R.id.recycler_view_verses);
        mListener = new VersesResponseListener(this);

        mAnalytics.versesView();
        DaggerVersesFragment_VersesComponent.builder()
                .experimentComponent((((VDEEApp) getActivity().getApplicationContext())).getExpComponent())
                .build()
                .inject(this);

        mVersesTitleView = getActivity().findViewById(R.id.verses_title);
        mVersesCopyright = getActivity().findViewById(R.id.verses_copyright);

        String versesId = getArguments().getString(CHAPTER_ID);
        mVersesTitle = getArguments().getString(VERSES_TITLE);

        mRetrofit.create(VdeeApi.class).getVerses(versesId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mListener);
        showDialog();
    }

    private void displayVerses(ArrayList<VersesPayload> versesPayloads) {
        mAdapter = new VersesAdapter(getActivity(), versesPayloads);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError(Throwable e) {
        hideDialog();
        showNetworkError();
    }

    @Override
    public void onNext(VersesResponse versesResponse) {
        mVersesTitleView.setText(mVersesTitle);
        hideDialog();
        mOriginalVerses = versesResponse.getResponse().getVersesPayload();
        displayVerses(mOriginalVerses);
        mVersesCopyright.setText(Html.fromHtml(mOriginalVerses.get(0).getCopyright()));
    }

    @PerFragment
    @Component(dependencies = ExperimentComponent.class)
    interface VersesComponent {
        void inject(VersesFragment versesFragment);
    }
}
