package com.asa.easysal.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import com.asa.easysal.R;
import com.asa.easysal.SettingsUtil;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PreferencesFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        SettingsUtil.launchAbout(getActivity(),
                findPreference(SettingsUtil.PREFERENCES_ABOUT));
        SettingsUtil.launchHome(getActivity(),
                findPreference(SettingsUtil.PREFERENCES_HOMEPAGE));
        SettingsUtil.setOvertimeListener(getActivity(), findPreference(SettingsUtil.PREFERENCES_OVERTIME));
        SettingsUtil.setOvertimeValueListener(getActivity(), (ListPreference) findPreference(SettingsUtil
                .PREFERENCES_OVERTIME_PAY));
        SettingsUtil.openLicenses(getActivity(), findPreference(SettingsUtil.PREFERENCES_LICENSES));
        SettingsUtil.setClearFieldsListener(getActivity(), findPreference(SettingsUtil.PREFERENCES_CLEAR_FIELDS));
    }
}
