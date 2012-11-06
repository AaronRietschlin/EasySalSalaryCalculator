package com.asa.easysal.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.asa.easysal.R;
import com.asa.easysal.SettingsUtil;
import com.asa.easysal.Utils;

public class EasySalSalaryCalculator extends SherlockFragmentActivity implements
		ActionBar.TabListener, ViewPager.OnPageChangeListener,
		ActionBar.OnNavigationListener {

	private ViewPager mPager;
	private ActionBar mActionBar;
	private PagerTitleStrip mPagerTitleStrip;
	private TabsPagerAdapter mAdapter;

	private ArrayAdapter<CharSequence> listAdapter;
	private SharedPreferences prefs;

	private ButtonClickListener mButtonClickListener;

	private Button resetButton;
	private Button calculateButton;

	public interface ButtonClickListener {
		public abstract void calculateButtonClicked();

		public abstract void resetButtonClicked();
	}

	public interface PageChangedListener {
		public abstract void pageChanged();
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		prefs = getSharedPreferences(Utils.PREFERENCES, MODE_PRIVATE);
		setTheme(prefs.getInt(Utils.PREFERENCES_THEME, R.style.LightTheme));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);

		// Instatiate the ActionBar
		mActionBar = getSupportActionBar();

		// Set the Pagers data adapter
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		mAdapter.addFragment(new EasySalHourly());
		mAdapter.addFragment(new EasySalDaily());
		mAdapter.addFragment(new EasySalWeekly());
		mAdapter.addFragment(new EasySalBiweekly());
		mAdapter.addFragment(new EasySalMonthly());
		mAdapter.addFragment(new EasySalYearly());
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(this);

		if (mPagerTitleStrip == null) {
			// This is a tablet in landscape mode. (Tablet portrait is the only
			// layout that doesn't have the pagertitle)
			addTabs(0);
		} else {
			// Phone layout
			listAdapter = ArrayAdapter.createFromResource(this, R.array.titles,
					R.layout.spinner_text);
			listAdapter.setDropDownViewResource(R.layout.spinner_dropdown_text);
			mActionBar.setListNavigationCallbacks(listAdapter, this);
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
			if (Utils.isLandscape(this)) {
				// This means we are in landscape
				mPagerTitleStrip.setVisibility(View.GONE);
			} else {
				// This means we are in portrait
			}
		}

		calculateButton = (Button) findViewById(R.id.button_calculate);
		resetButton = (Button) findViewById(R.id.button_reset);
		calculateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mButtonClickListener.calculateButtonClicked();
			}
		});
		resetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mButtonClickListener.resetButtonClicked();
			}
		});

		if (savedInstanceState != null) {
			// This means that the configuration changed. Need to restore
			// fragment.
			calculateButton.postDelayed(new Runnable() {

				@Override
				public void run() {
					BaseFragment fragment = mAdapter.getItem(savedInstanceState
							.getInt("current_item"));
					fragment.configurationChanged();
				}
			}, 1000);
		}

	}

	@Override
	public void onResume() {
		super.onResume();
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
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("current_item", mPager.getCurrentItem());
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
			changeTheme(R.style.Theme_money);
			return true;
		case R.id.menu_help:
			AlertDialogFragment fragment = AlertDialogFragment.newInstance(
					R.string.help_title, R.string.help_content,
					R.string.button_dismiss);
			fragment.show(getSupportFragmentManager(), "help");
			return true;
		case R.id.menu_settings:
			SettingsUtil.launchSettings(this);
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
		private List<BaseFragment> fragments;

		public TabsPagerAdapter(FragmentManager fm) {
			super(fm);
			fragments = new ArrayList<BaseFragment>();
		}

		public void addFragment(BaseFragment frag) {
			fragments.add(frag);
		}

		@Override
		public BaseFragment getItem(int position) {
			// switch (position) {
			// case 0:
			// return (new EasySalHourly());
			// case 1:
			// return (new EasySalDaily());
			// case 2:
			// return (new EasySalWeekly());
			// case 3:
			// return (new EasySalBiweekly());
			// case 4:
			// return (new EasySalMonthly());
			// case 5:
			// return (new EasySalYearly());
			// }

			return fragments.get(position);
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

		// Inform the current fragment that the page was changed.
		pageChanged(position);
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

	public ButtonClickListener getButtonClickListener() {
		return mButtonClickListener;
	}

	public void setButtonClickListener(ButtonClickListener buttonClickListener) {
		this.mButtonClickListener = buttonClickListener;
	}

	public void pageChanged(int position) {
		TabsPagerAdapter adapter = (TabsPagerAdapter) mPager.getAdapter();
		BaseFragment fragment = adapter.getItem(position);
		if (fragment instanceof EasySalHourly) {
			EasySalHourly frag = (EasySalHourly) fragment;
			frag.getActivity();
		}
		PageChangedListener listener = fragment.getPageChangedListener();
		if (listener != null)
			fragment.getPageChangedListener().pageChanged();
	}

}
