package vdee.vdee.bibleScreen;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import vdee.vdee.VDEEApp;
import vdee.vdee.component.ExperimentComponent;
import vdee.vdee.util.PerController;

/**
 *Bible Controller holds the logic for most activity happening on Main Screen.
 */
public class BibleController {

    @Inject BibleLayout mBibleLayout;

    public BibleController(BibleActivity bibleActivity) {

        DaggerBibleController_BibleControllerComponent.builder()
                .bibleControllerModule(new BibleControllerModule(bibleActivity))
                .experimentComponent(((VDEEApp) bibleActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);
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
