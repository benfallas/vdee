package vdee.evalverde.vdee.features.bliblia;

import android.support.annotation.IdRes;

import javax.inject.Inject;

import dagger.Component;
import dagger.Provides;
import retrofit2.Retrofit;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.util.PerController;

public class BibliaController implements BibliaLayout.Listener {

    @Inject BibliaLayout bibliaLayout;
    @Inject Retrofit retrofit;

    private BibliaActivity bibliaActivity;

    BibliaController(BibliaActivity bibliaActivity) {
        this.bibliaActivity = bibliaActivity;
        DaggerBibliaController_BibliaControllerComponent.builder()
                .bibliaControllerModule(new BibliaControllerModule(bibliaActivity, this))
                .experimentComponent(((VDEEApp) bibliaActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

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

        BibliaControllerModule(BibliaActivity bibliaActivity, BibliaLayout.Listener listener) {
            this.bibliaActivity = bibliaActivity;
            this.listener = listener;
        }

        @PerController
        @Provides
        BibliaLayout bibliaLayout() {
            return new BibliaLayout(bibliaActivity, listener);
        }
    }
}
