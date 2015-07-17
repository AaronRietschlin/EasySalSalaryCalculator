package com.asa.easysal.calculators;

import android.content.Context;
import android.os.Parcel;

import com.asa.easysal.R;
import com.asa.easysal.SettingsUtil;

/**
 * Created by aaron on 7/14/15.
 */
public class HourlyCalculator implements EsCalculator {

    @Override
    public boolean canHaveOvertime(Context context) {
        return SettingsUtil.isOvertime(context);
    }

    @Override
    public void performCalculation(double[] values, CalculatorCallback callback) {

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
