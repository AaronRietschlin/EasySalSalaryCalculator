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
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import de.neofonie.mobile.app.android.widget.crouton.Crouton;
import de.neofonie.mobile.app.android.widget.crouton.Style;

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

	/**
	 * Tells whether or not the device is in landscape mode.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isLandscape(Context context) {
		Configuration config = context.getResources().getConfiguration();
		return (config.orientation == Configuration.ORIENTATION_LANDSCAPE);
	}

	/**
	 * Commits to the sharedpreferences. This determines if the device is
	 * gingerbread or above. If it is, it uses
	 * {@link SharedPreferences.Editor#apply()} rather than
	 * {@link SharedPreferences.Editor#commit()}
	 * 
	 * @param editor
	 */
	@TargetApi(9)
	public static void commitSharedPrefs(SharedPreferences.Editor editor) {
		if (isGingerbreadOrHigher())
			editor.apply();
		else
			editor.commit();
	}

	/**
	 * Starts a new activity with the given intent. If the device is JellyBean
	 * or above, it uses the new activity with options to perform an animation.
	 * 
	 * @param activity
	 *            The activity context
	 * @param intent
	 *            The intent to launch
	 * @param v
	 *            The view to start the animation from (the anchor)
	 * @param startX
	 *            The starting x position
	 * @param startY
	 *            The starting y position
	 */
	@TargetApi(16)
	public static void startActivity(Activity activity, Intent intent, View v,
			int startX, int startY) {
		if (isJBorHigher()) {
			ActivityOptions options = ActivityOptions.makeScaleUpAnimation(v,
					startX, startY, v.getWidth(), v.getHeight());
			activity.startActivity(intent, options.toBundle());
		} else {
			activity.startActivity(intent);
		}
	}

	/**
	 * Starts a new activity with the given intent. If the device is JellyBean
	 * or above, it uses the new activity with options to perform an animation.
	 * 
	 * @param activity
	 *            The activity context
	 * @param intent
	 *            The intent to launch
	 * @param v
	 *            The view to start the animation from (the anchor)
	 */
	@TargetApi(16)
	public static void startActivity(Activity activity, Intent intent, View v) {
		if (isJBorHigher()) {
			ActivityOptions options = ActivityOptions.makeScaleUpAnimation(v,
					0, 0, v.getWidth(), v.getHeight());
			activity.startActivity(intent, options.toBundle());
		} else {
			activity.startActivity(intent);
		}
	}

	/**
	 * Clears the text in all of the EditTexts passed in.
	 * 
	 * @param et
	 *            An array of EditTexts
	 */
	public static void clearEditTexts(EditText... et) {
		if (et == null || et.length == 0)
			return;
		for (int i = 0; i < et.length; i++) {
			EditText editText = et[i];
			if (editText != null)
				et[i].setText("");
		}
	}

	public static void makeToast(Context context, int messageId) {
		Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
	}
	
	public static void showCrouton(SherlockFragmentActivity context, int messageId){
		Crouton.makeText(context, messageId, Style.ALERT);
	}
}
