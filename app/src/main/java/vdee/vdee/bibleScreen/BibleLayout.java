package vdee.vdee.bibleScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import vdee.vdee.R;
import vdee.vdee.bibleScreen.chaptersView.ChaptersActivity;
import vdee.vdee.data.module.booksResponse.Book;
import vdee.vdee.data.module.booksResponse.Books;
import vdee.vdee.data.module.booksResponse.BooksResponse;
import vdee.vdee.views.layout.toolbar.ToolbarSupport;
import vdee.vdee.views.layout.toolbar.backArrowToolbar.BackArrowToolbar;

import static android.view.View.GONE;

/**
 *Bible Layout holds the logic for most activity happening on Main Screen.
 */
public class BibleLayout extends Subscriber<BooksResponse> implements BooksAdapter.ViewHolderListener {

    public static String BOOK_TITLE = "BOOK_TITLE";
    public static String BOOK_ID = "BOOK_ID";

    private BibleActivity mBibleActivity;
    private ToolbarSupport mToolbar;
    private BooksAdapter mAdapater;

    private ArrayList<Book> originalBooks;

    @BindView(R.id.recycler_view_books) RecyclerView mRecyclerViewBooks;
    @BindView(R.id.loading_spineer) ProgressBar mLoadingSpinner;
    @BindView(R.id.cloud_image) ImageView mNetworkErrorImage;

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
    public void onError(Throwable e) {
        mLoadingSpinner.setVisibility(GONE);
        mNetworkErrorImage.setVisibility(View.VISIBLE);
        mRecyclerViewBooks.setVisibility(GONE);
    }

    @Override
    public void onNext(BooksResponse response) {
        mLoadingSpinner.setVisibility(GONE);
        mNetworkErrorImage.setVisibility(GONE);
        mRecyclerViewBooks.setVisibility(View.VISIBLE);
        originalBooks = response.getResponse().getBooks();
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
    public void onBibleBookClicked(int bookPosition) {
        String bookTitle = originalBooks.get(bookPosition).getName();
        String bookId = originalBooks.get(bookPosition).getId();
        Intent intent = new Intent(mBibleActivity, ChaptersActivity.class);
        intent.putExtra(BOOK_TITLE, bookTitle);
        intent.putExtra(BOOK_ID, bookId);
        mBibleActivity.startActivity(intent);
    }
}
