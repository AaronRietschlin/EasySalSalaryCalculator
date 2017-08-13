package com.asa.easysal.calculators;

import android.content.Context;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.EsException;
import com.asa.easysal.R;
import com.asa.easysal.calculators.EsCalculator.CalculatorCallback;
import com.asa.easysal.calculators.EsCalculator.CalculatorType;

import timber.log.Timber;

class CalculatorCommon {

    public static void performCalculation(Context context, double[] values, CalculatorCallback callback,
                                   @CalculatorType int type, boolean overtime) {
        double[] results = CalculationUtils.performCalculation(context, type, overtime, values);
        if (results != null) {
            callback.success(results);
        } else {
            Timber.e(new EsException(values), "");
            callback.failure(R.string.default_error);
        }
    }

}
