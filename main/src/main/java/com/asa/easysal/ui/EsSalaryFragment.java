package com.asa.easysal.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.R;
import com.asa.easysal.SettingsUtil;
import com.asa.easysal.SnackUtils;
import com.asa.easysal.Utils;
import com.asa.easysal.analytics.AnalyticsEvent;
import com.asa.easysal.analytics.AnalyticsHelper;
import com.asa.easysal.analytics.AnalyticsManager;
import com.asa.easysal.analytics.enums.AdditionalData;
import com.asa.easysal.analytics.enums.EventName;
import com.asa.easysal.calculators.EsCalculator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EsSalaryFragment extends Fragment implements EsCalculator.CalculatorCallback {
    public static final String TAG = EsSalaryFragment.class.getSimpleName();
    private static final String EXTRA_CALCULATOR = "calculator";

    @BindView(R.id.main_wage_field)
    EditText mWageField;
    @BindView(R.id.main_wage_field_layout)
    TextInputLayout mWageInputLayout;
    @BindView(R.id.main_hours_field)
    EditText mHoursField;
    @BindView(R.id.main_hours_field_layout)
    TextInputLayout mHoursInputLayout;

    protected EsHostActivityCompat mActivity;
    private EsCalculator mCalculator;
    private AnalyticsManager analyticsManager;

    public static EsSalaryFragment newInstance(EsCalculator calculator) {
        EsSalaryFragment fragment = new EsSalaryFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_CALCULATOR, calculator);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (EsHostActivityCompat) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (mCalculator == null) {
            Bundle args = getArguments();
            if (args != null) {
                mCalculator = args.getParcelable(EXTRA_CALCULATOR);
            }
        }
        analyticsManager = AnalyticsManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calculate_layout, container, false);
        ButterKnife.bind(this, v);

        mHoursInputLayout.setHint(getString(mCalculator.getHoursHintText()));
        mWageInputLayout.setHint(getString(mCalculator.getSalaryHintText()));
        mHoursInputLayout.setErrorEnabled(true);
        mWageInputLayout.setErrorEnabled(true);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!Utils.isHoneycombOrHigher()) {
            mHoursField.setTextColor(Color.BLACK);
            mWageField.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.invalidateOptionsMenu();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCalculator != null) {
            outState.putParcelable(EXTRA_CALCULATOR, mCalculator);
        }
    }

    protected String getWageString() {
        return mWageField.getText().toString().trim();
    }

    protected String getHoursString() {
        return mHoursField.getText().toString().trim();
    }

    /**
     * Checks the given string to make sure it is not empty.
     *
     * @param wageString
     * @return
     */
    protected boolean isStringValid(String wageString) {
        return !(wageString == null || wageString.length() == 0);
    }


    @OnClick(R.id.button_calculate)
    protected void onCalculateClicked() {
        resetErrors();

        // Check the entered wage/salary and don't let them proceed if wrong.
        String wageString = getWageString();
        if (!isStringValid(wageString)) {
            mWageInputLayout.setError(getString(R.string.error_no_salary_entered));
            Utils.performShakeOnView(mActivity, mWageInputLayout);
            logCalculationFailure("wage");
            return;
        }
        String hourString = getHoursString();
        if (!isStringValid(hourString)) {
            mHoursInputLayout.setError(getString(R.string.error_no_hours_entered));
            Utils.performShakeOnView(mActivity, mHoursInputLayout);
            logCalculationFailure("hours");
            return;
        }

        double[] params = CalculationUtils.convertStringsToDoubles(getWageString(), getHoursString());
        mCalculator.performCalculation(mActivity.getApplicationContext(), params, this);
        logCalculationSuccess(wageString, hourString);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mCalculator.canHaveOvertime(mActivity)) {
            inflater.inflate(R.menu.menu_ot, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ot:
                if (mCalculator.canHaveOvertime(mActivity)) {
                    Snackbar snackbar = SnackUtils.make(this, R.string.overtime_turned_on, Snackbar.LENGTH_LONG);
                    if (snackbar != null) {
                        snackbar.show();
                    }
                    AnalyticsHelper.sendOvertimeInfoButtonClickedEvent(mActivity);
                    return true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void success(double[] results) {
        CalculateDialogFragment frag = CalculateDialogFragment.newInstance(R.string.results_dialog_title);
        frag.setResults(results);
        frag.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (SettingsUtil.shouldClearFields(mActivity)) {
                    mHoursField.setText("");
                    mWageField.setText("");
                    mWageField.requestFocus();
                }
            }
        });
//        frag.show(mActivity.getSupportFragmentManager(), "calculation_result");

    }

    @Override
    public void failure(@StringRes int errorResId) {

    }

    private void resetErrors() {
        mWageInputLayout.setError("");
        mHoursInputLayout.setError("");
    }

    private void logCalculationFailure(String type) {
        analyticsManager.logEvent(AnalyticsEvent.eventName(EventName.CALCULATION_ERROR)
                .data(AdditionalData.CALCULATOR_TYPE, mCalculator.getType())
                .data(AdditionalData.CALCULATION_ERROR_TYPE, type).build());
    }

    private void logCalculationSuccess(String wageString, String hourString) {
        analyticsManager.logEvent(AnalyticsEvent.eventName(EventName.CALCULATION)
                .data(AdditionalData.CALCULATOR_TYPE, mCalculator.getType())
                .data(AdditionalData.SALARY, wageString)
                .data(AdditionalData.HOURS, hourString)
                .data(AdditionalData.OVERTIME_ON, SettingsUtil.isOvertime(getActivity()))
                .data(AdditionalData.OVERTIME_VALUE, SettingsUtil.getOvertimePay(getActivity()))
                .build());
    }
}
