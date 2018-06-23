package vdee.vdee.mainScreen.fragments.bibleFragments.verses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vdee.vdee.R;

public class VersesFragment extends Fragment {

    public VersesFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        return inflater.inflate(R.layout.verses_layout, viewGroup, false);
    }
}
