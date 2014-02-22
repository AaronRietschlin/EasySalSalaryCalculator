package com.asa.easysal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asa.easysal.CalculationUtils;

public class EasySalBiweekly extends BaseFragment {

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	static EasySalBiweekly newInstance(int num) {
		EasySalBiweekly f = new EasySalBiweekly();

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
	public void onResume() {
		super.onResume();
		// if (Util.prefsBiweeklyValue == Util.BIWEEKLY_VALUE_BI) {
		// hoursWorkedTv.setText(getString(R.string.biweekly_hours));
		// } else {
		// hoursWorkedTv.setText(getString(R.string.hours_worked));
		// }
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		overtimeTv.setVisibility(View.INVISIBLE);

	}

	@Override
	protected void calculateClicked() {
		makeCalculation(CalculationUtils.TYPE_BIWEEKLY);
	}
}
