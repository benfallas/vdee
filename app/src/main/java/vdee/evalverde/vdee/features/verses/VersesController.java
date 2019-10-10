package vdee.evalverde.vdee.features.verses;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.util.Log;

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
import vdee.evalverde.vdee.data.models.ChapterInfo;
import vdee.evalverde.vdee.data.models.VerseInfo;
import vdee.evalverde.vdee.data.module.versesResponse.VersesPayload;
import vdee.evalverde.vdee.data.module.versesResponse.VersesResponse;
import vdee.evalverde.vdee.util.PerController;
import vdee.evalverde.vdee.util.StorageUtils;
import vdee.evalverde.vdee.vdeeApi.VdeeApi;

public class VersesController implements VersesLayout.Listener {



    @Inject Retrofit retrofit;
    @Inject VersesLayout versesLayout;

    private Analytics analytics;
    private VersesActivity versesActivity;
    private VersesAdapter versesAdapter;
    private VersesResponseListener versesResponseListener;
    private ArrayList<VersesPayload> versesPayloads;
    private String versesTitle;
    private ChapterInfo chapterInfo;

    VersesController(VersesActivity versesActivity) {
        this.versesActivity = versesActivity;
        analytics = Analytics.getAnalytics();


        DaggerVersesController_VersesControllerComponent.builder()
                .versesControllerModule(new VersesControllerModule(versesActivity, this))
                .experimentComponent(((VDEEApp) versesActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        chapterInfo = StorageUtils.getChapterInfo();
        Log.d("BibleFlow: ",  "chpaterNumber: VErse: " + String.valueOf(chapterInfo.getChapterNumber()));
        VerseInfo verseInfo = chapterInfo.verseInfoHashMap().get("1");
        Log.d("BibleFlow: ", "verse " + verseInfo.getVerse());
        versesLayout.setVersesTitle(StorageUtils.getLatestSelectedBookInfo().getBookName() + " " + chapterInfo.getChapterNumber());
        versesLayout.updateVersePayloads(chapterInfo.verseInfoHashMap());
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
