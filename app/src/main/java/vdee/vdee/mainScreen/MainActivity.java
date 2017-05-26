package vdee.vdee.mainScreen;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskGranted;

import io.fabric.sdk.android.Fabric;
import vdee.vdee.permissions.PermissionsManager;

/**
 * Main Activity controls the Android lifecycle of main screen.
 */
public class MainActivity extends AppCompatActivity {

    private MainController mMainController;
    private PermissionsManager mPermissionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        mPermissionsManager = PermissionsManager.getPermissionsManager();
        Ask.on(this)
                .forPermissions(Manifest.permission.READ_PHONE_STATE)
                .withRationales("Need to grant access to Phone State in order to stop radio on incoming call")
                .go();
    }

    /**
     * When Read Phone State is granted.
     */
    @AskGranted(Manifest.permission.READ_PHONE_STATE)
    public void onReadPhoneStateGranted() {
        mPermissionsManager.PHONE_STATE_PERMISSION_GRANTED = true;
        mMainController = new MainController(this);
    }

    /**
     * When Read Phone State is denied.
     */
    @AskGranted(Manifest.permission.READ_PHONE_STATE)
    public void onReadPhoneStateDenied() {
        mPermissionsManager.PHONE_STATE_PERMISSION_GRANTED = false;
        mMainController = new MainController(this);
    }
}
