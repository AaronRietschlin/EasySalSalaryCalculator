package com.asa.easysal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.R;

public class EsWeeklyFragment extends BaseFragment {

	public static final String TAG = "WEEKLY";

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	static EsWeeklyFragment newInstance(int num) {
		EsWeeklyFragment f = new EsWeeklyFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt("num", num);
		f.setArguments(args);

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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		overtimeTv.setVisibility(View.INVISIBLE);
		salaryTv.setText(R.string.weekly_salary);
		hoursWorkedTv.setText(R.string.hours_worked);
	}

	@Override
	protected void calculateClicked() {
		makeCalculation(CalculationUtils.TYPE_WEEKLY);
	}
}
