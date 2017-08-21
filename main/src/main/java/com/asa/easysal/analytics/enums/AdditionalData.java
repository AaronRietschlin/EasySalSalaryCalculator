package com.asa.easysal.analytics.enums;

public enum AdditionalData {

    HOURS,
    BUTTON_TYPE,
    SALARY,
    CALCULATOR_TYPE,
    CALCULATION_ERROR_TYPE,
    OVERTIME_ON,
    MENU_CLICK,
    SETTINGS_TYPE,
    SETTINGS_VALUE,
    SETTINGS_VALUE_CHANGE,
    MENU_CLICK_SETTINGS,
    MENU_CLICK_HELP,
    OVERTIME_VALUE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
