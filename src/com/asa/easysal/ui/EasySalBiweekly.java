package com.asa.easysal.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asa.easysal.R;

public class EasySalBiweekly extends Fragment {
	int mNum;

	private TextView hoursWorkedTv;
	private TextView overtimeTv;

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	static EasySalBiweekly newInstance(int num) {
		EasySalBiweekly f = new EasySalBiweekly();

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
		mNum = getArguments() != null ? getArguments().getInt("num") : 1;
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.calculate_layout, container, false);
		hoursWorkedTv = (TextView) v.findViewById(R.id.main_hours_label);
		overtimeTv = (TextView) v.findViewById(R.id.main_ot_label);

		overtimeTv.setVisibility(View.INVISIBLE);

		return v;
	}

	@Override
	public void onResume() {
//		if (Util.prefsBiweeklyValue == Util.BIWEEKLY_VALUE_BI) {
//			hoursWorkedTv.setText(getString(R.string.biweekly_hours));
//		} else {
//			hoursWorkedTv.setText(getString(R.string.hours_worked));
//		}
		super.onResume();
	}
}
