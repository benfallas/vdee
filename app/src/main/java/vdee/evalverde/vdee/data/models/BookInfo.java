package vdee.evalverde.vdee.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class BookInfo {

    @SerializedName("book_name") @Expose private String bookName;
    @SerializedName("book_nr") @Expose private int bookNumber;
    @SerializedName("book") @Expose private HashMap<String, ChapterInfo> chapterInfoHashMap;
    @SerializedName("direction") @Expose private String direction;
    @SerializedName("version") @Expose private String version;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public HashMap<String, ChapterInfo> getChapterInfoHashMap() {
        return chapterInfoHashMap;
    }

    public void setChapterInfoHashMap(HashMap<String, ChapterInfo> chapterInfoHashMap) {
        this.chapterInfoHashMap = chapterInfoHashMap;
    }
}
