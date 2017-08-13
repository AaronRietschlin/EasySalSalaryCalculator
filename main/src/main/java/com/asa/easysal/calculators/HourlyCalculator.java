package com.asa.easysal.calculators;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import com.asa.easysal.R;
import com.asa.easysal.SettingsUtil;

public class HourlyCalculator implements EsCalculator {

    @Override
    public boolean canHaveOvertime(Context context) {
        return SettingsUtil.isOvertime(context);
    }

    @Override
    public void performCalculation(Context context, double[] values, CalculatorCallback callback) {
        CalculatorCommon.performCalculation(context, values, callback, CalculatorType.HOURLY, canHaveOvertime(context));
    }

    @Override
    public int getSalaryHintText() {
        return R.string.hourly_wage;
    }

    @Override
    public int getHoursHintText() {
        return R.string.hours_worked;
    }

    @NonNull
    @Override
    public String getType() {
        return "Hourly";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static final Creator<HourlyCalculator> CREATOR = new Creator<HourlyCalculator>() {
        @Override
        public HourlyCalculator createFromParcel(Parcel in) {
            return new HourlyCalculator();
        }

        @Override
        public HourlyCalculator[] newArray(int size) {
            return new HourlyCalculator[size];
        }
    };
}
