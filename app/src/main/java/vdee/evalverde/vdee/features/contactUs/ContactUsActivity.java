package vdee.evalverde.vdee.features.contactUs;

import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.baseActivity.BaseActivity;

public class ContactUsActivity extends BaseActivity {

    private ContactUsController contactUsController;

    public void onAttached() {
        contactUsController = new ContactUsController(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_contact_us;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.contactItem;
    }
}
