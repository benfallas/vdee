package vdee.vdee.bibleScreen.chaptersView;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vdee.vdee.VDEEApp;
import vdee.vdee.component.ExperimentComponent;
import vdee.vdee.util.PerController;
import vdee.vdee.vdeeApi.VdeeApi;

import static vdee.vdee.bibleScreen.BibleLayout.BOOK_ID;

/**
 * Controller to control logic for getting numbers based on bible book.
 */
public class ChaptersController {

    @Inject Retrofit mRetrofit;
    @Inject ChaptersLayout mChaptersLayout;

    private ChaptersActivity mChaptersActivity;

    public ChaptersController(ChaptersActivity chaptersActivity) {
        mChaptersActivity = chaptersActivity;
        DaggerChaptersController_ChapterControllerModule.builder()
                .chaptersControllerModule(new ChaptersControllerModule(mChaptersActivity))
                .experimentComponent(((VDEEApp)mChaptersActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        String bookId = mChaptersActivity.getIntent().getStringExtra(BOOK_ID);

        mRetrofit.create(VdeeApi.class).getChaptersByBookId(bookId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mChaptersLayout);
    }

    @PerController
    @Component(dependencies = ExperimentComponent.class, modules = ChaptersControllerModule.class)
    interface ChapterControllerModule {
        void inject(ChaptersController chaptersController);
    }

    @Module
    static class ChaptersControllerModule {

        private final ChaptersActivity mChaptersActivity;

        ChaptersControllerModule(@NonNull ChaptersActivity chaptersActivity) {
            mChaptersActivity = chaptersActivity;
        }

        @Provides
        @PerController
        ChaptersLayout providesChaptersLayout() {
            return new ChaptersLayout(mChaptersActivity);
        }
    }
}
