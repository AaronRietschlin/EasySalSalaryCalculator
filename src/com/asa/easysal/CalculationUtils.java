package com.asa.easysal;

import java.text.DecimalFormat;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class CalculationUtils {
	private static final String TAG = "CalculationUtils";

	// The following are the types that are used in this app.
	public static final int TYPE_HOURLY = 0;
	public static final int TYPE_DAILY = 1;
	public static final int TYPE_WEEKLY = 2;
	public static final int TYPE_BIWEEKLY = 3;
	public static final int TYPE_MONTHLY = 4;
	public static final int TYPE_YEARLY = 5;

	// The following is the max values that is used in order to display the fun
	// errors.
	public static int MAX_WEEKLY = 168;
	public static int MAX_DAILY = 24;

	// The following are the values to multiply for Overtime.
	public static final double OVERTIME_DOUBLE = 2;
	public static final double OVERTIME_TIME_HALF = 1.5;
	// TODO - Get Overtime value from settings
	public static double OVERTIME_VALUE;
	public static final int OVERTIME_VALUE_WEEK = 40;
	public static final int OVERTIME_VALUE_DAY = 8;

	// THe following are values t
	public static final int DAYS_IN_WEEK = 5;
	public static final int WEEKS_IN_YEAR = 52;
	public static final int WEEKS_IN_TWO = 2;
	public static final int MONTHS_IN_YEAR = 12;

	private static Context mContext;
	private static boolean mIsOvertime;
	private static double mHours;
	private static double mWage;
	private static int mType;

	public static double[] performCalculation(Context context, int type,
			boolean isOvertime, double... params) {
		mContext = context;
		mIsOvertime = isOvertime;
		mHours = params[1];
		mWage = params[0];
		mType = type;

		checkHours();

		switch (type) {
		case TYPE_HOURLY:
			return calculateHourly();
		case TYPE_DAILY:
			return calculateDaily();
		case TYPE_WEEKLY:
			return calculateWeekly();
		case TYPE_BIWEEKLY:
			return calculateBiweekly();
		case TYPE_MONTHLY:
			return calculateMonthly();
		case TYPE_YEARLY:
			return calculateYearly();
		}
		return null;
	}

	public static double[] convertStringsToDoubles(String... params) {
		if (params == null || params.length < 2) {
			throw new IllegalStateException(
					"Parameters must not be null and you must pass two.");
		}
		double[] converted = new double[2];
		String wage = params[0], hours = params[1];
		try {
			converted[0] = Double.valueOf(wage);
		} catch (NumberFormatException e) {
			Log.e(TAG, "An error converting the input to a double: " + wage);
			return new double[] { -1F, -1F };
		}
		try {
			converted[1] = Double.valueOf(hours);
		} catch (NumberFormatException e) {
			Log.e(TAG, "An error converting the input to a double: " + hours);
			return new double[] { -1F, -1F };
		}
		return converted;
	}

	private static double[] calculateHourly() {
		int size = 6;
		double hoursDelta = 0, overtimeResult = 0;
		// If it's OT, then there needs to be one more value returned.
		if (mIsOvertime) {
			size = 7;
			if (mHours > OVERTIME_VALUE_WEEK) {
				hoursDelta = mHours - OVERTIME_VALUE_WEEK;
				overtimeResult = mWage * OVERTIME_VALUE * hoursDelta;
				mHours = OVERTIME_VALUE_DAY;
			}
		}
		double[] result = new double[size];

		double hourlyResult = mWage;
		double weeklyResult = mWage * mHours;
		double dailyResult = weeklyResult / DAYS_IN_WEEK;
		double yearlyResult = weeklyResult * WEEKS_IN_YEAR;
		double biweeklyResult = weeklyResult * WEEKS_IN_TWO;
		double monthlyResult = yearlyResult / MONTHS_IN_YEAR;

		result[0] = hourlyResult;
		result[1] = dailyResult;
		result[2] = weeklyResult;
		result[3] = biweeklyResult;
		result[4] = monthlyResult;
		result[5] = yearlyResult;
		if (mIsOvertime) {
			result[6] = overtimeResult;
		}
		roundResults(result);
		return result;
	}

	private static double[] calculateDaily() {
		int size = 6;
		double hoursDelta = 0, overtimeResult = 0;
		// If it's OT, then there needs to be one more value returned.
		if (mIsOvertime) {
			size = 7;
			if (mHours > OVERTIME_VALUE_DAY) {
				hoursDelta = mHours - OVERTIME_VALUE_DAY;
				overtimeResult = mWage * OVERTIME_VALUE * hoursDelta;
				mHours = OVERTIME_VALUE_DAY;
			}
		}
		double[] result = new double[size];

		double dailyResult = mWage;
		// TODO - Add in days worked spinner
		double weeklyResult = dailyResult * DAYS_IN_WEEK;
		double hourlyResult = dailyResult / mHours;
		double yearlyResult = weeklyResult * WEEKS_IN_YEAR;
		double biweeklyResult = weeklyResult * WEEKS_IN_TWO;
		double monthlyResult = yearlyResult / MONTHS_IN_YEAR;

		result[0] = hourlyResult;
		result[1] = dailyResult;
		result[2] = weeklyResult;
		result[3] = biweeklyResult;
		result[4] = monthlyResult;
		result[5] = yearlyResult;
		if (mIsOvertime) {
			result[6] = overtimeResult;
		}
		roundResults(result);
		return result;
	}

	private static double[] calculateWeekly() {
		double[] result = new double[6];

		double weeklyResult = mWage;
		double hourlyResult = weeklyResult / mHours;
		// TODO - Add in days worked spinner?
		double dailyResult = weeklyResult / DAYS_IN_WEEK;
		double yearlyResult = weeklyResult * WEEKS_IN_YEAR;
		double biweeklyResult = weeklyResult * WEEKS_IN_TWO;
		double monthlyResult = yearlyResult / MONTHS_IN_YEAR;

		result[0] = hourlyResult;
		result[1] = dailyResult;
		result[2] = weeklyResult;
		result[3] = biweeklyResult;
		result[4] = monthlyResult;
		result[5] = yearlyResult;

		roundResults(result);

		return result;
	}

	private static double[] calculateBiweekly() {
		double[] result = new double[6];

		double biweeklyResult = mWage;
		double weeklyResult = biweeklyResult / WEEKS_IN_TWO;
		// TODO - Add in days worked spinner?
		double dailyResult = weeklyResult / DAYS_IN_WEEK;
		// TODO - Account for user option of saying every two weeks or every
		// week.
		double hourlyResult = biweeklyResult / mHours;
		double yearlyResult = weeklyResult * WEEKS_IN_YEAR;
		double monthlyResult = yearlyResult / MONTHS_IN_YEAR;

		result[0] = hourlyResult;
		result[1] = dailyResult;
		result[2] = weeklyResult;
		result[3] = biweeklyResult;
		result[4] = monthlyResult;
		result[5] = yearlyResult;

		roundResults(result);

		return result;
	}

	private static double[] calculateMonthly() {
		double[] result = new double[6];

		double monthlyResult = mWage;
		double yearlyResult = monthlyResult * MONTHS_IN_YEAR;
		double weeklyResult = yearlyResult / WEEKS_IN_YEAR;
		double biweeklyResult = weeklyResult * WEEKS_IN_TWO;
		double dailyResult = weeklyResult / DAYS_IN_WEEK;
		double hourlyResult = weeklyResult / mHours;

		result[0] = hourlyResult;
		result[1] = dailyResult;
		result[2] = weeklyResult;
		result[3] = biweeklyResult;
		result[4] = monthlyResult;
		result[5] = yearlyResult;

		roundResults(result);

		return result;
	}

	private static double[] calculateYearly() {
		double[] result = new double[6];

		double yearlyResult = mWage;
		double monthlyResult = yearlyResult / MONTHS_IN_YEAR;
		double weeklyResult = yearlyResult / WEEKS_IN_YEAR;
		double biweeklyResult = weeklyResult * WEEKS_IN_TWO;
		double dailyResult = weeklyResult / DAYS_IN_WEEK;
		double hourlyResult = weeklyResult / mHours;

		result[0] = hourlyResult;
		result[1] = dailyResult;
		result[2] = weeklyResult;
		result[3] = biweeklyResult;
		result[4] = monthlyResult;
		result[5] = yearlyResult;

		roundResults(result);

		return result;
	}

	/**
	 * Checks if the given hours worked is valid. If not, it will display a
	 * toast. This does nothing, just for fun.
	 * 
	 * @return
	 */
	private static boolean checkHours() {
		switch (mType) {
		case TYPE_HOURLY:
			if (mHours > MAX_WEEKLY) {
				showToast(R.string.fun_error_hourly_weeks);
			}
			return false;
		case TYPE_DAILY:
			if (mHours > MAX_DAILY) {
				showToast(R.string.fun_error_hourly_days);
			}
			return false;
		case TYPE_WEEKLY:
			if (mHours > MAX_WEEKLY) {
				showToast(R.string.fun_error_weekly);
			}
			return false;
		case TYPE_BIWEEKLY:
			if (mHours > MAX_WEEKLY * 2) {
				showToast(R.string.fun_error_weekly);
			}
			return false;
		case TYPE_MONTHLY:
			if (mHours > MAX_WEEKLY) {
				showToast(R.string.fun_error_weekly);
			}
			return false;
		}
		return true;
	}

	private static void showToast(int messageId) {
		Toast.makeText(mContext, messageId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Rounds the given results to two decimal places.
	 * 
	 * @param results
	 */
	private static void roundResults(double[] results) {
		for (int i = 0; i < results.length; i++) {
			double result = results[i];
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			results[i] = Double.valueOf(twoDForm.format(result));
		}
	}
}
