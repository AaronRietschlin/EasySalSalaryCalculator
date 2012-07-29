package com.asa.easysal.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asa.easysal.R;

public class EasySalDaily extends Fragment {
	int mNum;
	
	private TextView salaryTv;
	private TextView hoursWorkedTv;
	private TextView overtimeTv;

	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	static EasySalDaily newInstance(int num) {
		EasySalDaily f = new EasySalDaily();

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
		salaryTv = (TextView) v.findViewById(R.id.main_wage_label);
		hoursWorkedTv = (TextView) v.findViewById(R.id.main_hours_label);
		overtimeTv = (TextView) v.findViewById(R.id.main_ot_label);

		salaryTv.setText(R.string.daily_salary);
		hoursWorkedTv.setText(R.string.daily_hours);

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();

//		if (!Util.prefsOtOn) {
//			overtimeTv.setVisibility(View.INVISIBLE);
//		} else {
//			overtimeTv.setVisibility(View.VISIBLE);
//		}
	}
}
