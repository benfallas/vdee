package vdee.evalverde.vdee.features.bliblia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.baseActivity.BaseActivity;

public class BibliaActivity extends BaseActivity {


    @Override
    public int getContentViewId() {
        return R.layout.activity_biblia;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.bible;
    }
}
