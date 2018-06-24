package vdee.vdee.splashScreen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import javax.inject.Inject;

import dagger.Component;
import vdee.vdee.R;
import vdee.vdee.VDEEApp;
import vdee.vdee.component.ExperimentComponent;
import vdee.vdee.experiments.VdeeExperiments;
import vdee.vdee.mainScreen.MainActivity;
import vdee.vdee.util.PerFragment;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SpashActivity";

    @Inject FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerSplashActivity_SplashFragment.builder()
                .experimentComponent(((VDEEApp) getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        long cacheExpiration = 12 * 3600; // 12 hours in seconds.
        // If in developer mode cacheExpiration is set to 0 so each fetch will retrieve values from
        // the server immediately.
        if (mFirebaseRemoteConfig.getInfo( ).getConfigSettings( ).isDeveloperModeEnabled( )) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
                            VdeeExperiments.getInstance().initializeVdeeExperiments(mFirebaseRemoteConfig);
                        } else {
                            Log.d(TAG, "Fetch failed");
                        }
                        launchMainScreen();
                    }
                }
        );
    }

    private void launchMainScreen() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @PerFragment
    @Component(dependencies = ExperimentComponent.class)
    interface SplashFragment {
        void inject(SplashActivity splashActivity);
    }
}
