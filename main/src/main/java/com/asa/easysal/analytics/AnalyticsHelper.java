package com.asa.easysal.analytics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.asa.easysal.EsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class AnalyticsHelper {

    public static Tracker getTracker(Context context) {
        return ((EsApplication) context).getTracker();
    }

    public static void sendScreenView(Context context, @AnalyticsContants.Screens String stringName) {
        Tracker tracker = getTracker(context);
        tracker.setScreenName(stringName);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public static void sendEvent(@NonNull Context context, @Nullable String category,
                                 @AnalyticsContants.Events @Nullable String action, @Nullable String label) {
        Tracker tracker = getTracker(context);
        HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder();
        if (isEmpty(category)) {
            eventBuilder.setCategory(category);
        }
        if (isEmpty(action)) {
            eventBuilder.setAction(action);
        }
        if (isEmpty(label)) {
            eventBuilder.setLabel(label);
        }
        tracker.send(eventBuilder.build());
    }

    private static boolean isEmpty(@Nullable String text) {
        return text == null || text.length() == 0;
    }

    public static void sendSettingsClickedEvent(@NonNull Context context) {
        sendEvent(context, "", AnalyticsContants.EVENT_SETTINGS_ACTION_CLICKED, "");
    }

}
