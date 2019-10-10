package vdee.evalverde.vdee.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class ChapterInfo {

    @SerializedName("chapter_nr") @Expose private int chapterNumber;
    @SerializedName("chapter") @Expose private HashMap<String, VerseInfo> verseInfoHashMap;

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public HashMap<String, VerseInfo> verseInfoHashMap() {
        return verseInfoHashMap;
    }

    public void setVerseInfoHashMap(HashMap<String, VerseInfo> verseInfoHashMap) {
        this.verseInfoHashMap = verseInfoHashMap;
    }
}
