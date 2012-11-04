package com.asa.easysal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.R;
import com.asa.easysal.Utils;
import com.asa.easysal.ui.EasySalSalaryCalculator.ButtonClickListener;
import com.asa.easysal.ui.EasySalSalaryCalculator.PageChangedListener;

public class EasySalYearly extends BaseFragment {

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	static EasySalYearly newInstance(int num) {
		EasySalYearly f = new EasySalYearly();

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
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		overtimeTv.setVisibility(View.INVISIBLE);
		salaryTv.setText(R.string.yearly_salary);
		hoursWorkedTv.setText(R.string.yearly_monthly_hours);

		mPageChangedListener = new PageChangedListener() {
			@Override
			public void pageChanged() {
				mActivity.setButtonClickListener(new ButtonClickListener() {
					@Override
					public void calculateButtonClicked() {
						makeCalculation(CalculationUtils.TYPE_YEARLY);
					}

					@Override
					public void resetButtonClicked() {
						Utils.clearEditTexts(wageField, hoursField);
					}
				});
			}
		};
	}
}
