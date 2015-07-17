package com.asa.easysal.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.asa.easysal.R;
import com.asa.easysal.SettingsUtil;
import com.asa.easysal.calculators.HourlyCalculator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aaron on 7/14/15.
 */
public class EsHostActivityCompat extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.pager)
    ViewPager mPager;

    private EsPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        if (savedInstanceState != null) {
//            mPager.onRestoreInstanceState(savedInstanceState);
        }

        if (mPagerAdapter == null) {
            mPagerAdapter = new EsPagerAdapter(getSupportFragmentManager(), getApplicationContext());
            addFragmentsToPager();
            mPager.setAdapter(mPagerAdapter);
            mTabLayout.setupWithViewPager(mPager);
        }
    }

    private void addFragmentsToPager() {
        EsSalaryFragment frag = EsSalaryFragment.newInstance(new HourlyCalculator());
        mPagerAdapter.addTab(new EsPagerAdapter.TabInfo(this, frag, R.string.title_hourly));
        mPagerAdapter.addTab(new EsPagerAdapter.TabInfo(this, new FragmentPlaceholder(), R.string.title_daily));
        mPagerAdapter.addTab(new EsPagerAdapter.TabInfo(this, new FragmentPlaceholder(), R.string.title_weekly));
        mPagerAdapter.addTab(new EsPagerAdapter.TabInfo(this, new FragmentPlaceholder(), R.string.title_biweekly));
        mPagerAdapter.addTab(new EsPagerAdapter.TabInfo(this, new FragmentPlaceholder(), R.string.title_monthly));
        mPagerAdapter.addTab(new EsPagerAdapter.TabInfo(this, new FragmentPlaceholder(), R.string.title_yearly));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_easy_sal_salary_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    static class EsPagerAdapter extends FragmentPagerAdapter {

        protected List<TabInfo> tabs;
        protected Context context;

        public EsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int pos) {
            if (tabs == null) {
                throw new IllegalStateException("You must set the tabs for this PagerAdapter.");
            }
            TabInfo tab = tabs.get(pos);
            return tab.getFragment();
        }

        @Override
        public int getCount() {
            return tabs == null ? 0 : tabs.size();
        }

        @Override
        public CharSequence getPageTitle(int pos) {
            if (tabs == null) {
                return "";
            }
            TabInfo tab = tabs.get(pos);
            return tab.getTitle();
        }

        public List<TabInfo> getTabs() {
            return tabs;
        }

        public void setTabs(List<TabInfo> tabs) {
            this.tabs = tabs;
        }

        private void addTab(TabInfo tabInfo) {
            if (tabs == null) {
                tabs = new ArrayList<>();
            }
            tabs.add(tabInfo);
        }

        static class TabInfo {
            private Fragment fragment;
            private String title;

            public TabInfo(Context context, Fragment fragment, int titleId) {
                this.fragment = fragment;
                title = context.getString(titleId);
            }

            public Fragment getFragment() {
                return fragment;
            }

            public void setFragment(Fragment fragment) {
                this.fragment = fragment;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }


}
