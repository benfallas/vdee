package vdee.vdee.bibleScreen;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import vdee.vdee.R;
import vdee.vdee.data.module.booksResponse.Book;
import vdee.vdee.data.module.booksResponse.Books;
import vdee.vdee.data.module.booksResponse.BooksResponse;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

/**
 *Bible Layout holds the logic for most activity happening on Main Screen.
 */
public class BibleLayout extends Subscriber<BooksResponse> implements BooksAdapter.ViewHolderListener {

    private BibleActivity mBibleActivity;
    private ToolbarSupport mToolbar;
    private BooksAdapter mAdapater;

    @BindView(R.id.recycler_view_books) RecyclerView mRecyclerViewBooks;

    private ArrayList<Book> books;

    public BibleLayout(BibleActivity bibleActivity) {
        mBibleActivity = bibleActivity;
        
        bibleActivity.setContentView(R.layout.activity_bible);
        ButterKnife.bind(this, mBibleActivity);
        mToolbar = new BackArrowToolbar(mBibleActivity, R.string.biblia);
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError(Throwable e) { }

    @Override
    public void onNext(BooksResponse response) {
        displayBooks(response.getResponse().getBooks());
    }

    private void displayBooks(ArrayList<Book> books) {
        mAdapater = new BooksAdapter(mBibleActivity.getApplicationContext(),
                books,
                this);

        mRecyclerViewBooks.setLayoutManager(new LinearLayoutManager(mBibleActivity.getApplicationContext()));
        mRecyclerViewBooks.setAdapter(mAdapater);
    }

    @Override
    public void onBibleBookClicked(int position) {
        Toast.makeText(mBibleActivity.getApplicationContext(), "TESt", Toast.LENGTH_SHORT).show();
    }
}
