package vdee.vdee.util;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import vdee.vdee.R;

/**
 * Created by ben on 6/23/18.
 */

public class ParentFragment extends Fragment {

    ProgressDialog progressDialog;
    AlertDialog mMessageDialog;

    public void ParentFragment() { }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("cargando");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void showDialog() {
        if (progressDialog == null) {
            initProgressDialog();
        }
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    public void showNetworkError() {
        if (mMessageDialog == null) {
            mMessageDialog = new AlertDialog.Builder(getActivity())
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
}
