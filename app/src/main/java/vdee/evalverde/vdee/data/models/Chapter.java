package vdee.evalverde.vdee.data.models;

import java.util.HashMap;

public class Chapter {

    private float chapterNumber;
    private HashMap<String, Verse> verses;

    public float getChapterNumber() {
        return chapterNumber;
    }

    public HashMap<String, Verse> getVerses() {
        return verses;
    }

    public void setChapterNumber(float chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public void setVerses(HashMap<String, Verse> verses) {
        this.verses = verses;
    }
}
