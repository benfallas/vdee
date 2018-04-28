package vdee.vdee.bibleScreen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vdee.vdee.R;
import vdee.vdee.data.module.booksResponse.Book;

/**
 * Holds views for List of {@link Book}.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private ArrayList<Book> mBooks;
    private Context mContenxt;
    private ViewHolderListener mListener;

    public BooksAdapter(Context context, ArrayList<Book> books, ViewHolderListener listener) {
        mContenxt = context;
        mBooks = books;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View bookView = inflater.inflate(R.layout.book, parent, false);

        ViewHolder viewHolder = new ViewHolder(bookView, mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = mBooks.get(position);
        Button button = holder.bookButton;
        button.setText(book.getName());
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.book_button) Button bookButton;
        ViewHolderListener mListener;

        public ViewHolder(View itemView, ViewHolderListener listener) {
            super(itemView);
            mListener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.book_button)
        public void onBibleBookClicked() {
            mListener.onBibleBookClicked(getAdapterPosition());
        }
    }

    public interface ViewHolderListener {
        void onBibleBookClicked(int position);
    }
}
