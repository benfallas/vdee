package vdee.vdee;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import vdee.vdee.component.DaggerExperimentComponent;
import vdee.vdee.component.DaggerNetComponent;
import vdee.vdee.component.ExperimentComponent;
import vdee.vdee.component.NetComponent;
import vdee.vdee.module.AppModule;
import vdee.vdee.module.ExpModule;
import vdee.vdee.module.NetModule;

/**
 * VDEEApp creates a new experiment component.
 */
public class VDEEApp extends Application {

    private ExperimentComponent mExperimentComponent;
    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        mExperimentComponent = DaggerExperimentComponent.builder()
                .expModule(new ExpModule())
                .build();

        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule("https://bibles.org"))
                .appModule(new AppModule(this))
                .build();

    }

    /**
     * Returns build experiment component.
     */
    public ExperimentComponent getExpComponent() {
        return mExperimentComponent;
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

}
