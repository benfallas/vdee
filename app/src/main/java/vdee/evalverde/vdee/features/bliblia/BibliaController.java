package vdee.evalverde.vdee.features.bliblia;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import dagger.Component;
import dagger.Provides;
import retrofit2.Retrofit;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.data.models.BiblePayload;
import vdee.evalverde.vdee.data.models.BookInfo;
import vdee.evalverde.vdee.data.models.ChapterInfo;
import vdee.evalverde.vdee.data.models.VerseInfo;
import vdee.evalverde.vdee.data.module.booksResponse.Book;
import vdee.evalverde.vdee.data.module.chaptersResponse.ChapterPayload;
import vdee.evalverde.vdee.features.verses.VersesActivity;
import vdee.evalverde.vdee.util.PerController;
import vdee.evalverde.vdee.util.StorageUtils;
import vdee.evalverde.vdee.vdeeApi.VdeeApi;

public class BibliaController implements BibliaLayout.Listener {

    @Inject BibliaLayout bibliaLayout;
    @Inject Retrofit retrofit;

    private BibleResponseListener bibleResponseListener;
    private ChaptersResponseListener chaptersResponseListener;
    private Map<String, BookInfo> bookInfoMap;

    private Analytics analytics;
    private String bookTitle;


    private BibliaActivity bibliaActivity;

    BibliaController(BibliaActivity bibliaActivity) {
        this.bibliaActivity = bibliaActivity;
        analytics = Analytics.getAnalytics();
        bookInfoMap = StorageUtils.getListOfBooks();

        DaggerBibliaController_BibliaControllerComponent.builder()
                .bibliaControllerModule(new BibliaControllerModule(bibliaActivity, this, retrofit))
                .experimentComponent(((VDEEApp) bibliaActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        analytics.biblePageView();

    }



    @Override
    public void onBibleBookClicked(BookInfo bookInfo) {
        this.bookTitle = bookInfo.getBookName();
        Log.d("BibleFlow", "bibleClicked: " + bookTitle);
        StorageUtils.updateSelectedBook(bookInfo);

    }

    @Override
    public void onChapterButtonClicked(ChapterInfo chapterInfo) {
        VerseInfo verseInfo = chapterInfo.verseInfoHashMap().get("1");
        Log.d("BibleFlow: ", "verse in bibleC " + verseInfo.getVerse());
        Log.d("BibleFlow", "chapterClicked: " + chapterInfo.getChapterNumber());

        StorageUtils.updateChapterInfo(chapterInfo);
        Intent intent = VersesActivity.getVersesActivityIntent(bibliaActivity);
        bibliaActivity.startActivity(intent);
    }


    @PerController
    @Component(dependencies = ExperimentComponent.class, modules = BibliaControllerModule.class)
    interface BibliaControllerComponent {
        void inject(BibliaController bibliaController);
    }


    @dagger.Module
    static class BibliaControllerModule {

        private final BibliaActivity bibliaActivity;
        private final BibliaLayout.Listener listener;
        private final Retrofit retrofit;

        BibliaControllerModule(
                BibliaActivity bibliaActivity,
                BibliaLayout.Listener listener,
                Retrofit retrofit) {
            this.bibliaActivity = bibliaActivity;
            this.listener = listener;
            this.retrofit = retrofit;
        }

        @PerController
        @Provides
        BibliaLayout bibliaLayout() {
            return new BibliaLayout(bibliaActivity, listener, retrofit);
        }
    }
}
