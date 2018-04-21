package vdee.vdee.component;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import vdee.vdee.module.AppModule;
import vdee.vdee.module.ExpModule;

@Singleton
@Component(modules = {AppModule.class, ExpModule.class})

/**
* Creates remote config database object.
*/
public interface ExperimentComponent {
    FirebaseRemoteConfig remoteConfig();
    Retrofit retrofit();
}
