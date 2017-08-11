package com.asa.easysal.analytics.enums;

public enum EventName {

    CALCULATION,
    OVERTIME_TOGGLE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
