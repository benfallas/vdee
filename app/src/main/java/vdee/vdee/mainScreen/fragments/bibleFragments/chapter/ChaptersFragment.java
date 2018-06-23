package vdee.vdee.mainScreen.fragments.bibleFragments.chapter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import vdee.vdee.R;
import vdee.vdee.data.module.chaptersResponse.ChapterPayload;
import vdee.vdee.data.module.chaptersResponse.ChaptersResponse;

/**
 * Created by ben on 6/23/18.
 */

public class ChaptersFragment extends Fragment
        implements ChaptersResponseListener.Listener, ChaptersAdapter.ChapterButtonListener {

    public static String Chapter_ID = "CHAPTER_ID";

    private ArrayList<ChapterPayload> payloads;
    private GridView mGridView;

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
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError() { }

    @Override
    public void onNext(ChaptersResponse chaptersResponse) {
        payloads = chaptersResponse.getResponse().getChapterPayloads();
        displayGrid();
    }

    private void displayGrid() {
        mGridView.setAdapter(new ChaptersAdapter(getActivity(), payloads, this));
    }

    @Override
    public void onChapterButtonClicked(int position) {
        String chapterId = payloads.get(position).getId();
        Bundle bundle = new Bundle();
        bundle.putString(Chapter_ID, chapterId);

    }
}
