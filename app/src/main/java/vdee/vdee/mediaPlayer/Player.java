package vdee.vdee.mediaPlayer;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * Adapter to MediaPlayer.
 */
public class Player implements RadioPlayerListener {

    private PlayerListener mPlayerListener;
    private Activity mActivity;
    private RadioPlayer mRadioPlayer;
    private static Player mPlayer;

    private boolean mInitialized;

    private Player() {


        mInitialized = false;

        // Initialize ExoPlayer
        mRadioPlayer = SimplePlayer.initializeSimplePlayer(mActivity, this);
    }

    public Player initPlayer(@NonNull Activity activity, @NonNull PlayerListener playerListener) {
        if (mPlayer == null) {
            mPlayer = new Player();
            mInitialized = false;
        }

        mActivity = activity;
        mPlayerListener = playerListener;

        return mPlayer;
    }

    public boolean isInitialized() {
        return mInitialized;
    }

    public void start() {
        if (!mInitialized) {
            mRadioPlayer.initPlayer();
        }
    }

    public void stop() {
        mRadioPlayer.releasePlayer();
        mInitialized = false;
    }

    @Override
    public void onRadioPlayerReady() {
        mInitialized = true;
        mPlayerListener.onPlayerReady();
    }

    @Override
    public void onRadioPlayerError() {
        mInitialized = false;
        mPlayerListener.onPlayerError();
    }

    @Override
    public void onRadioPlayerBuffering() {
        mPlayerListener.onPlayerBuffering();
    }

    public interface PlayerListener {
        void onPlayerReady();
        void onPlayerError();
        void onPlayerBuffering();
    }
}
