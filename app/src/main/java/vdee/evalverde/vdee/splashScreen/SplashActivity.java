package vdee.evalverde.vdee.splashScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import dagger.Component;
import vdee.evalverde.vdee.R;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.data.models.BiblePayload;
import vdee.evalverde.vdee.features.mainScreen.MainActivity;
import vdee.evalverde.vdee.parent.ParentActivity;
import vdee.evalverde.vdee.util.PerFragment;
import vdee.evalverde.vdee.util.StorageUtils;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends ParentActivity {

    private static final String TAG = "SpashActivity";

    @Inject SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerSplashActivity_SplashFragment.builder()
                .experimentComponent(((VDEEApp) getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        setContentView(R.layout.activity_splash);

        /** Initializes storage manager. **/
        StorageUtils.initSharedUtils(mSharedPreferences);

        InputStream XmlFileInputStream = getResources().openRawResource(R.raw.bible);
        String jsonString = readTextFile(XmlFileInputStream);

        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<BiblePayload>>(){}.getType();
        ArrayList<BiblePayload> biblePayloads = gson.fromJson(jsonString, collectionType);
        StorageUtils.updateBiblePayloads(biblePayloads);
    }

    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        launchMainScreen();
    }

    private void launchMainScreen() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @PerFragment
    @Component(dependencies = ExperimentComponent.class)
    interface SplashFragment {
        void inject(SplashActivity splashActivity);
    }
}
