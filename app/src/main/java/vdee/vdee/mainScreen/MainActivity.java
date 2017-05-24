package vdee.vdee.mainScreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Main Activity controls the Android lifecycle of main screen.
 */
public class MainActivity extends AppCompatActivity {

    private MainController mMainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainController = new MainController(this);
    }
}
