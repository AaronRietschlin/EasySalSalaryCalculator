package com.asa.easysal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.R;

public class EsMonthlyFragment extends BaseFragment {
	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	static EsMonthlyFragment newInstance(int num) {
		EsMonthlyFragment f = new EsMonthlyFragment();

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
		salaryTv.setText(R.string.monthly_salary);
		hoursWorkedTv.setText(R.string.yearly_monthly_hours);

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void calculateClicked() {
		makeCalculation(CalculationUtils.TYPE_MONTHLY);
	}
}
