package com.jerry.payment.mobile.module.service;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;

/**
 * 交易记录详情Activity
 * Created by jerry on 2016/7/26.
 */
public class TransactionDetailActivity extends BaseActivity {

    private TextView amountTV;
    private String amountStr = "500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_detail_activity);
        initView();
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        amountTV = (TextView) findViewById(R.id.transaction_amount_text);

        backIV.setOnClickListener(clickListener);
        titleTV.setText(getString(R.string.service_transaction_detail));

        amountTV.setText(amountStr);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_title_back:
                    onBackPressed();
                    break;
            }
        }
    };
}
