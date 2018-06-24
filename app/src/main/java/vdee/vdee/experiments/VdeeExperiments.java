package vdee.vdee.experiments;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class VdeeExperiments {

    public static final String MUSIC_ON_CALL_BUG = "music_bug";
        public static final String VDEE_MULTIPLE_SUPPORT_RADIO = "vdee_multiple_radio_support";

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
        String instance = FirebaseInstanceId.getInstance().getToken();
    }

    public boolean isExperimentEnabled(String experimentName) {
        switch (experimentName) {
            case MUSIC_ON_CALL_BUG:
                return music_on_call_bug;
            case VDEE_MULTIPLE_SUPPORT_RADIO:
                return mMultipleRadioSupport;
            default:
                return false;
        }
    }
}
