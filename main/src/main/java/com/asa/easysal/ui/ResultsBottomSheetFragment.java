package com.asa.easysal.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TextView;

import com.asa.easysal.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsBottomSheetFragment extends BottomSheetDialogFragment {

    public static final String TAG = "ResultsFragment";
    private static final String EXTRA_RESULTS = "results";

    @BindView(R.id.results_tv_title)
    TextView tvTitle;

    public static ResultsBottomSheetFragment newInstance(double[] results) {
        ResultsBottomSheetFragment bottomSheetFragment = new ResultsBottomSheetFragment();
        Bundle args = new Bundle();
        args.putDoubleArray(EXTRA_RESULTS, results);
        bottomSheetFragment.setArguments(args);
        return bottomSheetFragment;
    }

    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            // NO-OP
        }
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View contentView = View.inflate(getContext(), R.layout.results_bottomsheet, null);
        ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);
        setupResults();

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
        }

        return dialog;
    }

    private void setupResults() {
        double[] results = getArguments().getDoubleArray(EXTRA_RESULTS);
        if (results == null) {
            throw new IllegalStateException("Results were null.");
        }
    }
}
