package vdee.evalverde.vdee.features.mainScreen.fragments.bibleFragments.chapter;

import rx.Subscriber;
import vdee.evalverde.vdee.data.module.chaptersResponse.ChaptersResponse;

/**
 * Created by ben on 6/23/18.
 */

public class ChaptersResponseListener extends Subscriber<ChaptersResponse> {

    private Listener mListener;

    public ChaptersResponseListener(Listener listener) {
        mListener = listener;
    }

    @Override
    public void onCompleted() {
        mListener.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        mListener.onError();
    }

    @Override
    public void onNext(ChaptersResponse chaptersResponse) {
        mListener.onNext(chaptersResponse);
    }

    interface Listener {

        public void onCompleted();

        public void onError();

        public void onNext(ChaptersResponse chaptersResponse);
    }
}
