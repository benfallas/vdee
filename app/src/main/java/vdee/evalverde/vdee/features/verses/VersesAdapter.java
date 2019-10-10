package vdee.evalverde.vdee.features.verses;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.data.models.VerseInfo;
import vdee.evalverde.vdee.data.module.versesResponse.VersesPayload;

public class VersesAdapter extends RecyclerView.Adapter<VersesAdapter.VersesViewHolder> {

    private HashMap<String, VerseInfo> verseInfoHashMap;
    private Context mContext;

    VersesAdapter(Context context, HashMap<String, VerseInfo> verseInfoHashMap) {
        mContext = context;
        this.verseInfoHashMap = verseInfoHashMap;
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
        if (position != 0) {
            VerseInfo verseInfo = verseInfoHashMap.get(String.valueOf(position));
            String verseText = verseInfo.getVerse();
            TextView verseTextView = holder.mVerseView;
            TextView verseNumber = holder.verseNumber;
            verseTextView.setText(Html.fromHtml(verseText));
            verseNumber.setText(String.valueOf(verseInfo.getVerseNumber()));
        }
    }

    @Override
    public int getItemCount() {
        return verseInfoHashMap.size();
    }

    public void updateVerses(HashMap<String, VerseInfo> verseInfoHashMap) {
        this.verseInfoHashMap.clear();
        this.verseInfoHashMap.putAll(verseInfoHashMap);
        notifyDataSetChanged();
    }

    public static class VersesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.verse_view) TextView mVerseView;
        @BindView(R.id.verse_number) TextView verseNumber;

        public VersesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
