package com.jerry.payment.mobile.module.service;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;

/**
 * 限额Activity
 * Created by jerry on 2016/7/22.
 */
public class LimitActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_limit_activity);
        initView();
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        WebView webView = (WebView) findViewById(R.id.limit_webview);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        titleTV.setText(getString(R.string.me_top_up_limit));

        webView.loadUrl("http://www.globebill.com.cn/contact/");
    }
}
