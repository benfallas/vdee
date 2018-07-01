package vdee.vdee.experiments;

import android.content.SharedPreferences;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import vdee.vdee.util.StorageUtils;

public class VdeeExperiments {

    public static final String MUSIC_ON_CALL_BUG = "music_bug";
        public static final String VDEE_MULTIPLE_SUPPORT_RADIO = "vdee_multiple_radio_support";

        private static boolean experimentsFetchedSuccessfully = false;

    boolean music_on_call_bug;
    boolean mMultipleRadioSupport;

    private static VdeeExperiments vdeeExperiments;

    private VdeeExperiments() { }

    public static VdeeExperiments getInstance() {
        if (vdeeExperiments == null) {
            vdeeExperiments = new VdeeExperiments();
        }
        return vdeeExperiments;
    }

    public void initializeVdeeExperiments(FirebaseRemoteConfig firebaseRemoteConfig) {
        music_on_call_bug = false;
        mMultipleRadioSupport = false;
        vdeeExperiments = getInstance();

        music_on_call_bug = firebaseRemoteConfig.getBoolean(MUSIC_ON_CALL_BUG);
        mMultipleRadioSupport = firebaseRemoteConfig.getBoolean(VDEE_MULTIPLE_SUPPORT_RADIO);
        experimentsFetchedSuccessfully = true;
        StorageUtils.storeMultiplePlayersExperiment(mMultipleRadioSupport);
    }

    public boolean isExperimentEnabled(String experimentName) {
        switch (experimentName) {
            case VDEE_MULTIPLE_SUPPORT_RADIO:
                return StorageUtils.isMutipleRadioXpSupported();
            default:
                return false;
        }
//        if (experimentsFetchedSuccessfully) {
//            switch (experimentName) {
//                case MUSIC_ON_CALL_BUG:
//                    return music_on_call_bug;
//                case VDEE_MULTIPLE_SUPPORT_RADIO:
//                    return mMultipleRadioSupport;
//                default:
//                    return false;
//            }
//        } else if (mSharedPreferences != null){
//            return StorageUtils.getStoredValueForIsMutipleRadioSupported(mSharedPreferences);
//        } else {
//            switch (experimentName) {
//                case VDEE_MULTIPLE_SUPPORT_RADIO:
//                    return mMultipleRadioSupport;
//                default:
//                    return false;
//            }
//        }
    }
}
