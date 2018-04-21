package vdee.vdee.bibleScreen;

import android.util.Log;

import butterknife.ButterKnife;
import rx.Subscriber;
import vdee.vdee.R;
import vdee.vdee.data.module.booksResponse.Books;
import vdee.vdee.data.module.booksResponse.BooksResponse;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

/**
 *Bible Layout holds the logic for most activity happening on Main Screen.
 */
public class BibleLayout extends Subscriber<BooksResponse>{

    private BibleActivity mBibleActivity;
    private ToolbarSupport mToolbar;

    public BibleLayout(BibleActivity bibleActivity) {
        mBibleActivity = bibleActivity;
        
        bibleActivity.setContentView(R.layout.activity_bible);
        ButterKnife.bind(this, mBibleActivity);
        mToolbar = new BackArrowToolbar(mBibleActivity, R.string.biblia);
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError(Throwable e) {
        Log.d("bibleResponse", e.getMessage());
    }

    @Override
    public void onNext(BooksResponse response) {
        Log.d("bibleResponse", response.toString());

    }
}
