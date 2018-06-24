package vdee.vdee.mainScreen.fragments.bibleFragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import vdee.vdee.R;
import vdee.vdee.VDEEApp;
import vdee.vdee.component.ExperimentComponent;
import vdee.vdee.data.module.booksResponse.Book;
import vdee.vdee.data.module.booksResponse.BooksResponse;
import vdee.vdee.mainScreen.fragments.bibleFragments.chapter.ChaptersFragment;
import vdee.vdee.util.ParentFragment;
import vdee.vdee.util.PerFragment;
import vdee.vdee.vdeeApi.VdeeApi;

public class BibleFragment extends ParentFragment implements BooksAdapter.ViewHolderListener, BibleResponseListener.Listener {

    public static String BOOK_TITLE = "BOOK_TITLE";
    public static String BOOK_ID = "BOOK_ID";

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
        mListOfBooks = getActivity().findViewById(R.id.recycler_view_books);
        mListener = new BibleResponseListener(this);

        DaggerBibleFragment_BibleComponent.builder()
                .experimentComponent((((VDEEApp) getActivity().getApplicationContext())).getExpComponent())
                .build()
                .inject(this);

        retrofit.create(VdeeApi.class).getBible()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mListener);
        showDialog();
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
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError(Throwable e) {
        hideDialog();
        showNetworkError();
    }

    @Override
    public void onNext(BooksResponse booksResponse) {
        hideDialog();
        originalBooks = booksResponse.getResponse().getBooks();
        displayBooks(originalBooks);
    }

    @PerFragment
    @Component(dependencies = ExperimentComponent.class)
    interface BibleComponent {
        void inject(BibleFragment bibleFragment);
    }
}
