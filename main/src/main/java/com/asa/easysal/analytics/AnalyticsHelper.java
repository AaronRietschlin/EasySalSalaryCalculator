package com.asa.easysal.analytics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.asa.easysal.EsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class AnalyticsHelper {

    public static Tracker getTracker(Context context) {
        return ((EsApplication) context.getApplicationContext()).getTracker();
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

    public static void sendOvertimeTurnedOnEvent(@NonNull Context context) {
        sendEvent(context, "", AnalyticsContants.EVENT_OVERTIME_TURNED_ON, "");
    }

    public static void sendOvertimeTurnedOffEvent(@NonNull Context context) {
        sendEvent(context, "", AnalyticsContants.EVENT_OVERTIME_TURNED_OFF, "");
    }

    public static void sendOvertimeValueChangedEvent(@NonNull Context context, String value) {
        sendEvent(context, "", AnalyticsContants.EVENT_OVERTIME_AMOUNT_CHANGED, value);
    }

    public static void sendOpenSourceLicensesnClickedEvent(@NonNull Context context) {
        sendEvent(context, "", AnalyticsContants.EVENT_OPEN_SOURCE_LICENSES_CLICKED, "");
    }

    public static void sendOvertimeInfoButtonClickedEvent(@NonNull Context context) {
        sendEvent(context, "", AnalyticsContants.EVENT_OVERTIME_INFO_BTN_CLICKED, "");
    }

    public static void sendClearFieldsToggled(@NonNull Context context, boolean value) {
        sendEvent(context, "", AnalyticsContants.EVENT_CLEAR_FIELDS_TOGGLED, String.valueOf(value));
    }

}
