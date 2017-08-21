package com.asa.easysal.analytics;

import android.content.Context;
import android.os.Bundle;

import com.asa.easysal.analytics.enums.AdditionalData;
import com.asa.easysal.analytics.enums.EventName;
import com.google.firebase.analytics.FirebaseAnalytics;

import timber.log.Timber;

import static com.asa.easysal.analytics.enums.AdditionalData.CLEAR_FIELDS_VALUE;

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
            case MENU_CLICK:
                logMenuClick(event);
                break;
            case SETTINGS_CLICKED:
                logSettingsClick(event);
                break;
            case CALCULATE_AGAIN_CLICKED:
                logCalculateAgainClicked(event);
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
        String buttonType = event.getData(AdditionalData.BUTTON_TYPE).toString();
        boolean overtimeOn = getOvertimeOn(event);
        if (overtimeOn) {
            double overtimeValue = getOvertimeValue(event);
            bundle.putString(AdditionalData.OVERTIME_VALUE.toString(), String.valueOf(overtimeValue));
        }
        bundle.putString(AdditionalData.SALARY.toString(), salary);
        bundle.putString(AdditionalData.HOURS.toString(), hours);
        bundle.putString(AdditionalData.CALCULATOR_TYPE.toString(), calculatorType);
        bundle.putBoolean(AdditionalData.OVERTIME_ON.toString(), overtimeOn);
        bundle.putString(AdditionalData.BUTTON_TYPE.toString(), buttonType);
        return bundle;
    }

    private Bundle createCalculationErrorBundle(AnalyticsEvent event) {
        Bundle bundle = new Bundle();
        String errorType = event.getData(AdditionalData.CALCULATION_ERROR_TYPE);
        String calculatorType = event.getData(AdditionalData.CALCULATOR_TYPE);
        String buttonType = event.getData(AdditionalData.BUTTON_TYPE).toString();
        bundle.putString(AdditionalData.CALCULATION_ERROR_TYPE.toString(), errorType);
        bundle.putString(AdditionalData.CALCULATOR_TYPE.toString(), calculatorType);
        bundle.putString(AdditionalData.BUTTON_TYPE.toString(), buttonType);
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

    private void logMenuClick(AnalyticsEvent event) {
        Bundle bundle = new Bundle();
        AdditionalData data = event.getData(AdditionalData.MENU_CLICK);
        bundle.putString(AdditionalData.MENU_CLICK.toString(), data.toString());
        firebaseAnalytics.logEvent(EventName.MENU_CLICK.toString(), bundle);
    }

    private void logSettingsClick(AnalyticsEvent event) {
        Bundle bundle = new Bundle();
        String settingsType = event.getData(AdditionalData.SETTINGS_TYPE);
        Object settingsValue = event.getData(AdditionalData.SETTINGS_VALUE);
        bundle.putString(AdditionalData.SETTINGS_TYPE.toString(), settingsType);
        if (settingsValue != null) {
            bundle.putString(AdditionalData.SETTINGS_VALUE.toString(), settingsValue.toString());
        }
        firebaseAnalytics.logEvent(EventName.SETTINGS_CLICKED.toString(), bundle);
    }

    private void logCalculateAgainClicked(AnalyticsEvent event) {
        Bundle bundle = new Bundle();
        Object clearFieldsValue = event.getData(CLEAR_FIELDS_VALUE);
        if (clearFieldsValue != null) {
            bundle.putString(CLEAR_FIELDS_VALUE.toString(), clearFieldsValue.toString());
        }
        firebaseAnalytics.logEvent(EventName.CALCULATE_AGAIN_CLICKED.toString(), bundle);
    }

}
