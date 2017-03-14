package com.jerry.payment.mobile.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseFragment;

/**
 * Base list fragment for Service, Discover, Me, etc.
 * Created by jerry on 2016/7/4.
 */
public class ListItemFragment extends BaseFragment {
    protected View rootView;
    protected LinearLayout rootLL;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.app_list_fragment, null);
        initView(rootView);
        return rootView;
    }

    private void initView(View view){
        rootLL = (LinearLayout) view.findViewById(R.id.list_fragment_ll);
    }

    protected void addItem(int imgResId, String text, View.OnClickListener onClickListener){
        ListItem listItem = new ListItem(mContext);
        listItem.getIconIV().setImageResource(imgResId);
        listItem.getTextTV().setText(text);
        listItem.getItemRL().setOnClickListener(onClickListener);
        rootLL.addView(listItem);
    }

    protected void addItem(View view, View.OnClickListener onClickListener){
        view.setOnClickListener(onClickListener);
        rootLL.addView(view);
    }
}
