package vdee.vdee.mainScreen.fragments.bibleFragments.verses;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import android.content.Context;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vdee.vdee.R;
import vdee.vdee.data.module.versesResponse.VersesPayload;

public class VersesAdapter extends RecyclerView.Adapter<VersesAdapter.VersesViewHolder> {

    private ArrayList<VersesPayload> mVerses;
    private Context mContext;

    VersesAdapter(Context context, ArrayList<VersesPayload> versesPayloads) {
        mContext = context;
        mVerses = versesPayloads;
    }

    @Override
    public VersesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View versesView = inflater.inflate(R.layout.verse, parent, false);
        VersesViewHolder versesViewHolder = new VersesViewHolder(versesView);
        return versesViewHolder;
    }

    @Override
    public void onBindViewHolder(VersesViewHolder holder, int position) {
        VersesPayload versesPayload = mVerses.get(position);
        String verseText = versesPayload.getText();
        TextView verseTextView = holder.mVerseView;
        verseTextView.setText(Html.fromHtml(verseText));
    }

    @Override
    public int getItemCount() {
        return mVerses.size();
    }

    public static class VersesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.verse_view) TextView mVerseView;
        public VersesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
