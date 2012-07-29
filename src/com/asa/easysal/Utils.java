package com.asa.easysal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.view.View;

public class Utils {

	public static final String PREFERENCES = "easysal_prefs";
	public static final String PREFERENCES_THEME = "theme";

	/**
	 * Determines if the current device is Ice Cream sandwich or higher.
	 */
	public static boolean isICSorHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	/**
	 * Determines if the current device is Jelly Bean or higher.
	 */
	public static boolean isJBorHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	/**
	 * Determines if the current device is Honeycomb or higher.
	 */
	public static boolean isHoneycombOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/**
	 * Determines if the current device is Gingerbread or higher.
	 */
	public static boolean isGingerbreadOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	/**
	 * Tells if the device is a tablet or not.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static boolean isLandscape(Context context) {
		Configuration config = context.getResources().getConfiguration();
		return (config.orientation == Configuration.ORIENTATION_LANDSCAPE);
	}

	@TargetApi(9)
	public static void commitSharedPrefs(SharedPreferences.Editor editor) {
		if (isGingerbreadOrHigher())
			editor.apply();
		else
			editor.commit();
	}

	@TargetApi(16)
	public static void startActivity(Activity activity, Intent intent, View v,
			int startX, int startY) {
		if (isICSorHigher()) {
			ActivityOptions options = ActivityOptions.makeScaleUpAnimation(v,
					startX, startY, v.getWidth(), v.getHeight());
			activity.startActivity(intent, options.toBundle());
		} else {
			activity.startActivity(intent);
		}
	}

	@TargetApi(16)
	public static void startActivity(Activity activity, Intent intent, View v) {
		if (isICSorHigher()) {
			ActivityOptions options = ActivityOptions.makeScaleUpAnimation(v,
					0, 0, v.getWidth(), v.getHeight());
			activity.startActivity(intent, options.toBundle());
		} else {
			activity.startActivity(intent);
		}
	}
}
