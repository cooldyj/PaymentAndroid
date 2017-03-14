package com.jerry.payment.mobile.widget;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jerry.payment.mobile.R;

/**
 * Common widget of list item
 * Created by jerry on 2016/7/4.
 */
public class ListItem extends LinearLayout {

    private ImageView iconIV;
    private TextView textTV;
    private RelativeLayout itemRL;

    public ListItem(Context context) {
        super(context);
        ((Activity)context).getLayoutInflater().inflate(R.layout.app_custom_list_item, this);
        initView();
    }

    private void initView(){
        iconIV = (ImageView) findViewById(R.id.list_item_img);
        textTV = (TextView) findViewById(R.id.list_item_text);
        itemRL = (RelativeLayout) findViewById(R.id.list_item_rl);
    }

    public ImageView getIconIV() {
        return iconIV;
    }

    public TextView getTextTV() {
        return textTV;
    }

    public RelativeLayout getItemRL() {
        return itemRL;
    }
}
