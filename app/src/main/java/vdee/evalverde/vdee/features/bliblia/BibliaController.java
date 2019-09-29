package vdee.evalverde.vdee.features.bliblia;

import android.support.annotation.IdRes;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.Component;
import dagger.Provides;
import retrofit2.Retrofit;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vdee.evalverde.vdee.VDEEApp;
import vdee.evalverde.vdee.analytics.Analytics;
import vdee.evalverde.vdee.component.ExperimentComponent;
import vdee.evalverde.vdee.data.module.booksResponse.Book;
import vdee.evalverde.vdee.util.PerController;
import vdee.evalverde.vdee.vdeeApi.VdeeApi;

public class BibliaController implements BibliaLayout.Listener {

    @Inject BibliaLayout bibliaLayout;
    @Inject Retrofit retrofit;

    private BibleResponseListener bibleResponseListener;
    private ChaptersResponseListener chaptersResponseListener;

    private Analytics analytics;


    private BibliaActivity bibliaActivity;

    BibliaController(BibliaActivity bibliaActivity) {
        this.bibliaActivity = bibliaActivity;
        analytics = Analytics.getAnalytics();



        DaggerBibliaController_BibliaControllerComponent.builder()
                .bibliaControllerModule(new BibliaControllerModule(bibliaActivity, this, retrofit))
                .experimentComponent(((VDEEApp) bibliaActivity.getApplicationContext()).getExpComponent())
                .build()
                .inject(this);

        analytics.biblePageView();
        bibleResponseListener = new BibleResponseListener(bibliaLayout);

        retrofit.create(VdeeApi.class)
                .getBible()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bibleResponseListener);
        bibliaActivity.showDialog();

    }

    @Override
    public void onBibleBookClicked(String bookId) {
        chaptersResponseListener = new ChaptersResponseListener(bibliaLayout);

        bibliaActivity.showDialog();
        retrofit.create(VdeeApi.class)
                .getChaptersByBookId(bookId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chaptersResponseListener);

    }


    @PerController
    @Component(dependencies = ExperimentComponent.class, modules = BibliaControllerModule.class)
    interface BibliaControllerComponent {
        void inject(BibliaController bibliaController);
    }


    @dagger.Module
    static class BibliaControllerModule {

        private final BibliaActivity bibliaActivity;
        private final BibliaLayout.Listener listener;
        private final Retrofit retrofit;

        BibliaControllerModule(
                BibliaActivity bibliaActivity,
                BibliaLayout.Listener listener,
                Retrofit retrofit) {
            this.bibliaActivity = bibliaActivity;
            this.listener = listener;
            this.retrofit = retrofit;
        }

        @PerController
        @Provides
        BibliaLayout bibliaLayout() {
            return new BibliaLayout(bibliaActivity, listener, retrofit);
        }
    }
}
