package vdee.evalverde.vdee.component;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import vdee.evalverde.vdee.module.AppModule;
import vdee.evalverde.vdee.module.ExpModule;

@Singleton
@Component(modules = {AppModule.class, ExpModule.class})

/**
* Creates remote config database object.
*/
public interface ExperimentComponent {
    SharedPreferences sharedPreferences();
    Retrofit retrofit();
}
