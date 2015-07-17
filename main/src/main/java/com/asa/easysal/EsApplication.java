package com.asa.easysal;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by aaron on 7/17/15.
 */
public class EsApplication extends Application {

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
//
            Crashlytics.log(priority, tag, message);
            if (t != null) {
                if (priority == Log.ERROR) {
                    Crashlytics.logException(t);
                }
                // TODO - do we want to check for warning priority?
            }
        }
    }
}
