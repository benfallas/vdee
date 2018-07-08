package vdee.evalverde.vdee.component;

import android.content.SharedPreferences;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

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
    FirebaseRemoteConfig remoteConfig();
    SharedPreferences sharedPreferences();
    Retrofit retrofit();
}
