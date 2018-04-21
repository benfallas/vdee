package vdee.vdee.component;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import vdee.vdee.module.AppModule;
import vdee.vdee.module.NetModule;

/**
 * Component for objects that will be used through out the app life cycle.
 */
@Singleton
public interface NetComponent {
    /**
     * Should return a {@link Retrofit}
     *
     * @return a {@link Retrofit}
     */
    Retrofit retrofit();
}