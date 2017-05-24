package vdee.vdee.mediaPlayer;

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
}
