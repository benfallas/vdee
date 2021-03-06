package vdee.evalverde.vdee.data.module.chaptersResponse;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("chapters") @Expose private ArrayList<ChapterPayload> chapterPayloads = null;
    @SerializedName("meta") @Expose private Meta meta;

    public ArrayList<ChapterPayload> getChapterPayloads() {
        return chapterPayloads;
    }

    public void setChapterPayloads(ArrayList<ChapterPayload> chapterPayloads) {
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
        return "VersesBodyResponse: " + " ChapterPaylaods: " + getChapterPayloads() +
                " Meta: " + getMeta();
    }
}