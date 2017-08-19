package com.asa.easysal.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asa.easysal.R;
import com.asa.easysal.ui.EsSalaryFragment.CalculationListener;
import com.asa.easysal.utils.Formatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ResultsBottomSheetFragment extends BottomSheetDialogFragment {

    public static final String TAG = "ResultsFragment";
    private static final String EXTRA_RESULTS = "results";

    @BindView(R.id.results_tv_title)
    TextView tvTitle;
    @BindView(R.id.results_tv_results_hourly)
    TextView tvHourly;
    @BindView(R.id.results_tv_results_daily)
    TextView tvDaily;
    @BindView(R.id.results_tv_results_weekly)
    TextView tvWeekly;
    @BindView(R.id.results_tv_results_biweekly)
    TextView tvBiweekly;
    @BindView(R.id.results_tv_results_monthly)
    TextView tvMonthly;
    @BindView(R.id.results_tv_results_yearly)
    TextView tvYearly;
    @BindView(R.id.results_tv_results_ot)
    TextView tvOvertime;
    @BindView(R.id.results_container_ot)
    LinearLayout containerOt;

    private CalculationListener calculationListener;
    private Formatter formatter;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        calculationListener = (CalculationListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formatter = new Formatter();
    }

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

    @OnClick(R.id.btn_calculate_again)
    public void onCalculateAgainClicked() {
        calculationListener.onCalculateAgain();
        dismiss();
    }

    private void setupResults() {
        double[] results = getArguments().getDoubleArray(EXTRA_RESULTS);
        if (results == null) {
            throw new IllegalStateException("Results were null.");
        }
        String hourly = formatter.format(results[0]);
        String daily = formatter.format(results[1]);
        String weekly = formatter.format(results[2]);
        String biweekly = formatter.format(results[3]);
        String monthly = formatter.format(results[4]);
        String yearly = formatter.format(results[5]);
        tvHourly.setText(hourly);
        tvDaily.setText(daily);
        tvWeekly.setText(weekly);
        tvBiweekly.setText(biweekly);
        tvMonthly.setText(monthly);
        tvYearly.setText(yearly);
        setOvertime(results);
    }

    private void setOvertime(double[] results) {
        if (results.length == 7 && results[6] > 0) {
            tvOvertime.setText(formatter.format(results[6]));
            containerOt.setVisibility(VISIBLE);
        } else {
            containerOt.setVisibility(GONE);
        }
    }

}
