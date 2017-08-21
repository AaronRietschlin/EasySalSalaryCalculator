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
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asa.easysal.BuildConfig;
import com.asa.easysal.CalculationUtils;
import com.asa.easysal.R;
import com.asa.easysal.SettingsUtil;
import com.asa.easysal.analytics.AnalyticsContants;
import com.asa.easysal.analytics.AnalyticsEvent;
import com.asa.easysal.analytics.AnalyticsHelper;
import com.asa.easysal.analytics.AnalyticsManager;
import com.asa.easysal.analytics.enums.AdditionalData;
import com.asa.easysal.analytics.enums.EventName;
import com.asa.easysal.calculators.BiWeeklyCalculator;
import com.asa.easysal.calculators.DailyCalculator;
import com.asa.easysal.calculators.HourlyCalculator;
import com.asa.easysal.calculators.MonthlyCalculator;
import com.asa.easysal.calculators.WeeklyCalculator;
import com.asa.easysal.calculators.YearlyCalculator;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.asa.easysal.BuildConfig.AD_UNIT_ID;
import static com.asa.easysal.analytics.enums.AdditionalData.CLEAR_FIELDS_VALUE;
import static com.asa.easysal.analytics.enums.AdditionalData.MENU_CLICK;
import static com.asa.easysal.analytics.enums.AdditionalData.MENU_CLICK_HELP;
import static com.asa.easysal.analytics.enums.AdditionalData.MENU_CLICK_SETTINGS;
import static com.asa.easysal.analytics.enums.EventName.CALCULATE_AGAIN_CLICKED;
import static com.google.android.gms.ads.AdSize.BANNER;

public class EsHostActivityCompat extends AppCompatActivity implements EsSalaryFragment.CalculationListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.root)
    ViewGroup root;

    private EsPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupAds();

        AdditionalData.CALCULATOR_TYPE.toString();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        if (pagerAdapter == null) {
            pagerAdapter = new EsPagerAdapter(getSupportFragmentManager(), getApplicationContext());
            addFragmentsToPager();
            pager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(pager);
        }

        setupPagerListener();
    }

    private void addFragmentsToPager() {
        EsSalaryFragment frag = EsSalaryFragment.newInstance(new HourlyCalculator());
        pagerAdapter.addTab(new EsPagerAdapter.TabInfo(this, frag, R.string.title_hourly));
        frag = EsSalaryFragment.newInstance(new DailyCalculator());
        pagerAdapter.addTab(new EsPagerAdapter.TabInfo(this, frag, R.string.title_daily));
        frag = EsSalaryFragment.newInstance(new WeeklyCalculator());
        pagerAdapter.addTab(new EsPagerAdapter.TabInfo(this, frag, R.string.title_weekly));
        frag = EsSalaryFragment.newInstance(new BiWeeklyCalculator());
        pagerAdapter.addTab(new EsPagerAdapter.TabInfo(this, frag, R.string.title_biweekly));
        frag = EsSalaryFragment.newInstance(new MonthlyCalculator());
        pagerAdapter.addTab(new EsPagerAdapter.TabInfo(this, frag, R.string.title_monthly));
        frag = EsSalaryFragment.newInstance(new YearlyCalculator());
        pagerAdapter.addTab(new EsPagerAdapter.TabInfo(this, frag, R.string.title_yearly));
    }

    private void setupAds() {
        final AdView adView = new AdView(this);
        // TODO - Add dagger and inject the ad unit id?
        adView.setAdUnitId(AD_UNIT_ID);
        adView.setAdSize(BANNER);
        root.addView(adView, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        // TODO - check if ads enabled.
        if (!BuildConfig.SHOW_ADS) {
            return;
        }
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void setupPagerListener() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case CalculationUtils.TYPE_HOURLY:
                        AnalyticsHelper.sendScreenView(getApplicationContext(),
                                AnalyticsContants.SCREEN_HOURLY_CALCULATE_SCREEN);
                        break;
                    case CalculationUtils.TYPE_DAILY:
                        AnalyticsHelper.sendScreenView(getApplicationContext(),
                                AnalyticsContants.SCREEN_DAILY_CALCULATE_SCREEN);
                        break;
                    case CalculationUtils.TYPE_WEEKLY:
                        AnalyticsHelper.sendScreenView(getApplicationContext(),
                                AnalyticsContants.SCREEN_WEEKLY_CALCULATE_SCREEN);
                        break;
                    case CalculationUtils.TYPE_BIWEEKLY:
                        AnalyticsHelper.sendScreenView(getApplicationContext(),
                                AnalyticsContants.SCREEN_BIWEEKLY_CALCULATE_SCREEN);
                        break;
                    case CalculationUtils.TYPE_MONTHLY:
                        AnalyticsHelper.sendScreenView(getApplicationContext(),
                                AnalyticsContants.SCREEN_MONTHLY_CALCULATE_SCREEN);
                        break;
                    case CalculationUtils.TYPE_YEARLY:
                        AnalyticsHelper.sendScreenView(getApplicationContext(),
                                AnalyticsContants.SCREEN_YEARLY_CALCULATE_SCREEN);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
                logSettings(MENU_CLICK_SETTINGS);
                AlertDialogFragment fragment = AlertDialogFragment.newInstance(
                        R.string.help_title, R.string.help_content,
                        R.string.button_dismiss);
                fragment.show(getSupportFragmentManager(), "help");
                return true;
            case R.id.menu_settings:
                logSettings(MENU_CLICK_HELP);
                SettingsUtil.launchSettings(this);
                return true;
        }
        return false;
    }

    private void logSettings(AdditionalData menuClickSettings) {
        AnalyticsManager.getInstance().logEvent(AnalyticsEvent.eventName(EventName.MENU_CLICK)
                .data(MENU_CLICK, menuClickSettings).build());
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

        public EsSalaryFragment getSalaryFragment(int pos) {
            return (EsSalaryFragment) getItem(pos);
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

    @Override
    public void onCalculationResults(double[] results) {
        ResultsBottomSheetFragment bottomSheetFragment = ResultsBottomSheetFragment.newInstance(results);
        bottomSheetFragment.show(getSupportFragmentManager(), ResultsBottomSheetFragment.TAG);
    }

    @Override
    public void onCalculateAgain() {
        boolean reset = SettingsUtil.shouldClearFields(getApplicationContext());
        int fragmentPosition = pager.getCurrentItem();
        Timber.d("Calculating again for position: %d; Reset? %b", fragmentPosition, reset);
        EsSalaryFragment fragment = pagerAdapter.getSalaryFragment(pager.getCurrentItem());
        fragment.calculateAgain(reset);
    }


}
