package vdee.vdee.mainScreen.fragments.bibleFragments.chapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.Component;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import vdee.vdee.R;
import vdee.vdee.VDEEApp;
import vdee.vdee.component.ExperimentComponent;
import vdee.vdee.data.module.chaptersResponse.ChapterPayload;
import vdee.vdee.data.module.chaptersResponse.ChaptersResponse;
import vdee.vdee.mainScreen.fragments.bibleFragments.verses.VersesFragment;
import vdee.vdee.util.FragmentManagerUtils;
import vdee.vdee.util.ParentFragment;
import vdee.vdee.util.PerFragment;
import vdee.vdee.vdeeApi.VdeeApi;

import static vdee.vdee.mainScreen.fragments.bibleFragments.BibleFragment.BOOK_ID;
import static vdee.vdee.util.FragmentManagerUtils.BIBLE_FRAGMENT_TAG;
import static vdee.vdee.util.FragmentManagerUtils.CHAPTERS_FRAGMENT_TAG;
import static vdee.vdee.util.FragmentManagerUtils.VERSES_FRAGMENT_TAG;

public class ChaptersFragment extends ParentFragment
        implements ChaptersResponseListener.Listener, ChaptersAdapter.ChapterButtonListener {

    public static String CHAPTER_ID = "CHAPTER_ID";

    private ArrayList<ChapterPayload> payloads;
    private GridView mGridView;
    private ChaptersResponseListener mListener;

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
        mGridView = getActivity().findViewById(R.id.chapters_grid);
        mListener = new ChaptersResponseListener(this);

        DaggerChaptersFragment_ChaptersComponent.builder()
                .experimentComponent((((VDEEApp) getActivity().getApplicationContext())).getExpComponent())
                .build()
                .inject(this);

        String bookId = getArguments().getString(BOOK_ID);


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
        fragment.setArguments(bundle);
        FragmentManagerUtils.pushFragment(fragment, VERSES_FRAGMENT_TAG);
    }

    @PerFragment
    @Component(dependencies = ExperimentComponent.class)
    interface ChaptersComponent {
        void inject(ChaptersFragment chaptersFragment);
    }
}
