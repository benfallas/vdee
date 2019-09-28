package vdee.evalverde.vdee.features.mainScreen.fragments.bibleFragments;

import rx.Subscriber;
import vdee.evalverde.vdee.data.module.booksResponse.BooksResponse;

public class BibleResponseListener extends Subscriber<BooksResponse> {

    private Listener mListener;

    public BibleResponseListener(Listener listener) {
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
    public void onNext(BooksResponse booksResponse) {
        mListener.onNext(booksResponse);
    }

    interface Listener {

        void onCompleted();

        void onError(Throwable e);

        void onNext(BooksResponse booksResponse);
    }
}
