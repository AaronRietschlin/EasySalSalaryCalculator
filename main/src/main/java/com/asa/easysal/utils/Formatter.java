package com.asa.easysal.utils;

import android.icu.text.DecimalFormat;
import android.os.Build;

import java.util.Currency;

public class Formatter {

    private DecimalFormat format;
    private java.text.DecimalFormat formatCompat;
    private Currency currency;

    public Formatter() {
        if (isNougat()) {
            format = new DecimalFormat("#.00");
        } else{
            formatCompat = new java.text.DecimalFormat("#.00");
        }
    }

    // TODO - here, we should look into proper formatting for different regions.
    public String format(double value) {
        return "$" + (isNougat() ? format.format(value) : formatCompat.format(value));
    }

    private boolean isNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

}
