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
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                if (isIncomingCall) {
                    Log.d(CALL_RECEIVER_LOG, INCOMING_CALL_ENDED);
                    mCallReceiverListener.onCallEnded();
                    isIncomingCall = false;
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(CALL_RECEIVER_LOG, INCOMING_ANSWERED);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                isIncomingCall = true;
                Log.d(CALL_RECEIVER_LOG, INCOMING_RECEIVED);
                mCallReceiverListener.onIncomingCall();
                break;
        }
    }

    /**
     * CallReceiverListener notifies MainController class.
     */
    public interface CallReceiverListener {
        void onIncomingCall();
        void onCallEnded();
    }
}
