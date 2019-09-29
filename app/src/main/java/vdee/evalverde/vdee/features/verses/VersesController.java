package vdee.evalverde.vdee.features.verses;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.data.module.versesResponse.VersesPayload;
import vdee.evalverde.vdee.data.module.versesResponse.VersesResponse;
import vdee.evalverde.vdee.util.PerController;
import vdee.evalverde.vdee.vdeeApi.VdeeApi;

import static vdee.evalverde.vdee.features.verses.VersesActivity.VERSES_ID_KEY;
import static vdee.evalverde.vdee.features.verses.VersesActivity.VERSES_TITLE_KEY;

public class VersesController implements VersesLayout.Listener, VersesResponseListener.Listener {



    @Inject Retrofit retrofit;
    @Inject VersesLayout versesLayout;

    private Analytics analytics;
    private VersesActivity versesActivity;
    private VersesAdapter versesAdapter;
    private VersesResponseListener versesResponseListener;
    private ArrayList<VersesPayload> versesPayloads;
    private String versesTitle;

    VersesController(VersesActivity versesActivity) {
        this.versesActivity = versesActivity;
        analytics = Analytics.getAnalytics();


        DaggerVersesController_VersesControllerComponent.builder()
                .versesControllerModule(new VersesControllerModule(versesActivity, this))
                .experimentComponent(((VDEEApp) versesActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        versesResponseListener = new VersesResponseListener(this);

        Intent intent = versesActivity.getIntent();

        String versesId = intent != null ? intent.getStringExtra(VERSES_ID_KEY) : "";
        versesTitle = intent != null ? intent.getStringExtra(VERSES_TITLE_KEY) : "";

        retrofit.create(VdeeApi.class)
                .getVerses(versesId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(versesResponseListener);
        versesActivity.showDialog();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        versesActivity.hideDialog();
        versesActivity.showNetworkError();
    }

    @Override
    public void onNext(VersesResponse versesResponse) {

        versesPayloads  = versesResponse.getResponse().getVersesPayload();
        versesLayout.setVersesTitle(versesTitle);
        versesLayout.setCopyWrite(Html.fromHtml(versesPayloads.get(0).getCopyright()));
        displayVerses(versesPayloads);
        versesActivity.hideDialog();
    }

    private void displayVerses(ArrayList<VersesPayload> versesPayloads) {
        versesLayout.updateVersePayloads(versesPayloads);
    }

    @PerController
    @Component(dependencies = ExperimentComponent.class, modules = VersesControllerModule.class)
    interface VersesControllerComponent {
        void inject(VersesController versesController);
    }

    @Module
    static class VersesControllerModule {

        private final VersesActivity versesActivity;
        private final VersesLayout.Listener listener;

        VersesControllerModule(
                VersesActivity versesActivity, VersesLayout.Listener listener) {
            this.versesActivity = versesActivity;
            this.listener = listener;
        }

        @Provides
        @PerController
        VersesLayout versesLayout() {
            return new VersesLayout(versesActivity, listener);
        }
    }
}
