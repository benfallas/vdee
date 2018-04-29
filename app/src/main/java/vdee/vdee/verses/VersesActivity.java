package vdee.vdee.verses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class VersesActivity extends AppCompatActivity {

    VersesController mVersesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVersesController = new VersesController(this);
    }

}
