package com.jerry.payment.mobile.module.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseFragment;

/**
 * 新用户指引页的某一页Fragment
 * Created by jerry on 2016/7/14.
 */
public class AppGuideFragment extends BaseFragment {

    private int imgResource;

    public static AppGuideFragment newInstance(int imgResource) {
        Bundle data = new Bundle();
        data.putInt("imgResource", imgResource);
        AppGuideFragment fragment = new AppGuideFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        imgResource = data.getInt("imgResource");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_guide_item, null);
        ImageView img = (ImageView) view.findViewById(R.id.app_guide_img);
        img.setImageResource(imgResource);
        return view;
    }
}
