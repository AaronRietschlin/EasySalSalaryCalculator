package com.asa.easysal.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.asa.easysal.CalculationUtils;
import com.asa.easysal.R;
import com.asa.easysal.SettingsUtil;
import com.asa.easysal.Utils;
import com.asa.easysal.calculators.EsCalculator;
import com.asa.easysal.ui.EsHostActivity.PageChangedListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EsSalaryFragment extends Fragment implements EsCalculator.CalculatorCallback {
    public static final String TAG = EsSalaryFragment.class.getSimpleName();
    private static final String EXTRA_CALCULATOR = "calculator";

    private EsCalculator mCalculator;

    @Bind(R.id.main_wage_field)
    EditText mWageField;
    @Bind(R.id.main_wage_field_layout)
    TextInputLayout mWageInputLayout;

    @Bind(R.id.main_hours_field)
    EditText mHoursField;
    @Bind(R.id.main_hours_field_layout)
    TextInputLayout mHoursInputLayout;

    @Bind(R.id.main_ot_label)
    TextView overtimeTv;
    @Bind(R.id.button_calculate)
    Button calculateButton;

    // Not keeping the reset button. TODO - Keep this in case I bring it back.
    // private Button resetButton;

    protected PageChangedListener mPageChangedListener;
    protected EsHostActivityCompat mActivity;

    public static EsSalaryFragment newInstance(EsCalculator calculator) {
        EsSalaryFragment fragment = new EsSalaryFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_CALCULATOR, calculator);
        fragment.setArguments(args);
        return fragment;
    }

    public PageChangedListener getPageChangedListener() {
        return mPageChangedListener;
    }

    public void setPageChangedListener(PageChangedListener pageChangedListener) {
        this.mPageChangedListener = pageChangedListener;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (EsHostActivityCompat) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCalculator = args.getParcelable(EXTRA_CALCULATOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calculate_layout_md, container, false);
        ButterKnife.bind(this, v);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateClicked();
            }
        });

        mHoursInputLayout.setHint(getString(mCalculator.getHoursHintText()));
        mWageInputLayout.setHint(getString(mCalculator.getSalaryHintText()));

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

    protected void makeCalculation(int type) {
        // Check the entered wage/salary and don't let them proceed if wrong.
        String wageString = getWageString();
        if (!isStringValid(wageString)) {
            Utils.showCrouton(mActivity, R.string.error_no_salary_entered);
            return;
        }
        String hourString = getHoursString();
        if (!isStringValid(hourString)) {
            Utils.showCrouton(mActivity, R.string.error_no_hours_entered);
            return;
        }

        double[] params = CalculationUtils.convertStringsToDoubles(
                getWageString(), getHoursString());
        double[] results = CalculationUtils.performCalculation(mActivity, type,
                SettingsUtil.isOvertime(getActivity()), params);
        CalculateDialogFragment frag = CalculateDialogFragment.newInstance(
                R.string.results_dialog_title,
                R.layout.calculate_layout);
        frag.setResults(results);
        frag.show(mActivity.getSupportFragmentManager(), "calculation_result");
    }

    // OVERRIDE
    protected void configurationChanged() {
    }

    protected void calculateClicked() {
// Check the entered wage/salary and don't let them proceed if wrong.
        String wageString = getWageString();
        if (!isStringValid(wageString)) {
            Utils.showCrouton(mActivity, R.string.error_no_salary_entered);
            return;
        }
        String hourString = getHoursString();
        if (!isStringValid(hourString)) {
            Utils.showCrouton(mActivity, R.string.error_no_hours_entered);
            return;
        }

        double[] params = CalculationUtils.convertStringsToDoubles(
                getWageString(), getHoursString());
        mCalculator.performCalculation(params, this);
    }


    @Override
    public void success(double[] results) {
        CalculateDialogFragment frag = CalculateDialogFragment.newInstance(R.string.results_dialog_title,
                R.layout.calculate_layout);
        frag.setResults(results);
        frag.show(mActivity.getSupportFragmentManager(), "calculation_result");
    }

    @Override
    public void failure(@StringRes int errorResId) {

    }
}
