package vdee.vdee.mainScreen.fragments.bibleFragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import vdee.vdee.util.FragmentManagerUtils;
import vdee.vdee.util.ParentFragment;
import vdee.vdee.util.PerFragment;
import vdee.vdee.util.StorageUtils;
import vdee.vdee.vdeeApi.VdeeApi;

import static vdee.vdee.util.FragmentManagerUtils.BIBLE_FRAGMENT_TAG;

public class BibleFragment extends ParentFragment implements BooksAdapter.ViewHolderListener, BibleResponseListener.Listener {

    public static String BOOK_TITLE = "BOOK_TITLE";
    public static String BOOK_ID = "BOOK_ID";

    private RecyclerView mListOfBooks;
    private BooksAdapter mAdapter;
    private BibleResponseListener mListener;
    private EditText mSearchBar;

    private ArrayList<Book> originalBooks;
    private ArrayList<Book> filteredBooks;

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
        filteredBooks = new ArrayList<>();
        mListOfBooks = getActivity().findViewById(R.id.recycler_view_books);
        mListener = new BibleResponseListener(this);

        DaggerBibleFragment_BibleComponent.builder()
                .experimentComponent((((VDEEApp) getActivity().getApplicationContext())).getExpComponent())
                .build()
                .inject(this);
        mSearchBar = getActivity().findViewById(R.id.bible_search);

        if (StorageUtils.getStoredBooksResponse().getResponse().getBooks() != null) {
            showBooks(StorageUtils.getStoredBooksResponse());
        } else {
            retrofit.create(VdeeApi.class).getBible()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mListener);
            showDialog();
        }

        mSearchBar.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        mSearchBar.setCursorVisible(false);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mSearchBar.setCursorVisible(false);
                        s = s.toString().toLowerCase();

                        for (int i = 0; i < originalBooks.size(); i++) {
                            final String bookSearched = originalBooks.get(i).getName().toLowerCase();
                            if (bookSearched.contains(s)) {
                                filteredBooks.add(originalBooks.get(i));
                            }
                        }

                        mListOfBooks.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                        mAdapter = new BooksAdapter(
                                getActivity().getApplicationContext(),
                                filteredBooks,
                                BibleFragment.this);

                        mListOfBooks.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        mSearchBar.setCursorVisible(false);
                    }
                }
        );
    }

    private void displayBooks(ArrayList<Book> books) {
        mAdapter = new BooksAdapter(getActivity(), books, this);
        mListOfBooks.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mListOfBooks.setAdapter(mAdapter);
    }

    @Override
    public void onBibleBookClicked(int position) {
        Fragment fragment = new ChaptersFragment();
        String bookTitle = filteredBooks.get(position).getName();
        String bookId = filteredBooks.get(position).getId();
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
            filteredBooks.addAll(originalBooks);
            displayBooks(originalBooks);
        }
    }

    @PerFragment
    @Component(dependencies = ExperimentComponent.class)
    interface BibleComponent {
        void inject(BibleFragment bibleFragment);
    }
}
