package vdee.vdee;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import vdee.vdee.component.DaggerExperimentComponent;
import vdee.vdee.component.ExperimentComponent;
import vdee.vdee.module.AppModule;
import vdee.vdee.module.ExpModule;

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
