package vdee.vdee.bibleScreen.chaptersView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static vdee.vdee.bibleScreen.BibleLayout.BOOK_TITLE;

/**
 * Activity for Chapter numbers.
 */
public class ChaptersActivity extends AppCompatActivity {

    private ChaptersController mChaptersController;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        mChaptersController = new ChaptersController(this);
    }
}
