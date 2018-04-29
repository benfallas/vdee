package vdee.vdee.data.module.versesResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import vdee.vdee.data.module.chaptersResponse.Meta;

/**
 * VersesBodyResponse body for Verses.
 */
public class VersesBodyResponse {

    @SerializedName("verses") @Expose
    private ArrayList<VersesPayload> chapterPayloads = null;
    @SerializedName("meta") @Expose private Meta meta;

    public ArrayList<VersesPayload> getVersesPayload() {
        return chapterPayloads;
    }

    public void setChapterPayloads(ArrayList<VersesPayload> chapterPayloads) {
        this.chapterPayloads = chapterPayloads;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "VersesBodyResponse: " + " VersesPayloads: " + getVersesPayload() +
                " Meta: " + getMeta();
    }
}
