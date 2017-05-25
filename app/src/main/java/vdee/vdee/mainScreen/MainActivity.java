package vdee.vdee.mainScreen;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Main Activity controls the Android lifecycle of main screen.
 */
public class MainActivity extends AppCompatActivity {

    private MainController mMainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        mMainController = new MainController(this);
    }
}
