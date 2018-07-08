package vdee.evalverde.vdee.component;

import javax.inject.Singleton;

import retrofit2.Retrofit;

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