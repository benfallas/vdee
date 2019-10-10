package vdee.evalverde.vdee.features.verses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import vdee.evalverde.vdee.baseActivity.DefaultBaseActivity;


public class VersesActivity extends DefaultBaseActivity {

    private VersesController versesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        versesController = new VersesController(this);
    }

    public static Intent getVersesActivityIntent(Activity activity) {
        Intent intent = new Intent(activity, VersesActivity.class);
        return intent;
    }
}
