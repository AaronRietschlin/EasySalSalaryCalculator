package com.asa.easysal.analytics.enums;

public enum EventName {

    CALCULATION,
    CALCULATION_ERROR,
    MENU_CLICK,
    OVERTIME_TOGGLE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
