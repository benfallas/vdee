package vdee.evalverde.vdee.mediaPlayer;

/**
 * Radios station keeps track of each radio stations title and url
 */

public class RadioStation {

    private final String mRadiourl;
    private final String mRadioTitle;

    public RadioStation(String radioUrl, String radioTitle) {
        mRadiourl = radioUrl;
        mRadioTitle = radioTitle;
    }

    /**
     * Returns the current radio station url
     */
    public String getRadioUrl() {
        return mRadiourl;
    }

    /**
     * Returns the current radio station title
     */
    public String getRadioTitle() {
        return mRadioTitle;
    }
}
