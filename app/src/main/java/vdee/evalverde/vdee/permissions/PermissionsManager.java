package vdee.evalverde.vdee.permissions;

/**
 * Manager permissions.
 */
public class PermissionsManager {

    public static boolean PHONE_STATE_PERMISSION_GRANTED;

    private static PermissionsManager mPermissionsManagerInstance;

    private PermissionsManager() {
        PHONE_STATE_PERMISSION_GRANTED = false;
    }

    public static PermissionsManager getPermissionsManager() {
        if (mPermissionsManagerInstance == null) {
            mPermissionsManagerInstance = new PermissionsManager();
        }

        return mPermissionsManagerInstance;
    }
}
