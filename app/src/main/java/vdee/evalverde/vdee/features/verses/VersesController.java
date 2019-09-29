package vdee.evalverde.vdee.features.verses;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.util.PerController;

public class VersesController implements VersesLayout.Listener {

    @Inject VersesLayout versesLayout;

    private VersesActivity versesActivity;

    VersesController(VersesActivity versesActivity) {
        this.versesActivity = versesActivity;

        DaggerVersesController_VersesControllerComponent.builder()
                .versesControllerModule(new VersesControllerModule(versesActivity, this))
                .experimentComponent(((VDEEApp) versesActivity.getApplicationContext()).getExpComponent())
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

        private final VersesActivity versesActivity;
        private final VersesLayout.Listener listener;

        VersesControllerModule(
                VersesActivity versesActivity, VersesLayout.Listener listener) {
            this.versesActivity = versesActivity;
            this.listener = listener;
        }

        @Provides
        @PerController
        VersesLayout versesLayout() {
            return new VersesLayout(versesActivity, listener);
        }
    }
}
