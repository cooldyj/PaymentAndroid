package com.jerry.payment.mobile.module.discover;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;

/**
 * Feed back page
 * Created by jerry on 2016/7/8.
 */
public class FeedBackActivity extends BaseActivity{

    private EditText submitET;
    private TextView submitTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_feedback_activity);
        initView();
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        submitET = (EditText) findViewById(R.id.feed_back_input);
        submitTV = (TextView) findViewById(R.id.feed_back_submit);

        titleTV.setText(getString(R.string.discover_feedback));

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
