package vdee.evalverde.vdee.phoneCallReceiver;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Manager call receiver.
 *
 * On incoming call stop radio.
 *
 * on call ended play radio.
 */
public class CallReceiver extends PhoneStateListener {

    private final String CALL_RECEIVER_LOG = "CallReceiver";
    private final String INCOMING_RECEIVED = "Incoming Received";
    private final String INCOMING_ANSWERED = "Incoming Answered";
    private final String INCOMING_CALL_ENDED = "Incoming Call Ended";

    private CallReceiverListener mCallReceiverListener;

    private boolean isIncomingCall;

    public CallReceiver(CallReceiverListener callReceiverListener) {
        mCallReceiverListener = callReceiverListener;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        if (state == TelephonyManager.CALL_STATE_RINGING) {
            mCallReceiverListener.onIncomingCall();
        }
    }

    /**
     * CallReceiverListener notifies MainController class.
     */
    public interface CallReceiverListener {
        void onIncomingCall();
    }
}
