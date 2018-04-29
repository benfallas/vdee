package vdee.vdee.bibleScreen.chaptersView;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.GridView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import vdee.vdee.R;
import vdee.vdee.data.module.chaptersResponse.ChapterPayload;
import vdee.vdee.data.module.chaptersResponse.ChaptersResponse;
import vdee.vdee.verses.VersesActivity;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

import static vdee.vdee.bibleScreen.BibleLayout.BOOK_TITLE;

/**
 * Contains layout for chapters.
 */
public class ChaptersLayout extends Subscriber<ChaptersResponse> implements ChaptersAdapter.ChapterButtonListener {

    public static String CHAPTER_ID = "CHAPTER_ID";

    private ChaptersActivity mChaptersActivity;
    private ToolbarSupport mToolbar;

    private ArrayList<ChapterPayload> payloads;

    @BindView(R.id.chapters_grid) GridView mGridView;

    public ChaptersLayout(ChaptersActivity chaptersActivity) {
        mChaptersActivity = chaptersActivity;
        mChaptersActivity.setContentView(R.layout.chapters_layout);
        ButterKnife.bind(this, mChaptersActivity);
        String title = mChaptersActivity.getIntent().getExtras().getString(BOOK_TITLE);
        mToolbar = new BackArrowToolbar(mChaptersActivity, title);
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError(Throwable e) { }

    @Override
    public void onNext(ChaptersResponse chaptersResponse) {
        payloads = chaptersResponse.getResponse().getChapterPayloads();
        displayGrid();
    }

    private void displayGrid() {
        mGridView.setAdapter(new ChaptersAdapter(mChaptersActivity, payloads, this));
    }

    @Override
    public void onChapterButtonClicked(int position) {
        String chapterId = payloads.get(position).getId();
        Intent intent = new Intent(mChaptersActivity, VersesActivity.class);
        intent.putExtra(CHAPTER_ID, chapterId);
        mChaptersActivity.startActivity(intent);
    }
}
