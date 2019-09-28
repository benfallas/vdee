package vdee.evalverde.vdee.features.mainScreen;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskGranted;

import io.fabric.sdk.android.Fabric;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.baseActivity.BaseActivity;
import vdee.evalverde.vdee.permissions.PermissionsManager;
import vdee.evalverde.vdee.util.FragmentManagerUtils;

/**
 * Main Activity controls the Android lifecycle of main screen.
 */
public class MainActivity extends BaseActivity {

    private MainController mMainController;
    private PermissionsManager mPermissionsManager;
    private FragmentManagerUtils fragmentManagerUtils;

    public void onAttached() {
        Log.d("BASEACTIVITTEST", "onAttachedMAINACTIVTY");

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
    public void onPause() {
        super.onPause();
        mMainController.onPaused();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.homeItem;
    }
}
