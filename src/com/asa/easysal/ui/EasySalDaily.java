package com.asa.easysal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.R;

public class EasySalDaily extends BaseFragment {

	public static final String TAG = "DAILY";

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	static EasySalDaily newInstance(int num) {
		EasySalDaily f = new EasySalDaily();

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

		salaryTv.setText(R.string.daily_salary);
		hoursWorkedTv.setText(R.string.daily_hours);

	}

	@Override
	protected void calculateClicked() {
		makeCalculation(CalculationUtils.TYPE_DAILY);
	}
}
