package vdee.evalverde.vdee.features.verses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import vdee.evalverde.vdee.baseActivity.DefaultBaseActivity;


public class VersesActivity extends DefaultBaseActivity {

    public static String VERSES_ID_KEY = "VERSES_ID_KEY";
    public static String VERSES_TITLE_KEY= "VERSES_TITLE_KEY";

    private VersesController versesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        versesController = new VersesController(this);
    }

    public static Intent getVersesActivityIntent(Activity activity, String versesKey, String versesTitle) {
        Intent intent = new Intent(activity, VersesActivity.class);
        intent.putExtra(VERSES_ID_KEY, versesKey);
        intent.putExtra(VERSES_TITLE_KEY, versesTitle);
        return intent;
    }
}
