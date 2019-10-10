package vdee.evalverde.vdee.features.bliblia;

import android.content.Context;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Map;

import vdee.evalverde.vdee.data.models.ChapterInfo;
import vdee.evalverde.vdee.data.models.VerseInfo;
import vdee.evalverde.vdee.data.module.chaptersResponse.ChapterPayload;

public class ChaptersAdapter extends BaseAdapter {

    private Map<String, ChapterInfo> chapterInfoMap;
    private Context mContext;

    private ChapterButtonListener mChapterButtonListener;

    public ChaptersAdapter(Context context, Map<String, ChapterInfo> chapterInfoMap,
                           ChapterButtonListener chapterButtonListener) {
        mChapterButtonListener = chapterButtonListener;
        mContext = context;
        this.chapterInfoMap = chapterInfoMap;
    }
    @Override
    public int getCount() {
        return chapterInfoMap.size() + 1;
    }

    @Override
    public Object getItem(int i) {
        return chapterInfoMap.get(String.valueOf(i));
    }

    @Override
    public long getItemId(int i) {
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

        if (position == 0) {
            textView.setVisibility(View.GONE);
        }

        final ChapterInfo chapterInfo = chapterInfoMap.get(String.valueOf(position));


        if (chapterInfo != null) {
            textView.setText(String.valueOf(chapterInfo.getChapterNumber()));
        } else {
            textView.setText("null");
        }

        textView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mChapterButtonListener.onChapterButtonClicked(chapterInfo);
                    }
                }
        );
        return textView;
    }

    public void updateChapters(Map<String, ChapterInfo> chapterInfoMap) {
        this.chapterInfoMap.clear();
        this.chapterInfoMap.putAll(chapterInfoMap);
        notifyDataSetChanged();
    }

    interface ChapterButtonListener {
        void onChapterButtonClicked(ChapterInfo chapterInfo);
    }
}
