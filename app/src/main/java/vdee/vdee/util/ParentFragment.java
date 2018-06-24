package vdee.vdee.util;

import android.app.Fragment;
import android.app.ProgressDialog;

/**
 * Created by ben on 6/23/18.
 */

public class ParentFragment extends Fragment {

    ProgressDialog progressDialog;

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
}
