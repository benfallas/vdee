package vdee.evalverde.vdee.data.models;

import com.google.gson.annotations.SerializedName;

public class VerseInfo {

    @SerializedName("verse") private String verse;
    @SerializedName("verse_nr") private int verseNumber;

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public int getVerseNumber() {
        return verseNumber;
    }

    public void setVerseNumber(int verseNumber) {
        this.verseNumber = verseNumber;
    }
}
