package vdee.evalverde.vdee.util;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import vdee.evalverde.vdee.data.models.BiblePayload;
import vdee.evalverde.vdee.data.models.BookInfo;
import vdee.evalverde.vdee.data.models.Chapter;
import vdee.evalverde.vdee.data.models.ChapterInfo;
import vdee.evalverde.vdee.data.module.booksResponse.BooksResponse;

/**
 * Stores values for fetched experiments.
 *
 * This is needed since relying entirely on firebase might cause users to not see an
 * active experiment do to network issue.
 */
public class StorageUtils {

    private static ArrayList<BiblePayload> biblePayloads;
    private static BookInfo bookInfo;
    private static ChapterInfo chapterInfo;

    private static SharedPreferences mSharedPreferences;
    private static StorageUtils mStorageUtils;

    private StorageUtils() { }

    public static void initSharedUtils(SharedPreferences sharedPreferences) {
        if (mStorageUtils == null) {
            mStorageUtils = new StorageUtils();
            mSharedPreferences = sharedPreferences;
            biblePayloads = new ArrayList<>();
            chapterInfo = null;
            bookInfo = null;
        }
    }

    public static void updateBiblePayloads(ArrayList<BiblePayload> biblePayloads) {
        StorageUtils.biblePayloads.clear();
        StorageUtils.biblePayloads.addAll(biblePayloads);
    }

    public static Map<String, BookInfo> getListOfBooks() {
        Map<String, BookInfo> treeMap = new TreeMap<String, BookInfo>(biblePayloads.get(0).getVersion());
        return treeMap;
    }

    public static void updateSelectedBook(BookInfo bookInfo) {
        StorageUtils.bookInfo = bookInfo;
    }

    public static BookInfo getLatestSelectedBookInfo() {
        return bookInfo;
    }

    public static void updateChapterInfo(ChapterInfo chapterInfo) {
        StorageUtils.chapterInfo = chapterInfo;
    }

    public static ChapterInfo getChapterInfo() {
        return chapterInfo;
    }
}
