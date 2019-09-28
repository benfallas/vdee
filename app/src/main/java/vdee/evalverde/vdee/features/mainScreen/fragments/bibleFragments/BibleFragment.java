package vdee.evalverde.vdee.features.mainScreen.fragments.bibleFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.Component;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.data.module.booksResponse.Book;
import vdee.evalverde.vdee.data.module.booksResponse.BooksResponse;
import vdee.evalverde.vdee.features.mainScreen.fragments.bibleFragments.chapter.ChaptersFragment;
import vdee.evalverde.vdee.util.FragmentManagerUtils;
import vdee.evalverde.vdee.util.ParentFragment;
import vdee.evalverde.vdee.util.PerFragment;
import vdee.evalverde.vdee.util.StorageUtils;
import vdee.evalverde.vdee.vdeeApi.VdeeApi;

public class BibleFragment extends ParentFragment implements BooksAdapter.ViewHolderListener, BibleResponseListener.Listener {

    public static String BOOK_TITLE = "BOOK_TITLE";
    public static String BOOK_ID = "BOOK_ID";

    private Analytics mAnalytics;
    private RecyclerView mListOfBooks;
    private BooksAdapter mAdapter;
    private BibleResponseListener mListener;

    private ArrayList<Book> originalBooks;

    @Inject Retrofit retrofit;

    public BibleFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_bible, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onAttach();
    }

    private void onAttach() {
        mAnalytics = Analytics.getAnalytics();
        mListOfBooks = getActivity().findViewById(R.id.recycler_view_books);
        mListener = new BibleResponseListener(this);

        mAnalytics.biblePageView();
        DaggerBibleFragment_BibleComponent.builder()
                .experimentComponent((((VDEEApp) getActivity().getApplicationContext())).getExpComponent())
                .build()
                .inject(this);

        if (StorageUtils.getStoredBooksResponse() != null
            && StorageUtils.getStoredBooksResponse().getResponse().getBooks() != null) {
            showBooks(StorageUtils.getStoredBooksResponse());
        } else {
            retrofit.create(VdeeApi.class).getBible()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mListener);
            showDialog();
        }
    }

    private void displayBooks(ArrayList<Book> books) {
        mAdapter = new BooksAdapter(getActivity(), books, this);
        mListOfBooks.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mListOfBooks.setAdapter(mAdapter);
    }

    @Override
    public void onBibleBookClicked(int position) {
        Fragment fragment = new ChaptersFragment();
        String bookTitle = originalBooks.get(position).getName();
        String bookId = originalBooks.get(position).getId();
        Bundle bundle = new Bundle();
        bundle.putString(BOOK_TITLE, bookTitle);
        bundle.putString(BOOK_ID, bookId);
        fragment.setArguments(bundle);
        FragmentManagerUtils.pushFragment(fragment, FragmentManagerUtils.CHAPTERS_FRAGMENT_TAG);
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError(Throwable e) {
        hideDialog();
        showNetworkError();
        BooksResponse booksResponse = StorageUtils.getStoredBooksResponse();
        showBooks(booksResponse);
    }

    @Override
    public void onNext(BooksResponse booksResponse) {
        hideDialog();
        showBooks(booksResponse);
        StorageUtils.storeBooksResponse(booksResponse);
    }

    private void showBooks(BooksResponse booksResponse) {
        if (booksResponse.getResponse().getBooks() != null) {
            originalBooks = booksResponse.getResponse().getBooks();
            displayBooks(originalBooks);
        }
    }

    @PerFragment
    @Component(dependencies = ExperimentComponent.class)
    interface BibleComponent {
        void inject(BibleFragment bibleFragment);
    }
}