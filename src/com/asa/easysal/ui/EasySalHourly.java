package com.asa.easysal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.R;
import com.asa.easysal.Utils;
import com.asa.easysal.ui.EasySalSalaryCalculator.ButtonClickListener;
import com.asa.easysal.ui.EasySalSalaryCalculator.PageChangedListener;

public class EasySalHourly extends BaseFragment {

	public static final String TAG = "HOURLY";

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	static EasySalHourly newInstance(int num) {
		EasySalHourly f = new EasySalHourly();

		return f;
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		// if (!Util.prefsOtOn) {
		// overtimeTv.setVisibility(View.INVISIBLE);
		// } else {
		// overtimeTv.setVisibility(View.VISIBLE);
		// }
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		salaryTv.setText(R.string.hourly_wage);
		hoursWorkedTv.setText(R.string.hours_worked);

		mPageChangedListener = new PageChangedListener() {
			@Override
			public void pageChanged() {
				mActivity.setButtonClickListener(new ButtonClickListener() {
					@Override
					public void calculateButtonClicked() {
						makeCalculation();
					}

					@Override
					public void resetButtonClicked() {
						Utils.clearEditTexts(wageField, hoursField);
					}
				});
			}
		};
		// This is to allow the buttons to be clicked on first load
		mActivity.pageChanged(0);
	}

	private void makeCalculation() {
		double[] params = CalculationUtils.convertStringsToDoubles(
				getWageString(), getHoursString());
		double[] results = CalculationUtils.performCalculation(mActivity,
				CalculationUtils.TYPE_HOURLY, false, params);
		CalculateDialogFragment frag = CalculateDialogFragment.newInstance(
				R.string.title_activity_easy_sal_salary_calculator,
				R.layout.calculate_layout);
		frag.setResults(results);
		frag.show(mActivity.getSupportFragmentManager(), "Hourly_calcs");
	}

}
