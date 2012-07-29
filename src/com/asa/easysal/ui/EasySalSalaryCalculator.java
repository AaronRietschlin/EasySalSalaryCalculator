package com.asa.easysal.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.asa.easysal.R;
import com.asa.easysal.Utils;

public class EasySalSalaryCalculator extends SherlockFragmentActivity implements
		ActionBar.TabListener, ViewPager.OnPageChangeListener,
		ActionBar.OnNavigationListener {

	private ViewPager mPager;
	private ActionBar mActionBar;
	private PagerTitleStrip mPagerTitleStrip;

	private ArrayAdapter<CharSequence> listAdapter;
	private SharedPreferences prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		prefs = getSharedPreferences(Utils.PREFERENCES, MODE_PRIVATE);
		setTheme(prefs.getInt(Utils.PREFERENCES_THEME, R.style.LightTheme));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);

		// Instatiate the ActionBar
		mActionBar = getSupportActionBar();

		// Set the Pagers data adapter
		mPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager()));
		mPager.setOnPageChangeListener(this);

		if (mPagerTitleStrip == null) {
			// This is a tablet in landscape mode.
			addTabs(0);
		} else {
			// Phone layout
			listAdapter = ArrayAdapter.createFromResource(this, R.array.titles,
					R.layout.spinner_text);
			listAdapter
					.setDropDownViewResource(R.layout.spinner_dropdown_text);
			mActionBar.setListNavigationCallbacks(listAdapter, this);
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
			if (Utils.isLandscape(this)) {
				// This means we are in landscape
				mPagerTitleStrip.setVisibility(View.GONE);
			} else {
				// This means we are in portrait
			}

		}
	}

	/**
	 * Adds the tabs to the ActionBar.
	 */
	private void addTabs(int flag) {
		if (flag == 0)
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		else
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		mActionBar.addTab(mActionBar.newTab().setText(R.string.title_hourly)
				.setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText(R.string.title_daily)
				.setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText(R.string.title_weekly)
				.setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText(R.string.title_biweekly)
				.setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText(R.string.title_monthly)
				.setTabListener(this));
		mActionBar.addTab(mActionBar.newTab().setText(R.string.title_yearly)
				.setTabListener(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(
				R.menu.activity_easy_sal_salary_calculator, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_theme_dark:
			changeTheme(R.style.DarkTheme);
			return true;
		case R.id.menu_theme_light:
			changeTheme(R.style.LightTheme);
			return true;
		case R.id.menu_theme_green:
			changeTheme(R.style.GreenAppTheme);
			return true;
		}
		return false;
	}

	private void changeTheme(int themeId) {
		Utils.commitSharedPrefs(prefs.edit().putInt(Utils.PREFERENCES_THEME,
				themeId));
		Intent intent = new Intent(this, EasySalSalaryCalculator.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	private class TabsPagerAdapter extends FragmentPagerAdapter {
		public TabsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return (new EasySalHourly());
			case 1:
				return (new EasySalDaily());
			case 2:
				return (new EasySalWeekly());
			case 3:
				return (new EasySalBiweekly());
			case 4:
				return (new EasySalMonthly());
			case 5:
				return (new EasySalYearly());
			}
			return null;
		}

		@Override
		public int getCount() {
			return 6;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_hourly).toUpperCase();
			case 1:
				return getString(R.string.title_daily).toUpperCase();
			case 2:
				return getString(R.string.title_weekly).toUpperCase();
			case 3:
				return getString(R.string.title_biweekly).toUpperCase();
			case 4:
				return getString(R.string.title_monthly).toUpperCase();
			case 5:
				return getString(R.string.title_yearly).toUpperCase();
			}
			return null;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		// Set the selected item
		getSupportActionBar().setSelectedNavigationItem(position);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		mPager.setCurrentItem(itemPosition);
		return true;
	}

}
