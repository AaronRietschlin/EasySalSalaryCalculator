package com.asa.easysal;

import android.support.annotation.Nullable;

public class EsException extends RuntimeException {

    public EsException(@Nullable double[] values) {
        super(extractMessage(values));
    }

    private static String extractMessage(@Nullable double[] values) {
        if (values == null) {
            return "Values array is null";
        }
        if (values.length == 0) {
            return "Values array is empty (size == 0)";
        }
        if (values.length == 1) {
            return "Values arrays size is 1. Value: " + values[0];
        }
        return "Values: " + values[0] + "; " + values[1];
    }

}
