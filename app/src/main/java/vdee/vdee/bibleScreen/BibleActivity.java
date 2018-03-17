package vdee.vdee.bibleScreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vdee.vdee.R;

public class BibleActivity extends AppCompatActivity {

    private BibleController mBibleController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBibleController = new BibleController(this);
    }
}
