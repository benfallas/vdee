package vdee.vdee.contact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Contact Activity controls the Android lifecycle of contact us screen.
 */
public class ContactActivity extends AppCompatActivity {

    private ContactController mContactController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContactController = new ContactController(this);
    }
}
