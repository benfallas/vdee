package vdee.evalverde.vdee.mediaPlayer;

/**
 * Every radio Player should implement Radio Player Listener.
 * */
public interface RadioPlayerListener {
    /**
     * Radio Player should notify when ready to play
     */
    void onRadioPlayerReady();

    /**
     * Radio Player should notify when there is an error.
     */
    void onRadioPlayerError();

    /**
     * Radio Player should notify when buffering.
     */
    void onRadioPlayerBuffering();

    /**
     * Radio Player should notify when released.
     */
    void onReleaseRadio();
}
