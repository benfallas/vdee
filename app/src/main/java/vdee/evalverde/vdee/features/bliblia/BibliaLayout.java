package vdee.evalverde.vdee.features.bliblia;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Subscriber;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.data.models.BookInfo;
import vdee.evalverde.vdee.data.models.ChapterInfo;
import vdee.evalverde.vdee.data.module.booksResponse.Book;
import vdee.evalverde.vdee.data.module.booksResponse.BooksResponse;
import vdee.evalverde.vdee.data.module.chaptersResponse.ChapterPayload;
import vdee.evalverde.vdee.data.module.chaptersResponse.ChaptersResponse;
import vdee.evalverde.vdee.util.StorageUtils;

public class BibliaLayout implements BooksAdapter.ViewHolderListener{

    private Analytics analytics;
    private BibliaActivity bibliaActivity;
    private BooksAdapter booksAdapter;
    private Listener listener;
    private RecyclerView listOfBooks;
    private Retrofit retrofit;

    private Map<String, BookInfo> originalBooks;
    private ArrayList<ChapterPayload> chapterPayloads;

    BibliaLayout(BibliaActivity bibliaActivity, Listener listener, Retrofit retrofit) {
        this.bibliaActivity = bibliaActivity;
        this.listener = listener;
        this.retrofit = retrofit;
        originalBooks = StorageUtils.getListOfBooks();
        chapterPayloads = new ArrayList<>();

        booksAdapter = new BooksAdapter(bibliaActivity, originalBooks, this);


        analytics = Analytics.getAnalytics();
        analytics.biblePageView();

        listOfBooks = bibliaActivity.findViewById(R.id.recycler_view_books);
        listOfBooks.setLayoutManager(new LinearLayoutManager(bibliaActivity.getApplicationContext()));
        listOfBooks.setAdapter(booksAdapter);
    }


    @Override
    public void onBibleBookClicked(int position) {
        chapterPayloads = new ArrayList<>();
        listener.onBibleBookClicked(originalBooks.get(String.valueOf(position)));
    }

    @Override
    public void onChapterButtonClicked(ChapterInfo chapterInfo) {
        listener.onChapterButtonClicked(chapterInfo);
    }

    interface Listener {
        void onBibleBookClicked(BookInfo bookInfo);

        void onChapterButtonClicked(ChapterInfo chapterInfo);
    }
}
