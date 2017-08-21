package com.asa.easysal.analytics.enums;

public enum AdditionalData {

    HOURS,
    BUTTON_TYPE,
    SALARY,
    CALCULATOR_TYPE,
    CALCULATION_ERROR_TYPE,
    OVERTIME_ON,
    OVERTIME_VALUE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
