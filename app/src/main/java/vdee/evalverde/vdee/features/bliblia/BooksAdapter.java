package vdee.evalverde.vdee.features.bliblia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.data.models.BookInfo;
import vdee.evalverde.vdee.data.models.ChapterInfo;
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

    private  Map<String, BookInfo> mBooks;
    private Map<String, ChapterInfo> chapters;
    private ChaptersAdapter chaptersAdapter;
    private GridView gridView;
    private ViewHolderListener mListener;
    private static int selectedBook = -1;

    public BooksAdapter(
            BibliaActivity bibliaActivity,
            Map<String, BookInfo> books,
            ViewHolderListener listener) {
        mBooks = books;
        mListener = listener;
        chapters = new HashMap<>();
        selectedBook = -1;

        chaptersAdapter = new ChaptersAdapter(bibliaActivity, chapters, this);
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
        if (position == 0) {
            holder.bookButton.setVisibility(View.GONE);
        } else {


            final BookInfo bookInfo = mBooks.get(String.valueOf(position));
            chapters.clear();
            if (bookInfo != null) {
                chapters.putAll(bookInfo.getChapterInfoHashMap());
            }

            if (selectedBook == position) {
                holder.chaptersGrid.setVisibility(View.VISIBLE);
            } else {
                holder.chaptersGrid.setVisibility(View.GONE);
            }

            gridView = holder.chaptersGrid;
            gridView.setAdapter(chaptersAdapter);
            TextView button = holder.bookButton;

            button.setText(bookInfo != null ? bookInfo.getBookName() : "");


            holder.bookButton
                    .setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (selectedBook == position) {
                                        holder.chaptersGrid.setVisibility(View.GONE);
                                        selectedBook = -1;
                                    } else {
                                        selectedBook = position;
                                        holder.chaptersGrid.setVisibility(View.VISIBLE);
                                        chaptersAdapter.updateChapters(chapters);
                                        notifyDataSetChanged();
                                        mListener.onBibleBookClicked(position);
                                    }

                                }
                            }
                    );
        }
    }

    @Override
    public int getItemCount() {
        return mBooks.size() + 1;
    }

    @Override
    public void onChapterButtonClicked(ChapterInfo chapterInfo) {
        mListener.onChapterButtonClicked(chapterInfo);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vdee__book_title) Button bookButton;
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

        void onChapterButtonClicked(ChapterInfo chapterInfo);

    }
}
