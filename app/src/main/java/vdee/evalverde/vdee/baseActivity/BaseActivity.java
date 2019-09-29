package vdee.evalverde.vdee.baseActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.features.bliblia.BibliaActivity;
import vdee.evalverde.vdee.features.contactUs.ContactUsActivity;
import vdee.evalverde.vdee.features.mainScreen.MainActivity;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    ProgressDialog progressDialog;
    AlertDialog mMessageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        onAttached();
    }

    public void onAttached() { }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        hideDialog();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.homeItem) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (itemId == R.id.bible) {
            startActivity(new Intent(this, BibliaActivity.class));
        } else if (itemId == R.id.contactItem) {
            startActivity(new Intent(this, ContactUsActivity.class));
        }
        finish();
        return true;
    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("cargando");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void showDialog() {
        if (progressDialog == null) {
            initProgressDialog();
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void hideDialog() {
        if (progressDialog != null) {
            progressDialog.hide();
            progressDialog = null;
        }
    }


    public void showNetworkError() {
        if (mMessageDialog == null) {
            mMessageDialog = new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.network_error))
                    .setNeutralButton(getString(R.string.continuar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mMessageDialog.hide();
                            mMessageDialog = null;
                        }
                    }).create();

        }
        mMessageDialog.show();
    }

    public abstract int getContentViewId();

    public abstract int getNavigationMenuItemId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideDialog();
    }
}
