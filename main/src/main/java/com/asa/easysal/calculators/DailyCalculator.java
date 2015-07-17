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
public class DailyCalculator implements EsCalculator {

    @Override
    public boolean canHaveOvertime(Context context) {
        return SettingsUtil.isOvertime(context);
    }

    @Override
    public void performCalculation(Context context, double[] values, CalculatorCallback callback) {
        double[] results = CalculationUtils.performCalculation(context, CalculatorType.DAILY,
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
        return R.string.daily_salary;
    }

    @Override
    public int getHoursHintText() {
        return R.string.daily_hours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public DailyCalculator() {
    }

    protected DailyCalculator(Parcel in) {
    }

    public static final Creator<DailyCalculator> CREATOR = new Creator<DailyCalculator>() {
        @Override
        public DailyCalculator createFromParcel(Parcel in) {
            return new DailyCalculator(in);
        }

        @Override
        public DailyCalculator[] newArray(int size) {
            return new DailyCalculator[size];
        }
    };
}
