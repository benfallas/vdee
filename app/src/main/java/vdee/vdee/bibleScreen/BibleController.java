package vdee.vdee.bibleScreen;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vdee.vdee.VDEEApp;
import vdee.vdee.component.ExperimentComponent;
import vdee.vdee.util.PerController;
import vdee.vdee.vdeeApi.VdeeApi;

/**
 *Bible Controller holds the logic for most activity happening on Main Screen.
 */
public class BibleController {

    @Inject BibleLayout mBibleLayout;
    @Inject
    Retrofit mRetrofit;

    public BibleController(BibleActivity bibleActivity) {

        DaggerBibleController_BibleControllerComponent.builder()
                .bibleControllerModule(new BibleControllerModule(bibleActivity))
                .experimentComponent(((VDEEApp) bibleActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        mRetrofit.create(VdeeApi.class).getBible()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mBibleLayout);
    }

    @PerController
    @Component(dependencies = ExperimentComponent.class, modules = BibleControllerModule.class)
    interface BibleControllerComponent {
        void inject(BibleController bibleController);
    }

    @Module
    static class BibleControllerModule {
        private final BibleActivity mBibleActivity;

        BibleControllerModule(@NonNull BibleActivity bibleActivity) {
            mBibleActivity = bibleActivity;
        }

        @Provides
        @PerController
        BibleLayout providesBibleLayout() {
            return new BibleLayout(mBibleActivity);
        }
    }
}
