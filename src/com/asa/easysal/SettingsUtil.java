package com.asa.easysal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;

import com.asa.easysal.ui.PostHCPreferenceActivity;
import com.asa.easysal.ui.PreHCPreferenceActivity;

public class SettingsUtil {

	public static final String PREFERENCES_ABOUT = "aboutPref";
	public static final String PREFERENCES_HOMEPAGE = "homepagePref";
	public static final String PREFERENCES_OVERTIME = "overtime";
	public static final String PREFERENCES_OVERTIME_PAY = "overtimePay";
	public static final String PREFERENCES_TAXES = "taxes";

	private static SharedPreferences defaultPrefs;

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
}
