package vdee.evalverde.vdee.features.bliblia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.data.module.booksResponse.Book;
import vdee.evalverde.vdee.data.module.chaptersResponse.Chapter;
import vdee.evalverde.vdee.data.module.chaptersResponse.ChapterPayload;
import vdee.evalverde.vdee.data.module.chaptersResponse.ChaptersResponse;
import vdee.evalverde.vdee.vdeeApi.VdeeApi;
import vdee.evalverde.vdee.views.layout.VdeeChaptersGrid;

/**
 * Holds views for List of {@link Book}.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder>
        implements ChaptersAdapter.ChapterButtonListener {

    private  ArrayList<Book> mBooks;
    private  ArrayList<ChapterPayload> chapterPayloads;
    private ChaptersAdapter chaptersAdapter;
    private GridView gridView;
    private ViewHolderListener mListener;
    private static int selectedBook = -1;

    public BooksAdapter(
            BibliaActivity bibliaActivity,
            ArrayList<Book> books,
            ArrayList<ChapterPayload> chapterPayloads,
            ViewHolderListener listener) {
        mBooks = books;
        this.chapterPayloads = chapterPayloads;
        mListener = listener;
        chaptersAdapter = new ChaptersAdapter(bibliaActivity, chapterPayloads, this);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Book book = mBooks.get(position);
        if (chapterPayloads != null
                && !chapterPayloads.isEmpty() && selectedBook == position) {
            holder.chaptersGrid.setVisibility(View.VISIBLE);
        } else {
            holder.chaptersGrid.setVisibility(View.GONE);
        }
        gridView = holder.chaptersGrid;
        gridView.setAdapter(chaptersAdapter);
        TextView button = holder.bookButton;
        button.setText(book.getName());

        holder.bookButton
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (selectedBook == position) {
                                    holder.chaptersGrid.setVisibility(View.GONE);
                                    chapterPayloads.clear();
                                } else {
                                    selectedBook = position;
                                    chapterPayloads.clear();
                                    notifyDataSetChanged();
                                    mListener.onBibleBookClicked(position);
                                }

                            }
                        }
                );
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    @Override
    public void onChapterButtonClicked(int position) {
        mListener.onChapterButtonClicked(chapterPayloads.get(position));
    }

    public void updateBooks(ArrayList<Book> books, ArrayList<ChapterPayload> chapterPayloads) {
        this.chapterPayloads.clear();
        this.chapterPayloads.addAll(chapterPayloads);
        mBooks.clear();
        mBooks.addAll(books);
        notifyDataSetChanged();
        chaptersAdapter.updateChapterPayloads(chapterPayloads);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vdee__book_title) TextView bookButton;
        @BindView(R.id.chapters_grid) VdeeChaptersGrid chaptersGrid;
        ViewHolderListener mListener;

        public ViewHolder(View itemView, ViewHolderListener listener) {
            super(itemView);
            mListener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.vdee__book_title)
        public void onBibleBookClicked() {
            mListener.onBibleBookClicked(getAdapterPosition());
        }
    }

    public interface ViewHolderListener {
        void onBibleBookClicked(int position);

        void onChapterButtonClicked(ChapterPayload chapterPayload);

    }
}
