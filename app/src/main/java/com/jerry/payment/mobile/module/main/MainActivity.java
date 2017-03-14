package com.jerry.payment.mobile.module.main;

import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.common.Env;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.module.discover.DiscoverFragment;
import com.jerry.payment.mobile.module.home.HomeFragment;
import com.jerry.payment.mobile.module.me.MeFragment;
import com.jerry.payment.mobile.module.service.ServiceFragment;
import com.jerry.payment.mobile.widget.NoSlidingViewPager;

/**
 * Main Page, contains 4 Tabs
 * Created by jerry on 2016/7/4.
 */
public class MainActivity extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private NoSlidingViewPager mViewPager;

    private String[] mTitles;

    private Fragment[] mFragments;

    private int[] icons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main_activity);
        initData();
        initView();
    }

    private void initData(){
        mTitles = new String[]{getString(R.string.app_tab_main)
                , getString(R.string.app_tab_service)
                , getString(R.string.app_tab_discover)
                , getString(R.string.app_tab_me)};

        mFragments = new Fragment[]{HomeFragment.newInstance()
                , ServiceFragment.newInstance()
                , DiscoverFragment.newInstance()
                , MeFragment.newInstance()
        };

        icons = new int[]{R.drawable.app_tab_main
                ,R.drawable.app_tab_service
                ,R.drawable.app_tab_find
                ,R.drawable.app_tab_me};
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        backIV.setVisibility(View.GONE);
        final TextView title = (TextView) findViewById(R.id.app_title_text);
        title.setText(getString(R.string.app_tab_main));

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (NoSlidingViewPager) findViewById(R.id.container);
        mViewPager.setNoSlide(false);
        mViewPager.setAdapter(mSectionsPagerAdapter);
//        mViewPager.setPageTransformer(true, new MyPageTransformer());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    title.setText(getString(R.string.app_tab_main));
                } else if (tab.getPosition() == 1) {
                    title.setText(getString(R.string.app_tab_service));
                } else if (tab.getPosition() == 2) {
                    title.setText(getString(R.string.app_tab_discover));
                } else if (tab.getPosition() == 3){
                    title.setText(getString(R.string.app_tab_me));
                }
                mViewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getTitleAndBottomPixels(title, tabLayout);
    }

    class MyPageTransformer implements ViewPager.PageTransformer {

        private static final float ROT_MAX = 20.0f;
        private float mRot;

        /**
         * @param view     滑动中的那个view
         */
        public void transformPage(View view, float position) {
            //界面不可见
            if (position < -1) {
//                view.setRotation(0);
                view.setAlpha(0);
        }
            //界面可见
            else if (position <= 1) {
//                mRot = (ROT_MAX * position);
//                view.setPivotX(view.getMeasuredWidth() * 0.5f);
//                view.setPivotY(view.getMeasuredHeight());
//                view.setRotation(mRot);
                view.setAlpha(1-Math.abs(position));
            }
            //界面不可见
            else {
//                view.setRotation(0);
                view.setAlpha(0);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
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
                case 3:
                    return mTitles[3];
            }
            return null;
        }
    }

    /**
     * get the Height of title and bottom
     */
    private void getTitleAndBottomPixels(final TextView title, final TabLayout tabLayout){

        final ViewTreeObserver titleTreeObserver = title.getViewTreeObserver();
        titleTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                title.getViewTreeObserver().removeOnPreDrawListener(this);
                Env.titleHeight = title.getMeasuredHeight();
                return false;
            }
        });

        final ViewTreeObserver tabTreeObserver = tabLayout.getViewTreeObserver();
        tabTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tabLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Env.tabHeight = tabLayout.getMeasuredHeight();
            }
        });
    }
}
