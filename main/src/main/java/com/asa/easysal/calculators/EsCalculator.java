package com.asa.easysal.calculators;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface EsCalculator extends Parcelable {

    boolean canHaveOvertime(Context context);

    void performCalculation(Context context, double[] values, CalculatorCallback callback);

    @StringRes
    int getSalaryHintText();

    @StringRes
    int getHoursHintText();

    void sendAnalyticsCalculateClickedEvent(@NonNull Context context);

    @NonNull
    String getType();

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CalculatorType.HOURLY, CalculatorType.DAILY, CalculatorType.WEEKLY, CalculatorType.BIWEEKLY,
            CalculatorType.MONTHLY, CalculatorType.YEARLY})
    @interface CalculatorType {
        int HOURLY = 0;
        int DAILY = 1;
        int WEEKLY = 2;
        int BIWEEKLY = 3;
        int MONTHLY = 4;
        int YEARLY = 5;
    }

    interface CalculatorCallback {
        void success(double[] results);

        void failure(@StringRes int errorResId);
    }


}
