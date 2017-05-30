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
import vdee.vdee.maranatha.MaranathaActivity;

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
        switch (item.getItemId()) {
            case R.id.maranatha:
                Intent intent = new Intent(mMainActivity, MaranathaActivity.class);
                mMainActivity.startActivity(intent);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
