package com.asa.easysal.calculators;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.EsException;
import com.asa.easysal.R;
import com.asa.easysal.analytics.AnalyticsContants;
import com.asa.easysal.analytics.AnalyticsHelper;

import timber.log.Timber;

public class YearlyCalculator implements EsCalculator {

    @Override
    public boolean canHaveOvertime(Context context) {
        return false;
    }

    @Override
    public void performCalculation(Context context, double[] values, CalculatorCallback callback) {
        double[] results = CalculationUtils.performCalculation(context, CalculatorType.YEARLY,
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
        return R.string.yearly_salary;
    }

    @Override
    public int getHoursHintText() {
        return R.string.yearly_monthly_hours;
    }

    @NonNull
    @Override
    public String getType() {
        return "Yearly";
    }

    public static final Creator<YearlyCalculator> CREATOR = new Creator<YearlyCalculator>() {
        @Override
        public YearlyCalculator createFromParcel(Parcel in) {
            return new YearlyCalculator();
        }

        @Override
        public YearlyCalculator[] newArray(int size) {
            return new YearlyCalculator[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
