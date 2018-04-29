package vdee.vdee.vdeeApi;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vdee.vdee.data.module.booksResponse.BooksResponse;
import vdee.vdee.data.module.chaptersResponse.ChaptersResponse;
import vdee.vdee.data.module.versesResponse.VersesResponse;

public interface VdeeApi{

    public static String API_KEY = "u1IDoa19G87bf5l4PmB9LAemRV20tg2LKoAp3k6F";

    @GET("versions/spa-RVR1960/books.js/")
    rx.Observable<BooksResponse> getBible();

    @GET("books/{book_id}/chapters.js")
    rx.Observable<ChaptersResponse> getChaptersByBookId(@Path("book_id") String bookId);

    @GET("chapters/{chapter_id}/verses.js")
    rx.Observable<VersesResponse> getVerses(@Path("chapter_id") String chapterId);
}
