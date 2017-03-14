package com.jerry.payment.mobile.module.discover;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.module.base.BaseWebView;

/**
 * 帮助Activity
 * Created by jerry on 2016/7/18.
 */
public class HelpActivity extends BaseActivity {

    private BaseWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_help_activity);
        initView();
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        webView = (BaseWebView) findViewById(R.id.help_web_view);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleTV.setText(getString(R.string.discover_help));
    }
}
