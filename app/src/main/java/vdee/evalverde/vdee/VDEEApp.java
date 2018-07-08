package vdee.evalverde.vdee;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import vdee.evalverde.vdee.component.DaggerExperimentComponent;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.module.AppModule;
import vdee.evalverde.vdee.module.ExpModule;

/**
 * VDEEApp creates a new experiment component.
 */
public class VDEEApp extends Application {

    private ExperimentComponent mExperimentComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        mExperimentComponent = DaggerExperimentComponent.builder()
                .appModule(new AppModule(this))
                .expModule(new ExpModule())
                .build();
    }

    /**
     * Returns build experiment component.
     */
    public ExperimentComponent getExpComponent() {
        return mExperimentComponent;
    }
}
