package com.asa.easysal.ui;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.asa.easysal.R;

public class CalculateDialogFragment extends DialogFragment {
	private static ArrayList<BigDecimal> list;

	public static CalculateDialogFragment newInstance(int title, int layoutId,
			ArrayList<BigDecimal> nums) {
		list = nums;
		CalculateDialogFragment frag = new CalculateDialogFragment();
		Bundle args = new Bundle();
		args.putInt("title", title);
		args.putInt("layout", layoutId);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int title = getArguments().getInt("title");
		int layoutId = getArguments().getInt("layout");
		layoutId = R.layout.results;
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layoutId, null);
		// view = Util.setView(list, view);
		return new AlertDialog.Builder(getActivity())
				.setIcon(R.drawable.ic_launcher)
				.setTitle(title)
				.setPositiveButton(R.string.resultsCalculate,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
							}
						}).setView(view).create();
	}
}
