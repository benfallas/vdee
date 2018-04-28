package vdee.vdee.bibleScreen.chaptersView;

import android.content.Context;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vdee.vdee.data.module.chaptersResponse.ChapterPayload;

public class ChaptersAdapter extends BaseAdapter {

    private ArrayList<ChapterPayload> mPayloads;
    private Context mContext;

    public ChaptersAdapter(Context context, ArrayList<ChapterPayload> payloads) {
        mContext = context;
        mPayloads = payloads;
    }
    @Override
    public int getCount() {
        return mPayloads.size();
    }

    @Override
    public Object getItem(int position) {
        return mPayloads.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;

        if (convertView == null) {
            textView = new TextView(mContext);
            textView.setLayoutParams(new ViewGroup.LayoutParams(85,85));
            textView.setPadding(8,8,8,8);
            textView.setTextColor(Color.parseColor("#bdbdbd"));
        } else {
            textView = (TextView) convertView;
        }
        Log.d("ChaptersAdapter", mPayloads.get(position).getChapter());
        textView.setText(mPayloads.get(position).getChapter());
        return textView;
    }
}
