package com.asa.easysal.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.asa.easysal.analytics.AnalyticsContants;
import com.asa.easysal.analytics.AnalyticsHelper;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class PostHCPreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Since this is onjly going to be show on API 11+, we don't need to use
        // ABS.
        ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment())
                .commit();

        AnalyticsHelper.sendScreenView(this, AnalyticsContants.SCREEN_SETTINGS_SCREEN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

}
