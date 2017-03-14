package com.jerry.payment.mobile.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;

/**
 * Base List Activity
 * Created by jerry on 2016/7/12.
 */
public class ListItemActivity extends BaseActivity {

    protected LinearLayout rootLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list_activity);
        initView();
    }

    private void initView(){
        rootLL = (LinearLayout) findViewById(R.id.list_fragment_ll);
    }

    protected void addItem(int imgResId, String text, View.OnClickListener onClickListener){
        ListItem listItem = new ListItem(ListItemActivity.this);
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
