package vdee.evalverde.vdee.features.verses;

import rx.Subscriber;
import vdee.evalverde.vdee.data.module.versesResponse.VersesResponse;

/**
 * Created by ben on 6/23/18.
 */

public class VersesResponseListener extends Subscriber<VersesResponse> {

    Listener mListener;

    public VersesResponseListener(Listener listener) {
        mListener = listener;
    }

    @Override
    public void onCompleted() {
        mListener.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        mListener.onError(e);
    }

    @Override
    public void onNext(VersesResponse versesResponse) {
        mListener.onNext(versesResponse);
    }

    public interface Listener {
        void onCompleted();

        void onError(Throwable e);

        void onNext(VersesResponse versesResponse);
    }
}
