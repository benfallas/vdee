package vdee.evalverde.vdee.baseActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import vdee.evalverde.vdee.R;

public class DefaultBaseActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    AlertDialog mMessageDialog;

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

    public void showNetworkError(String detailedError) {
        if (mMessageDialog == null) {
            mMessageDialog = new AlertDialog.Builder(this)
                    .setMessage(detailedError)
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

    @Override
    public void onPause() {
        super.onPause();
        hideDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideDialog();
    }
}
