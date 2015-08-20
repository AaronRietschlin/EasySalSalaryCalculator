package com.asa.easysal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.asa.easysal.analytics.AnalyticsHelper;
import com.asa.easysal.ui.PostHCPreferenceActivity;
import com.asa.easysal.ui.PreHCPreferenceActivity;
import com.asa.easysal.utils.NoticeUtils;

public class SettingsUtil {

    public static final String PREFERENCES_ABOUT = "aboutPref";
    public static final String PREFERENCES_HOMEPAGE = "homepagePref";
    public static final String PREFERENCES_OVERTIME = "overtime";
    public static final String PREFERENCES_OVERTIME_PAY = "overtimePay";
    public static final String PREFERENCES_LICENSES = "licenses";
    public static final String PREFERENCES_TAXES = "taxes";
    public static final String PREFERENCES_CLEAR_FIELDS = "clear_fields";

    private static SharedPreferences defaultPrefs;
    private static boolean isOvertime;

    private static boolean isOTSet;

    public static SharedPreferences getDefaultPreferences(Context context) {
        if (defaultPrefs == null) {
            defaultPrefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
        }
        return defaultPrefs;
    }

    public static void launchAbout(final Context context,
                                   Preference aboutPreference) {
        aboutPreference
                .setOnPreferenceClickListener(new OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {
                        AlertDialog.Builder aboutDialog = new AlertDialog.Builder(
                                context);

                        aboutDialog
                                .setMessage(R.string.about_content)
                                .setCancelable(true)
                                .setTitle(R.string.about_title)
                                .setPositiveButton("Close",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int id) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                        return true;
                    }

                });
    }

    public static void setOvertimeListener(final Context context, Preference checkBoxPreference) {
        checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue instanceof Boolean) {
                    if (((boolean) newValue)) {
                        AnalyticsHelper.sendOvertimeTurnedOnEvent(context);
                    } else {
                        AnalyticsHelper.sendOvertimeTurnedOffEvent(context);
                    }
                }
                return true;
            }
        });
    }

    public static void setOvertimeValueListener(final Context context, ListPreference preference) {
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue instanceof String) {
                    AnalyticsHelper.sendOvertimeValueChangedEvent(context, (String) newValue);
                }
                return true;
            }
        });
    }

    public static void setClearFieldsListener(final Context context, Preference preference) {
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue instanceof Boolean) {
                    boolean result = (boolean) newValue;
                    if (result) {
                        AnalyticsHelper.sendClearFieldsToggled(context, result);
                    } else {
                        AnalyticsHelper.sendClearFieldsToggled(context, result);
                    }
                }
                return true;
            }
        });
    }

    public static void launchHome(final Context context,
                                  Preference homepagePreference) {
        homepagePreference
                .setOnPreferenceClickListener(new OnPreferenceClickListener() {
                    // Launches homepage when clicked.
                    public boolean onPreferenceClick(Preference preference) {
                        // Sets the homepage to be launched as a new activity.
                        String url = "http://asandroid.blogspot.com/";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                        return true;
                    }
                });
    }

    public static void launchSettings(Context context) {
        Intent intent = null;
        if (Utils.isHoneycombOrHigher()) {
            intent = new Intent(context, PostHCPreferenceActivity.class);
        } else {
            intent = new Intent(context, PreHCPreferenceActivity.class);
        }
        context.startActivity(intent);
    }

    public static void openLicenses(final Context context, Preference licensePreference) {
        licensePreference
                .setOnPreferenceClickListener(new OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {
                        NoticeUtils.showLicenseDialog(context);
                        return true;
                    }

                });
    }

    public static boolean isOvertime(Context context) {
        getDefaultPreferences(context);
        return defaultPrefs.getBoolean(PREFERENCES_OVERTIME, false);
    }

    public static double getOvertimePay(Context context) {
        getDefaultPreferences(context);
        try {
            return Double.valueOf(defaultPrefs.getString(PREFERENCES_OVERTIME_PAY,
                    "1.5"));
        } catch (NumberFormatException e) {
            return 1.5;
        }
    }

    public static boolean shouldClearFields(@Nullable Context context) {
        SharedPreferences prefs = getDefaultPreferences(context);
        return prefs.getBoolean(PREFERENCES_CLEAR_FIELDS, false);
    }
}
