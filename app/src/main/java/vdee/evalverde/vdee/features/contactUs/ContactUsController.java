package vdee.evalverde.vdee.features.contactUs;

import android.content.Intent;
import android.net.Uri;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import vdee.evalverde.vdee.util.PerController;

public class ContactUsController implements ContactUsLayout.ContactUsLayoutListener {

    @Inject ContactUsLayout contactUsLayout;

    private ContactUsActivity contactUsActivity;

    ContactUsController(ContactUsActivity activity) {
        this.contactUsActivity = activity;

        DaggerContactUsController_ContactUsControllerComponent.builder()
                .contactUsControllerModule(
                        new ContactUsControllerModule(contactUsActivity, this))
                .build()
                .inject(this);
    }

    @Override
    public void onDialClicked() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:8314220647"));

        contactUsActivity.startActivity(callIntent);
    }

    @PerController
    @Component(modules = ContactUsControllerModule.class)
    interface ContactUsControllerComponent {
        void inject(ContactUsController contactUsController);
    }

    @Module
    static class ContactUsControllerModule {
        private final ContactUsActivity contactUsActivity;
        private final ContactUsLayout.ContactUsLayoutListener contactUsLayoutListener;

        ContactUsControllerModule(ContactUsActivity contactUsActivity, ContactUsLayout.ContactUsLayoutListener listener) {
            this.contactUsActivity = contactUsActivity;
            this.contactUsLayoutListener = listener;
        }

        @Provides
        @PerController
        ContactUsLayout contactUsLayout() {
            return new ContactUsLayout(contactUsActivity, contactUsLayoutListener);
        }
    }
}
