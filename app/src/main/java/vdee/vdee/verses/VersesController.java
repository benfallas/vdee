package vdee.vdee.verses;

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

import static vdee.vdee.bibleScreen.chaptersView.ChaptersLayout.CHAPTER_ID;

public class VersesController {

    @Inject VersesLayout mVersesLayout;
    @Inject Retrofit mRetrofit;

    private VersesActivity mVersesActivity;

    public VersesController(VersesActivity versesActivity) {
        mVersesActivity = versesActivity;
        DaggerVersesController_VersesControllerComponent.builder()
                .versesControllerModule(new VersesControllerModule(mVersesActivity))
                .experimentComponent(((VDEEApp) mVersesActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        String chapterID = mVersesActivity.getIntent().getStringExtra(CHAPTER_ID);
        Log.d("CHAPTER ID", chapterID);
        mRetrofit.create(VdeeApi.class).getVerses(chapterID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mVersesLayout);
    }

    @PerController
    @Component(dependencies = ExperimentComponent.class, modules = VersesControllerModule.class)
    interface VersesControllerComponent {
        void inject(VersesController versesController);
    }

    @Module
    static class VersesControllerModule {

        private final VersesActivity mVersesActivity;

        VersesControllerModule(@NonNull VersesActivity versesActivity) {
            mVersesActivity = versesActivity;
        }

        @Provides
        @PerController
        VersesLayout providesLayout() {
            return new VersesLayout(mVersesActivity);
        }
    }
}
