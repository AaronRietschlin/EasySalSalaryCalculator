package com.asa.easysal.calculators;

import android.content.Context;
import android.os.Parcel;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.EsException;
import com.asa.easysal.R;

import timber.log.Timber;

/**
 * Created by aaron on 7/14/15.
 */
public class BiWeeklyCalculator implements EsCalculator {

    @Override
    public boolean canHaveOvertime(Context context) {
        return false;
    }

    @Override
    public void performCalculation(Context context, double[] values, CalculatorCallback callback) {
        double[] results = CalculationUtils.performCalculation(context, CalculatorType.BIWEEKLY,
                canHaveOvertime(context), values);
        if (results != null) {
            callback.success(results);
        } else {
            Timber.e(new EsException(values), "");
            callback.failure(R.string.default_error);
        }
    }

    @Override
    public int getSalaryHintText() {
        return R.string.biweekly_salary;
    }

    @Override
    public int getHoursHintText() {
        return R.string.biweekly_hours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public BiWeeklyCalculator() {
    }

    protected BiWeeklyCalculator(Parcel in) {
    }

    public static final Creator<BiWeeklyCalculator> CREATOR = new Creator<BiWeeklyCalculator>() {
        @Override
        public BiWeeklyCalculator createFromParcel(Parcel in) {
            return new BiWeeklyCalculator(in);
        }

        @Override
        public BiWeeklyCalculator[] newArray(int size) {
            return new BiWeeklyCalculator[size];
        }
    };
}
