package vdee.vdee.verses;

import android.support.annotation.NonNull;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import vdee.vdee.VDEEApp;
import vdee.vdee.component.ExperimentComponent;
import vdee.vdee.util.PerController;

public class VersesController {

    private VersesActivity mVersesActivity;

    public VersesController(VersesActivity versesActivity) {
        mVersesActivity = versesActivity;
        DaggerVersesController_VersesControllerComponent.builder()
                .versesControllerModule(new VersesControllerModule(mVersesActivity))
                .experimentComponent(((VDEEApp) mVersesActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);
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
