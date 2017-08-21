package com.asa.easysal;

import android.app.Application;
import android.util.Log;

import com.asa.easysal.analytics.AnalyticsManager;
import com.asa.easysal.firebase.RemoteConfigHandler;
import com.asa.easysal.model.Injector;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class EsApplication extends Application {

    private RemoteConfigHandler remoteConfigHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        setupRemoteConfigHandler();

        if (BuildConfig.REPORT_CRASHES) {
            final Fabric fabric = new Fabric.Builder(this).kits(new Crashlytics()).build();
            Fabric.with(fabric);
            Timber.plant(new CrashReportingTree());
        } else {
            Timber.plant(new Timber.DebugTree());
        }

        MobileAds.initialize(this, BuildConfig.DEBUG ? Utils.getDebugAdmobId() : BuildConfig.ADMOB_ID);
        AnalyticsManager.initialize(this);
    }

    private void setupRemoteConfigHandler() {
        remoteConfigHandler = Injector.provideRemoteConfigHandler();
        remoteConfigHandler.initialize();
    }

    /**
     * The {@link timber.log.Timber.Tree} that will be used to send our crashes to Crashlytics.
     */
    protected static class CrashReportingTree extends Timber.Tree {

        @Override
        protected boolean isLoggable(int priority) {
            return !(priority == Log.VERBOSE || priority == Log.DEBUG) && super.isLoggable(priority);
        }

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (!isLoggable(priority)) {
                return;
            }

            Crashlytics.log(priority, tag, message);
            if (t != null) {
                if (priority == Log.ERROR) {
                    Crashlytics.logException(t);
                }
            }
        }
    }
}
