package com.jerry.payment.mobile.module.service;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.IntentUtils;

/**
 * 交易记录Activity
 * Created by jerry on 2016/7/25.
 */
public class TransactionActivity extends BaseActivity {

    private MyPagerAdapter pagerAdapter;
    private ViewPager mViewPager;

    private String[] mTitles;
    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_activity);
        initData();
        initView();
    }

    private void initData(){
        mTitles = new String[]{getString(R.string.service_all)
                , getString(R.string.service_outflow)
                , getString(R.string.service_inflow)};

        mFragments = new Fragment[]{TransactionAllFragment.newInstance()
                , TransactionOutFragment2.newInstance()
                , TransactionInFragment.newInstance()
        };
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView title = (TextView) findViewById(R.id.app_title_text);
        ImageView calendarIV = (ImageView) findViewById(R.id.transaction_calendar);

        backIV.setOnClickListener(clickListener);
        title.setText(getString(R.string.home_transactions));
        calendarIV.setOnClickListener(clickListener);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.transaction_container);
        mViewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.transaction_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_title_back:
                    onBackPressed();
                    break;
                case R.id.transaction_calendar:
                    IntentUtils.startActivity(TransactionActivity.this, CalendarActivity.class);
                    overridePendingTransition(R.anim.pop_bottom_in, R.anim.fake_translate);
                    break;
            }

        }
    };

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mTitles[0];
                case 1:
                    return mTitles[1];
                case 2:
                    return mTitles[2];
            }
            return null;
        }
    }
}
