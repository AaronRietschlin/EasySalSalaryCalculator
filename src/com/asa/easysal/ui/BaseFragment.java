package com.asa.easysal.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.R;
import com.asa.easysal.SettingsUtil;
import com.asa.easysal.Utils;
import com.asa.easysal.ui.EasySalSalaryCalculator.PageChangedListener;

public class BaseFragment extends Fragment {

	protected EditText wageField;
	protected EditText hoursField;
	protected TextView salaryTv;
	protected TextView hoursWorkedTv;
	protected TextView overtimeTv;

	// Not keeping the reset button. TODO - Keep this in case I bring it back.
	// private Button resetButton;
	private Button calculateButton;

	protected PageChangedListener mPageChangedListener;
	protected EasySalSalaryCalculator mActivity;

	public PageChangedListener getPageChangedListener() {
		return mPageChangedListener;
	}

	public void setPageChangedListener(PageChangedListener pageChangedListener) {
		this.mPageChangedListener = pageChangedListener;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (EasySalSalaryCalculator) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.calculate_layout, container, false);
		salaryTv = (TextView) v.findViewById(R.id.main_wage_label);
		hoursWorkedTv = (TextView) v.findViewById(R.id.main_hours_label);
		overtimeTv = (TextView) v.findViewById(R.id.main_ot_label);
		wageField = (EditText) v.findViewById(R.id.main_wage_field);
		hoursField = (EditText) v.findViewById(R.id.main_hours_field);

		calculateButton = (Button) v.findViewById(R.id.button_calculate);
		// resetButton = (Button) v.findViewById(R.id.button_reset);
		calculateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				calculateClicked();
			}
		});
		
		// TODO - Keep this in case I am going to keep it. 
		// resetButton.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Utils.clearEditTexts(wageField, hoursField);
		// }
		// });

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = (EasySalSalaryCalculator) getActivity();
		if (!Utils.isHoneycombOrHigher()) {
			hoursField.setTextColor(Color.BLACK);
			wageField.setTextColor(Color.BLACK);
		}
	}

	protected String getWageString() {
		return wageField.getText().toString().trim();
	}

	protected String getHoursString() {
		return hoursField.getText().toString().trim();
	}

	/**
	 * Checks the given string to make sure it is not empty.
	 * 
	 * @param wageString
	 * @return
	 */
	protected boolean isStringValid(String wageString) {
		return !(wageString == null || wageString.length() == 0);
	}

	protected void makeCalculation(int type) {
		// Check the entered wage/salary and don't let them proceed if wrong.
		String wageString = getWageString();
		if (!isStringValid(wageString)) {
			Utils.showCrouton(mActivity, R.string.error_no_salary_entered);
			return;
		}
		String hourString = getHoursString();
		if (!isStringValid(hourString)) {
			Utils.showCrouton(mActivity, R.string.error_no_hours_entered);
			return;
		}

		double[] params = CalculationUtils.convertStringsToDoubles(
				getWageString(), getHoursString());
		double[] results = CalculationUtils.performCalculation(mActivity, type,
				SettingsUtil.isOvertime(getActivity()), params);
		CalculateDialogFragment frag = CalculateDialogFragment.newInstance(
				R.string.results_dialog_title,
				R.layout.calculate_layout);
		frag.setResults(results);
		frag.show(mActivity.getSupportFragmentManager(), "calculation_result");
	}

	// OVERRIDE
	protected void configurationChanged() {
	}

	protected void calculateClicked() {

	}
}
