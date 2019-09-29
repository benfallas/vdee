package vdee.evalverde.vdee.features.verses;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.data.module.versesResponse.VersesPayload;

public class VersesLayout {

    private VersesActivity versesActivity;
    private Listener listener;
    private RecyclerView versesHolder;
    private VersesAdapter versesAdapter;
    private ArrayList<VersesPayload> versesPayloads;

    private TextView versesTitle;
    private TextView versesCopywrite;

    VersesLayout(VersesActivity versesActivity, Listener listener) {
        this.versesActivity = versesActivity;
        this.listener = listener;
        versesPayloads = new ArrayList<>();

        versesActivity.setContentView(R.layout.verses_layout);

        versesTitle = versesActivity.findViewById(R.id.verses_title);
        versesCopywrite = versesActivity.findViewById(R.id.verses_copyright);

        versesHolder = versesActivity.findViewById(R.id.recycler_view_verses);
        versesHolder.setLayoutManager(new LinearLayoutManager(versesActivity.getApplicationContext()));
        versesAdapter = new VersesAdapter(versesActivity, versesPayloads);
        versesHolder.setAdapter(versesAdapter);

    }

    public void setVersesTitle(String title) {
        versesTitle.setText(title);
    }

    public void setCopyWrite(Spanned copyWrite) {
        versesCopywrite.setText(copyWrite);
    }

    public void updateVersePayloads(ArrayList<VersesPayload> versesPayloads) {
        this.versesPayloads.clear();
        this.versesPayloads.addAll(versesPayloads);
        versesAdapter.updateVerses(versesPayloads);
    }

    interface Listener { }
}
