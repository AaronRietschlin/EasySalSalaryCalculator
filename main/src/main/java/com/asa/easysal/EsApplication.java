package com.asa.easysal;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by aaron on 7/17/15.
 */
public class EsApplication extends Application {

    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.REPORT_CRASHES) {
            final Fabric fabric = new Fabric.Builder(this).kits(new Crashlytics()).build();
            Fabric.with(fabric);
            Timber.plant(new CrashReportingTree());
        } else {
            Timber.plant(new Timber.DebugTree());
        }

        startTracking();
    }

    public Tracker getTracker() {
        startTracking();
        return mTracker;
    }

    public void startTracking() {
        if (mTracker == null) {
            GoogleAnalytics ga = GoogleAnalytics.getInstance(this);
            if (!BuildConfig.SHOW_PROD_AD) {
                ga.setDryRun(true);
            }
            if (BuildConfig.IS_DEBUG) {
                ga.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
            }
            mTracker = ga.newTracker(BuildConfig.GA_TRACKING_ID);
        }
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
