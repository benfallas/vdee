package vdee.vdee.phoneCallReceiver;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by benitosanchez on 5/26/17.
 */

public class CallReceiver extends PhoneStateListener {

    private final String CALL_RECEIVER_LOG = "CallReceiver";
    private final String INCOMING_RECEIVED = "Incoming Received";
    private final String INCOMING_ANSWERED = "Incoming Answered";
    private final String INCOMING_CALL_ENDED = "Incoming Call Ended";

    private CallReceiverListener mCallReceiverListener;

    public CallReceiver(CallReceiverListener callReceiverListener) {
        mCallReceiverListener = callReceiverListener;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(CALL_RECEIVER_LOG, INCOMING_CALL_ENDED);
                mCallReceiverListener.onCallEnded();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(CALL_RECEIVER_LOG, INCOMING_ANSWERED);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(CALL_RECEIVER_LOG, INCOMING_RECEIVED);
                mCallReceiverListener.onIncomingCall();
                break;
        }
    }

    public interface CallReceiverListener {
        void onIncomingCall();
        void onCallEnded();
    }
}
