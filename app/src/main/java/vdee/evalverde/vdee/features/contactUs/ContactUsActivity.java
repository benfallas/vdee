package vdee.evalverde.vdee.features.contactUs;

import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.baseActivity.BaseActivity;

public class ContactUsActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_contact_us;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.contactItem;
    }
}
