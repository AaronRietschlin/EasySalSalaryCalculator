package com.asa.easysal.analytics;

import android.content.Context;
import android.os.Bundle;

import com.asa.easysal.analytics.enums.AdditionalData;
import com.asa.easysal.analytics.enums.EventName;
import com.google.firebase.analytics.FirebaseAnalytics;

import timber.log.Timber;

public class AnalyticsManager {

    private static AnalyticsManager instance;
    private FirebaseAnalytics firebaseAnalytics;

    public static AnalyticsManager initialize(Context context) {
        if (instance == null) {
            instance = new AnalyticsManager(context);
        }
        return instance;
    }

    public static AnalyticsManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("You must initialize the analytics Manager.");
        }
        return instance;
    }

    public AnalyticsManager(Context context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public void logEvent(AnalyticsEvent event) {
        EventName eventName = event.eventName();
        String eventNameStr = eventName.toString();
        Bundle bundle;
        switch (eventName) {
            case CALCULATION:
                bundle = createCalculationBundle(event);
                logEvent(eventNameStr, bundle);
                break;
            case CALCULATION_ERROR:
                bundle = createCalculationErrorBundle(event);
                logEvent(eventNameStr, bundle);
                break;
            case OVERTIME_TOGGLE:
                logOvertimeAttribute(event);
                break;
        }
    }

    private void logEvent(String eventName, Bundle bundle) {
        Timber.d("Logging event:\n\t\tName: %s\n\t\tBundle: %s", eventName, bundle == null ? "No Bundle:" : bundle.toString());
        firebaseAnalytics.logEvent(eventName, bundle);
    }

    private Bundle createCalculationBundle(AnalyticsEvent event) {
        Bundle bundle = new Bundle();
        String salary = event.getData(AdditionalData.SALARY);
        String hours = event.getData(AdditionalData.HOURS);
        String calculatorType = event.getData(AdditionalData.CALCULATOR_TYPE);
        boolean overtimeOn = getOvertimeOn(event);
        if (overtimeOn) {
            double overtimeValue = getOvertimeValue(event);
            bundle.putString(AdditionalData.OVERTIME_VALUE.toString(), String.valueOf(overtimeValue));
        }
        bundle.putString(AdditionalData.SALARY.toString(), salary);
        bundle.putString(AdditionalData.HOURS.toString(), hours);
        bundle.putString(AdditionalData.CALCULATOR_TYPE.toString(), calculatorType);
        bundle.putBoolean(AdditionalData.OVERTIME_ON.toString(), overtimeOn);
        return bundle;
    }

    private Bundle createCalculationErrorBundle(AnalyticsEvent event) {
        Bundle bundle = new Bundle();
        bundle.putString(AdditionalData.CALCULATION_ERROR_TYPE.toString(), (String) event.getData(AdditionalData.CALCULATION_ERROR_TYPE));
        return bundle;
    }

    private void logOvertimeAttribute(AnalyticsEvent event) {
        boolean overtimeValue = getOvertimeOn(event);
        firebaseAnalytics.setUserProperty(AdditionalData.OVERTIME_VALUE.toString(), String.valueOf(overtimeValue));
    }

    private boolean getOvertimeOn(AnalyticsEvent event) {
        Boolean value = event.getData(AdditionalData.OVERTIME_ON);
        return value == null ? false : value;
    }

    private double getOvertimeValue(AnalyticsEvent event) {
        Double value = event.getData(AdditionalData.OVERTIME_VALUE);
        return value == null ? -1 : value;
    }

}
