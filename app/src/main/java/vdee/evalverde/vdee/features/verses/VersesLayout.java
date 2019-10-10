package vdee.evalverde.vdee.features.verses;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.data.models.VerseInfo;
import vdee.evalverde.vdee.data.module.versesResponse.VersesPayload;

public class VersesLayout {

    private VersesActivity versesActivity;
    private Listener listener;
    private RecyclerView versesHolder;
    private VersesAdapter versesAdapter;
    private HashMap<String, VerseInfo> verseInfoHashMap;

    private TextView versesTitle;

    VersesLayout(VersesActivity versesActivity, Listener listener) {
        this.versesActivity = versesActivity;
        this.listener = listener;
        verseInfoHashMap = new HashMap<>();

        versesActivity.setContentView(R.layout.verses_layout);

        versesTitle = versesActivity.findViewById(R.id.verses_title);

        versesHolder = versesActivity.findViewById(R.id.recycler_view_verses);
        versesHolder.setLayoutManager(new LinearLayoutManager(versesActivity.getApplicationContext()));
        versesAdapter = new VersesAdapter(versesActivity, verseInfoHashMap);
        versesHolder.setAdapter(versesAdapter);

    }

    public void setVersesTitle(String title) {
        versesTitle.setText(title);
    }


    public void updateVersePayloads(HashMap<String, VerseInfo> verseInfoHashMap) {
        this.verseInfoHashMap.clear();
        this.verseInfoHashMap.putAll(verseInfoHashMap);
        versesAdapter.updateVerses(verseInfoHashMap);
    }

    interface Listener { }
}
