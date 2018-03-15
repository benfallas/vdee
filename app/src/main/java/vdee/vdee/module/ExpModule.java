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
    private long cacheExpiration;
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

         cacheExpiration = 12 * 3600; // 12 hours in seconds.
        // If in developer mode cacheExpiration is set to 0 so each fetch will retrieve values from
        // the server.
        if (mFirebaseRemoteConfig.getInfo( ).getConfigSettings( ).isDeveloperModeEnabled( )) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(new OnCompleteListener<Void>() {
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
