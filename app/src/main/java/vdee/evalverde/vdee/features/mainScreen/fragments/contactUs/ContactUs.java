package vdee.evalverde.vdee.features.mainScreen.fragments.contactUs;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskGranted;

import vdee.evalverde.vdee.Animations.MyBounceInterpolator;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.mediaPlayer.SimplePlayer;
import vdee.evalverde.vdee.phoneCallReceiver.CallReceiver;

/**
 * Created by ben on 6/23/18.
 */

public class ContactUs extends Fragment {

    private Analytics mAnalytics;
    private Animation mAnimation;
    private MyBounceInterpolator mInterpolator;
    private SimplePlayer mSimplePlayer;

    private Button mCallButton;

    public ContactUs() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_contact, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onAttach();
    }

    private void onAttach() {
        mAnalytics = Analytics.getAnalytics();
        mAnalytics.contactPageView();
        mAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
        mInterpolator = new MyBounceInterpolator(0.2, 20);
        mAnimation.setInterpolator(mInterpolator);
        mSimplePlayer = SimplePlayer.getSimplePlayer();

        mCallButton = getActivity().findViewById(R.id.call_button);

        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnalytics.onPhoneButtonClicked();
                if (mSimplePlayer.isInitialized()) {
                    Toast.makeText(getActivity(),
                            "Por Favor Pare La Música Antes de Llamar", Toast.LENGTH_SHORT).show();
                } else {
                    mCallButton.startAnimation(mAnimation);
                    if (checkPermission(Manifest.permission.CALL_PHONE)) {
                        dial();
                    } else {
                        Ask.on(getActivity())
                                .forPermissions(Manifest.permission.CALL_PHONE)
                                .withRationales("Se Necesita Permiso para Realizar Llamada")
                                .go();
                    }
                }
            }
        });
    }


    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(
                getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    @AskGranted(Manifest.permission.CALL_PHONE)
    public void onCallPhoneGranted() {
        dial();
    }

    @AskGranted(Manifest.permission.CALL_PHONE)
    public void onCallPhoneDenied() { }

    private void dial() {
        Toast.makeText(getActivity(),
                "calling", Toast.LENGTH_SHORT).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:8314220647"));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getActivity().startActivity(callIntent);
    }
}
