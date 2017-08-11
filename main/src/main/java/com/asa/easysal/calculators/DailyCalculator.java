package com.asa.easysal.calculators;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.EsException;
import com.asa.easysal.R;
import com.asa.easysal.SettingsUtil;
import com.asa.easysal.analytics.AnalyticsContants;
import com.asa.easysal.analytics.AnalyticsHelper;

import timber.log.Timber;

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
    public void sendAnalyticsCalculateClickedEvent(@NonNull Context context) {
        AnalyticsHelper.sendEvent(context, "", AnalyticsContants.EVENT_CALCULATE_CLICKED,
                AnalyticsContants.CALC_TYPE_DAILY);
    }

    @Override
    @NonNull
    public String getType() {
        return "Daily";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static final Creator<DailyCalculator> CREATOR = new Creator<DailyCalculator>() {
        @Override
        public DailyCalculator createFromParcel(Parcel in) {
            return new DailyCalculator();
        }

        @Override
        public DailyCalculator[] newArray(int size) {
            return new DailyCalculator[size];
        }
    };
}
