package com.asa.easysal.calculators;

import android.content.Context;
import android.os.Parcel;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.EsException;
import com.asa.easysal.R;
import com.asa.easysal.SettingsUtil;

import timber.log.Timber;

/**
 * Created by aaron on 7/14/15.
 */
public class HourlyCalculator implements EsCalculator {

    @Override
    public boolean canHaveOvertime(Context context) {
        return SettingsUtil.isOvertime(context);
    }

    @Override
    public void performCalculation(Context context, double[] values, CalculatorCallback callback) {
        double[] results = CalculationUtils.performCalculation(context, CalculatorType.HOURLY,
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
        return R.string.hourly_wage;
    }

    @Override
    public int getHoursHintText() {
        return R.string.hours_worked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public HourlyCalculator() {
    }

    protected HourlyCalculator(Parcel in) {
    }

    public static final Creator<HourlyCalculator> CREATOR = new Creator<HourlyCalculator>() {
        @Override
        public HourlyCalculator createFromParcel(Parcel in) {
            return new HourlyCalculator(in);
        }

        @Override
        public HourlyCalculator[] newArray(int size) {
            return new HourlyCalculator[size];
        }
    };
}
