package vdee.vdee.module;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vdee.vdee.BuildConfig;

import static android.content.ContentValues.TAG;

@Module
public class ExpModule {
    private final int CACHE_EXPIRATION = 0;
    FirebaseRemoteConfig mFirebaseRemoteConfig;

    /**
     * ExpModule fetches current remote config values.
     */
    public ExpModule() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings config = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(config);

        mFirebaseRemoteConfig.fetch(CACHE_EXPIRATION).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Fetch Succeeded");
                    mFirebaseRemoteConfig.activateFetched();
                } else {
                    Log.d(TAG, "Fetch Failed");
                }
            }
        });
    }

    @Provides
    @Singleton
    FirebaseRemoteConfig providesRemoteConfig() {return mFirebaseRemoteConfig;}

}
