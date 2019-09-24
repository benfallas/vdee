package vdee.evalverde.vdee.mediaPlayer;

/**
 * Radios station keeps track of each radio stations title and url
 */

public class RadioStation {

    private final String mRadiourl;

    public RadioStation(String radioUrl) {
        mRadiourl = radioUrl;
    }

    /**
     * Returns the current radio station url
     */
    public String getRadioUrl() {
        return mRadiourl;
    }

}
