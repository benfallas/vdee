package vdee.vdee.mainScreen;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskGranted;

import io.fabric.sdk.android.Fabric;
import vdee.vdee.parent.ParentActivity;
import vdee.vdee.permissions.PermissionsManager;
import vdee.vdee.util.FragmentManagerUtils;

/**
 * Main Activity controls the Android lifecycle of main screen.
 */
public class MainActivity extends ParentActivity {

    private MainController mMainController;
    private PermissionsManager mPermissionsManager;
    private FragmentManagerUtils fragmentManagerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        fragmentManagerUtils = FragmentManagerUtils.getFragmentManagerUtils();
        mMainController = new MainController(this);
        mPermissionsManager = PermissionsManager.getPermissionsManager();
        Ask.on(this)
                .forPermissions(Manifest.permission.READ_PHONE_STATE)
                .withRationales("Se Necesita permiso para el estado del celular.", "NOTA: Puede seguir sin permiso. Solo no parara la radio si entra una llamada.")
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

    @Override
    protected void onPause() {
        super.onPause();
        mMainController.onPaused();
    }
}
