package vdee.vdee.bibleScreen.chaptersView;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import vdee.vdee.VDEEApp;
import vdee.vdee.component.ExperimentComponent;
import vdee.vdee.util.PerController;

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
