package vdee.vdee.util;

import android.content.SharedPreferences;

/**
 * Stores values for fetched experiments.
 *
 * This is needed since relying entirely on firebase might cause users to not see an
 * active experiment do to network issue.
 */
public class StorageUtils {

    public static final String MULTIPLE_RADIO_SUPPORT = "MUTIPLE_RADIO_SUPPORT";

    private static SharedPreferences mSharedPreferences;
    private static StorageUtils mStorageUtils;

    private StorageUtils() { }

    public static void initSharedUtils(SharedPreferences sharedPreferences) {
        if (mStorageUtils == null) {
            mStorageUtils = new StorageUtils();
            mSharedPreferences = sharedPreferences;
        }
    }

    /**
     * @param isMutipleRadioSupported fetched value from firebase.
     */
    public static void storeMultiplePlayersExperiment(boolean isMutipleRadioSupported) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(MULTIPLE_RADIO_SUPPORT, isMutipleRadioSupported);
        editor.commit();
    }

    /**
     * Whether multiple radio is supported or not.
     *
     * @return {@true} if multiple radio functionality is supported. {@false} otherwise.
     */
    public static boolean isMutipleRadioXpSupported() {
        return mSharedPreferences.getBoolean(MULTIPLE_RADIO_SUPPORT, false);
    }
}
