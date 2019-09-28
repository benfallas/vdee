package vdee.evalverde.vdee.features.mainScreen.fragments.bibleFragments.chapter;

import android.content.Context;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

import vdee.evalverde.vdee.data.module.chaptersResponse.ChapterPayload;

public class ChaptersAdapter extends BaseAdapter {

    private ArrayList<ChapterPayload> mPayloads;
    private Context mContext;

    private ChapterButtonListener mChapterButtonListener;

    public ChaptersAdapter(Context context, ArrayList<ChapterPayload> payloads,
                           ChapterButtonListener chapterButtonListener) {
        mChapterButtonListener = chapterButtonListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Button textView;

        if (convertView == null) {
            textView = new Button(mContext);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setPadding(8,8,8,8);
            textView.setTextColor(Color.parseColor("#000000"));
        } else {
            textView = (Button) convertView;
        }
        textView.setText(mPayloads.get(position).getChapter());

        textView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mChapterButtonListener.onChapterButtonClicked(position);
                    }
                }
        );
        return textView;
    }

    interface ChapterButtonListener {
        void onChapterButtonClicked(int position);
    }
}
