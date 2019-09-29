package vdee.evalverde.vdee.features.bliblia;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

import retrofit2.Retrofit;
import rx.Subscriber;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.data.module.booksResponse.Book;
import vdee.evalverde.vdee.data.module.booksResponse.BooksResponse;
import vdee.evalverde.vdee.data.module.chaptersResponse.ChapterPayload;
import vdee.evalverde.vdee.data.module.chaptersResponse.ChaptersResponse;

public class BibliaLayout
        implements BibleResponseListener.Listener, ChaptersResponseListener.Listener,
        BooksAdapter.ViewHolderListener{

    private Analytics analytics;
    private BibliaActivity bibliaActivity;
    private BooksAdapter booksAdapter;
    private Listener listener;
    private RecyclerView listOfBooks;
    private Retrofit retrofit;

    private ArrayList<Book> originalBooks;
    private ArrayList<ChapterPayload> chapterPayloads;

    BibliaLayout(BibliaActivity bibliaActivity, Listener listener, Retrofit retrofit) {
        this.bibliaActivity = bibliaActivity;
        this.listener = listener;
        this.retrofit = retrofit;
        originalBooks = new ArrayList<>();
        chapterPayloads = new ArrayList<>();

        booksAdapter = new BooksAdapter(bibliaActivity, originalBooks, chapterPayloads, this);


        analytics = Analytics.getAnalytics();
        analytics.biblePageView();

        listOfBooks = bibliaActivity.findViewById(R.id.recycler_view_books);
        listOfBooks.setLayoutManager(new LinearLayoutManager(bibliaActivity.getApplicationContext()));
        listOfBooks.setAdapter(booksAdapter);
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError() {
        bibliaActivity.hideDialog();
        bibliaActivity.showNetworkError();
    }

    @Override
    public void onNext(ChaptersResponse chaptersResponse) {
        bibliaActivity.hideDialog();
        if (chaptersResponse.getResponse().getChapterPayloads() != null) {
            chapterPayloads = chaptersResponse.getResponse().getChapterPayloads();
            booksAdapter.updateBooks(originalBooks, chapterPayloads);
        }
    }

    @Override
    public void onError(Throwable e) {
        bibliaActivity.hideDialog();
        bibliaActivity.showNetworkError();
    }

    @Override
    public void onNext(BooksResponse booksResponse) {
        bibliaActivity.hideDialog();
        showBooks(booksResponse);

    }

    private void displayBooks(ArrayList<Book> books) {
        booksAdapter.updateBooks(books, chapterPayloads);
    }

    private void showBooks(BooksResponse booksResponse) {
        if (booksResponse.getResponse().getBooks() != null) {
            originalBooks = booksResponse.getResponse().getBooks();
            displayBooks(originalBooks);
        }
    }

    @Override
    public void onBibleBookClicked(int position) {
        chapterPayloads = new ArrayList<>();
        listener.onBibleBookClicked(originalBooks.get(position).getId());
    }

    interface Listener {
        void onBibleBookClicked(String bookTitle);
    }
}
