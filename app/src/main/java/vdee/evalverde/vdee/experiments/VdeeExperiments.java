package vdee.evalverde.vdee.experiments;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import vdee.evalverde.vdee.util.StorageUtils;

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
        String id = FirebaseInstanceId.getInstance().getId();
        Log.d("FiirebaseInstanceId", id);
        Log.d("FiirebaseInstanceId", FirebaseInstanceId.getInstance().getToken());
    }

    public boolean isExperimentEnabled(String experimentName) {
        switch (experimentName) {
            case VDEE_MULTIPLE_SUPPORT_RADIO:
                return StorageUtils.isMutipleRadioXpSupported();
            default:
                return false;
        }
    }
}
