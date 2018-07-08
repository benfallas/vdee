package vdee.evalverde.vdee.util;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import vdee.evalverde.vdee.data.module.booksResponse.BooksResponse;

/**
 * Stores values for fetched experiments.
 *
 * This is needed since relying entirely on firebase might cause users to not see an
 * active experiment do to network issue.
 */
public class StorageUtils {

    public static final String MULTIPLE_RADIO_SUPPORT = "MUTIPLE_RADIO_SUPPORT";
    public static final String BOOKS_RESPONSE = "books_response";

    private static SharedPreferences mSharedPreferences;
    private static StorageUtils mStorageUtils;

    private StorageUtils() { }

    public static void initSharedUtils(SharedPreferences sharedPreferences) {
        if (mStorageUtils == null) {
            mStorageUtils = new StorageUtils();
            mSharedPreferences = sharedPreferences;
        }
    }

    /**
     * @param isMutipleRadioSupported fetched value from firebase.
     */
    public static void storeMultiplePlayersExperiment(boolean isMutipleRadioSupported) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(MULTIPLE_RADIO_SUPPORT, isMutipleRadioSupported);
        editor.commit();
    }

    /**
     * Whether multiple radio is supported or not.
     *
     * @return {@true} if multiple radio functionality is supported. {@false} otherwise.
     */
    public static boolean isMutipleRadioXpSupported() {
        return mSharedPreferences.getBoolean(MULTIPLE_RADIO_SUPPORT, false);
    }

    public static void storeBooksResponse(BooksResponse booksResponse) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(booksResponse);
        editor.putString(BOOKS_RESPONSE, json);
        editor.commit();
    }

    public static BooksResponse getStoredBooksResponse() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString(BOOKS_RESPONSE, "");
        BooksResponse booksResponse = gson.fromJson(json, BooksResponse.class);
        return booksResponse;
    }
}
