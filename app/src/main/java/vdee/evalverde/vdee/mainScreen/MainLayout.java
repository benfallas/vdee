package vdee.evalverde.vdee.mainScreen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.analytics.Analytics;

/**
 * MainLayout should only know about the Main Layout screen
 * Controls the view only. Logic for this layout can be found in {@link MainController}
 */
public class MainLayout
        implements
            BottomNavigationView.OnNavigationItemSelectedListener {

    private ImageView shareButton;
    private MainActivity mMainActivity;
    private MainLayoutListener mMainLayoutListener;
    private Analytics mAnalytics;

    private BottomNavigationView bottomNavigationView;
    private Menu mMenu;

    MainLayout(
            @NonNull MainActivity mainActivity,
            @NonNull MainLayoutListener listener,
            @NonNull Analytics analytics) {
        mMainActivity = mainActivity;
        mMainLayoutListener = listener;
        mAnalytics = analytics;

        mMainActivity.setContentView(R.layout.activity_main);
        bottomNavigationView = mMainActivity.findViewById(R.id.navigation);
        shareButton = mMainActivity.findViewById(R.id.share_button);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.homeItem);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=vdee.evalverde.vdee");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Escucha Predicaciones!");
                intent.setType("text/plain");
                mMainActivity.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        updateMenuItems(item);
        return mMainLayoutListener.onNavigationItemSelected(item.getItemId());
    }

    private void updateMenuItems(MenuItem item) {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getItemId() == item.getItemId()) {
                menu.getItem(i).setChecked(true);
                menu.getItem(i).setEnabled(false);
            } else {
                menu.getItem(i).setCheckable(true);
                menu.getItem(i).setEnabled(true);
            }
        }
    }

    /**
     * Interface to communicate with {@link MainController}
     */
    interface MainLayoutListener {

        boolean onNavigationItemSelected(int itemId);
    }
}
