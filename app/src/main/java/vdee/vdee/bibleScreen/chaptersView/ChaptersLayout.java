package vdee.vdee.bibleScreen.chaptersView;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.GridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import vdee.vdee.R;
import vdee.vdee.data.module.chaptersResponse.ChapterPayload;
import vdee.vdee.data.module.chaptersResponse.ChaptersResponse;
import vdee.vdee.mainScreen.fragments.bibleFragments.chapter.ChaptersAdapter;
import vdee.vdee.verses.VersesActivity;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

import static android.view.View.GONE;
import static vdee.vdee.bibleScreen.BibleLayout.BOOK_TITLE;

/**
 * Contains layout for chapters.
 */
public class ChaptersLayout extends Subscriber<ChaptersResponse> {

    public static String CHAPTER_ID = "CHAPTER_ID";

    private ChaptersActivity mChaptersActivity;
    private ToolbarSupport mToolbar;

    private ArrayList<ChapterPayload> payloads;

    @BindView(R.id.chapters_grid) GridView mGridView;
    @BindView(R.id.loading_spineer) ProgressBar mLoadingSpinner;
    @BindView(R.id.cloud_image) ImageView mNetworkErrorImage;

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
        mLoadingSpinner.setVisibility(GONE);
        mNetworkErrorImage.setVisibility(View.VISIBLE);
        mGridView.setVisibility(GONE);
    }

    @Override
    public void onNext(ChaptersResponse chaptersResponse) {
        mLoadingSpinner.setVisibility(GONE);
        mNetworkErrorImage.setVisibility(GONE);
        mGridView.setVisibility(View.VISIBLE);
        payloads = chaptersResponse.getResponse().getChapterPayloads();
        displayGrid();
    }

    private void displayGrid() {
    }
    
}
