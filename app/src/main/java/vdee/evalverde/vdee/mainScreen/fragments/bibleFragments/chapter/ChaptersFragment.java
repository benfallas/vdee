package vdee.evalverde.vdee.mainScreen.fragments.bibleFragments.chapter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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
import vdee.evalverde.vdee.data.module.chaptersResponse.ChapterPayload;
import vdee.evalverde.vdee.data.module.chaptersResponse.ChaptersResponse;
import vdee.evalverde.vdee.mainScreen.fragments.bibleFragments.verses.VersesFragment;
import vdee.evalverde.vdee.util.FragmentManagerUtils;
import vdee.evalverde.vdee.util.ParentFragment;
import vdee.evalverde.vdee.util.PerFragment;
import vdee.evalverde.vdee.vdeeApi.VdeeApi;

import static vdee.evalverde.vdee.mainScreen.fragments.bibleFragments.BibleFragment.BOOK_ID;
import static vdee.evalverde.vdee.mainScreen.fragments.bibleFragments.BibleFragment.BOOK_TITLE;
import static vdee.evalverde.vdee.util.FragmentManagerUtils.VERSES_FRAGMENT_TAG;

public class ChaptersFragment extends ParentFragment
        implements ChaptersResponseListener.Listener, ChaptersAdapter.ChapterButtonListener {

    public static String CHAPTER_ID = "CHAPTER_ID";
    public static String VERSES_TITLE = "VERSES_TITLE";

    private Analytics mAnalytic;
    private ArrayList<ChapterPayload> payloads;
    private GridView mGridView;
    private ChaptersResponseListener mListener;
    private TextView mChaptersTitle;
    private String bookTitle;

    @Inject Retrofit retrofit;

    public ChaptersFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        return inflater.inflate(R.layout.chapters_layout, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onAttach();
    }

    private void onAttach() {
        mAnalytic = Analytics.getAnalytics();
        mGridView = getActivity().findViewById(R.id.chapters_grid);
        mListener = new ChaptersResponseListener(this);
        mAnalytic.chaptersView();
        DaggerChaptersFragment_ChaptersComponent.builder()
                .experimentComponent((((VDEEApp) getActivity().getApplicationContext())).getExpComponent())
                .build()
                .inject(this);
        mChaptersTitle = getActivity().findViewById(R.id.chapters_title);

        String bookId = getArguments().getString(BOOK_ID);
        bookTitle = getArguments().getString(BOOK_TITLE);

        retrofit.create(VdeeApi.class).getChaptersByBookId(bookId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mListener);
        showDialog();
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError() {
        hideDialog();
        showNetworkError();
    }

    @Override
    public void onNext(ChaptersResponse chaptersResponse) {
        hideDialog();
        mChaptersTitle.setText(bookTitle);
        payloads = chaptersResponse.getResponse().getChapterPayloads();
        displayGrid();
    }

    private void displayGrid() {
        mGridView.setAdapter(new ChaptersAdapter(getActivity(), payloads, this));
    }

    @Override
    public void onChapterButtonClicked(int position) {
        Fragment fragment = new VersesFragment();
        String chapterId = payloads.get(position).getId();
        Bundle bundle = new Bundle();
        bundle.putString(CHAPTER_ID, chapterId);
        bundle.putString(VERSES_TITLE, bookTitle + " " + String.valueOf(position + 1));
        fragment.setArguments(bundle);
        FragmentManagerUtils.pushFragment(fragment, VERSES_FRAGMENT_TAG);
    }

    @PerFragment
    @Component(dependencies = ExperimentComponent.class)
    interface ChaptersComponent {
        void inject(ChaptersFragment chaptersFragment);
    }
}
