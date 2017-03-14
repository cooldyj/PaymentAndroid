package com.jerry.payment.mobile.module.launcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.module.main.MainActivity;
import com.jerry.payment.mobile.utils.IntentUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * 新用户指引页
 * Created by jerry on 2016/7/14.
 */
public class AppGuideActivity extends BaseActivity {

    private ViewPager viewPager;
    private GuideAdapter adapter;
    private LinearLayout indicator;
    private List<Integer> imgList;

    private EdgeEffectCompat rightEdge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_guide_activity);
        initData();
        initView();
    }

    private void initData(){
        imgList = new LinkedList<>();
        imgList.add(R.drawable.app_guide_1);
        imgList.add(R.drawable.app_guide_2);
        imgList.add(R.drawable.app_guide_3);
        adapter = new GuideAdapter(getSupportFragmentManager());
    }

    private void initView(){
        viewPager = (ViewPager) findViewById(R.id.app_guide_viewpager);
        indicator = (LinearLayout) findViewById(R.id.app_guide_indicator);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        try {
            Field leftEdgeField = viewPager.getClass().getDeclaredField("mLeftEdge");
            Field rightEdgeField = viewPager.getClass().getDeclaredField("mRightEdge");
            if(leftEdgeField != null && rightEdgeField != null){
                leftEdgeField.setAccessible(true);
                rightEdgeField.setAccessible(true);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(viewPager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView dot;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(10);
        params.setMarginEnd(10);
        for (int i = 0; i < imgList.size(); i++){
            dot = new ImageView(AppGuideActivity.this);
            dot.setLayoutParams(params);
            dot.setImageResource(R.drawable.app_guide_dot_bg);
            dot.setTag(i);
            final int j = i;
            dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(j);
                }
            });
            indicator.addView(dot);
        }
        setSelectedDot(0);
    }

    private void setSelectedDot(int position){
        if(null != indicator && indicator.getChildCount() > 0){
            int len = indicator.getChildCount();
            ImageView dot;
            while (len -- > 0){
                dot = (ImageView) indicator.getChildAt(len);
                if(position == len){
                    dot.setEnabled(false);
                } else {
                    dot.setEnabled(true);
                }
            }
        }
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setSelectedDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(rightEdge != null && !rightEdge.isFinished()){//到了最后一张并且还继续拖动，出现蓝色限制边条了
                IntentUtils.startActivity(AppGuideActivity.this, MainActivity.class);
            }
        }
    };

    class GuideAdapter extends FragmentPagerAdapter{

        public GuideAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return AppGuideFragment.newInstance(imgList.get(position));
        }

        @Override
        public int getCount() {
            return imgList.size();
        }
    }

}
