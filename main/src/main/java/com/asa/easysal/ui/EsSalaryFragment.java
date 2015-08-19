package com.asa.easysal.ui;

import android.content.Context;
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
import com.asa.easysal.SnackUtils;
import com.asa.easysal.Utils;
import com.asa.easysal.analytics.AnalyticsHelper;
import com.asa.easysal.calculators.EsCalculator;
import com.asa.easysal.widget.CancelEditText2;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EsSalaryFragment extends Fragment implements EsCalculator.CalculatorCallback {
    public static final String TAG = EsSalaryFragment.class.getSimpleName();
    private static final String EXTRA_CALCULATOR = "calculator";

    private EsCalculator mCalculator;

    @Bind(R.id.salary_field_wage)
    CancelEditText2 mFieldWage;
    @Bind(R.id.salary_field_wage_hours)
    CancelEditText2 mFieldHours;

    // Not keeping the reset button. TODO - Keep this in case I bring it back.
    // private Button resetButton;

    protected EsHostActivityCompat mActivity;

    public static EsSalaryFragment newInstance(EsCalculator calculator) {
        EsSalaryFragment fragment = new EsSalaryFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_CALCULATOR, calculator);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context activity) {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calculate_layout, container, false);
        ButterKnife.bind(this, v);

        mFieldHours.getTextInputLayout().setHint(getString(mCalculator.getHoursHintText()));
        mFieldWage.getTextInputLayout().setHint(getString(mCalculator.getSalaryHintText()));
        mFieldHours.getTextInputLayout().setErrorEnabled(true);
        mFieldWage.getTextInputLayout().setErrorEnabled(true);

        CancelEditText2 text = (CancelEditText2) v.findViewById(R.id.test);
        text.getTextInputLayout().setErrorEnabled(true);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!Utils.isHoneycombOrHigher()) {
            mFieldHours.getEditText().setTextColor(Color.BLACK);
            mFieldWage.getEditText().setTextColor(Color.BLACK);
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
        return mFieldWage.getEditText().getText().toString().trim();
    }

    protected String getHoursString() {
        return mFieldHours.getEditText().getText().toString().trim();
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
        // Reset the errors:
        mFieldWage.getTextInputLayout().setError(" ");
        mFieldHours.getTextInputLayout().setError(" ");

        // Check the entered wage/salary and don't let them proceed if wrong.
        String wageString = getWageString();
        if (!isStringValid(wageString)) {
            mFieldWage.getTextInputLayout().setError(getString(R.string.error_no_salary_entered));
            Utils.performShakeOnView(mActivity, mFieldWage);
            return;
        }
        String hourString = getHoursString();
        if (!isStringValid(hourString)) {
            mFieldHours.getTextInputLayout().setError(getString(R.string.error_no_hours_entered));
            Utils.performShakeOnView(mActivity, mFieldHours);
            return;
        }

        double[] params = CalculationUtils.convertStringsToDoubles(
                getWageString(), getHoursString());
        mCalculator.performCalculation(mActivity.getApplicationContext(), params, this);
        mCalculator.sendAnalyticsCalculateClickedEvent(mActivity.getApplicationContext());
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
        frag.show(mActivity.getSupportFragmentManager(), "calculation_result");
    }

    @Override
    public void failure(@StringRes int errorResId) {

    }
}
