package com.asa.easysal.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.asa.easysal.R;
import com.asa.easysal.ui.EasySalSalaryCalculator.PageChangedListener;

public class BaseFragment extends Fragment {

	protected EditText wageField;
	protected EditText hoursField;
	protected TextView salaryTv;
	protected TextView hoursWorkedTv;
	protected TextView overtimeTv;

	protected PageChangedListener mPageChangedListener;
	protected EasySalSalaryCalculator mActivity;

	public PageChangedListener getPageChangedListener() {
		return mPageChangedListener;
	}

	public void setPageChangedListener(PageChangedListener pageChangedListener) {
		this.mPageChangedListener = pageChangedListener;
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
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = (EasySalSalaryCalculator) getActivity();
	}

}
