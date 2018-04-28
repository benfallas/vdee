package vdee.vdee.bibleScreen.chaptersView;

import android.util.Log;
import android.widget.TextView;
import android.widget.GridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import vdee.vdee.R;
import vdee.vdee.data.module.chaptersResponse.ChapterPayload;
import vdee.vdee.data.module.chaptersResponse.ChaptersResponse;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

import static vdee.vdee.bibleScreen.BibleLayout.BOOK_TITLE;

/**
 * Contains layout for chapters.
 */
public class ChaptersLayout extends Subscriber<ChaptersResponse> {

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
    public void onError(Throwable e) {
        Log.d("chaptersResponse", e.getMessage());
    }

    @Override
    public void onNext(ChaptersResponse chaptersResponse) {
        Log.d("chaptersResponse", "onNExt");
        Log.d("chaptersResponse", chaptersResponse.getResponse().toString());
        Log.d("chaptersResponse", chaptersResponse.getResponse().getChapterPayloads().toString());
        payloads = chaptersResponse.getResponse().getChapterPayloads();
        displayGrid();
    }

    private void displayGrid() {
        Log.d("chaptersResponse", "DisplayGrid");
        mGridView.setAdapter(new ChaptersAdapter(mChaptersActivity, payloads));

    }
}
