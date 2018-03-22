package vdee.vdee.mainScreen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import vdee.vdee.R;
import vdee.vdee.bibleScreen.BibleActivity;
import vdee.vdee.contact.ContactActivity;

/**
 * Manages Navigation Menu
 */
public class NavigationMenu
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainActivity mMainActivity;

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    NavigationMenu(MainActivity mainActivity) {
        mMainActivity = mainActivity;
        ButterKnife.bind(this, mMainActivity);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.bible:
                intent = new Intent(mMainActivity, BibleActivity.class);
                mMainActivity.startActivity(intent);
                break;
            case R.id.contactItem:
                Intent contactIntent = new Intent(mMainActivity, ContactActivity.class);
                mMainActivity.startActivity(contactIntent);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
