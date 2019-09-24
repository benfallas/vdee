package vdee.evalverde.vdee.splashScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.Component;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.mainScreen.MainActivity;
import vdee.evalverde.vdee.parent.ParentActivity;
import vdee.evalverde.vdee.util.PerFragment;
import vdee.evalverde.vdee.util.StorageUtils;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends ParentActivity {

    private static final String TAG = "SpashActivity";

    @Inject SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerSplashActivity_SplashFragment.builder()
                .experimentComponent(((VDEEApp) getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        setContentView(R.layout.activity_splash);

        /** Initializes storage manager. **/
        StorageUtils.initSharedUtils(mSharedPreferences);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        launchMainScreen();
    }

    private void launchMainScreen() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @PerFragment
    @Component(dependencies = ExperimentComponent.class)
    interface SplashFragment {
        void inject(SplashActivity splashActivity);
    }
}
