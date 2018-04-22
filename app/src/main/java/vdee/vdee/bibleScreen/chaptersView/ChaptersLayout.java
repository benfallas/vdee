package vdee.vdee.bibleScreen.chaptersView;

import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vdee.vdee.R;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

import static vdee.vdee.bibleScreen.BibleLayout.BOOK_TITLE;

/**
 * Contains layout for chapters.
 */
public class ChaptersLayout {

    private ChaptersActivity mChaptersActivity;
    private ToolbarSupport mToolbar;

    public ChaptersLayout(ChaptersActivity chaptersActivity) {
        mChaptersActivity = chaptersActivity;
        mChaptersActivity.setContentView(R.layout.chapters_layout);
        ButterKnife.bind(this, mChaptersActivity);
        String title = mChaptersActivity.getIntent().getExtras().getString(BOOK_TITLE);
        mToolbar = new BackArrowToolbar(mChaptersActivity, title);
    }
}
