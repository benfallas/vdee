package vdee.vdee.maranatha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MaranathaActivity extends AppCompatActivity {

    private MaranathaController mMaranathaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMaranathaController = new MaranathaController(this);
    }
}
