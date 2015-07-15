package com.asa.easysal.calculators;

import android.os.Parcel;

/**
 * Created by aaron on 7/14/15.
 */
public class HourlyCalculator implements EsCalculator {
    @Override
    public boolean canHaveOvertime() {
        return true;
    }

    @Override
    public void performCalculation(double[] values, CalculatorCallback callback) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
