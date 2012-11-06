package com.asa.easysal.ui;

import com.asa.easysal.SettingsUtil;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class PostHCPreferenceActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new PreferencesFragment())
				.commit();
	}

}
