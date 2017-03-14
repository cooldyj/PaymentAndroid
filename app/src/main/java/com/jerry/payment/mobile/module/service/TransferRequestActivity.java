package com.jerry.payment.mobile.module.service;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.IntentUtils;
import com.jerry.payment.mobile.utils.SoftInputUtils;

/**
 * 转账请求Activity
 * Created by jerry on 2016/7/22.
 */
public class TransferRequestActivity extends BaseActivity {

    private EditText currencyET;
    private EditText amountET;
    private EditText remarkET;
    private TextView confirmTV;
    private TextView cancelTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tansfer_request_activity);
        initView();
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        TextView limitTV = (TextView) findViewById(R.id.app_title_action);

        currencyET = (EditText) findViewById(R.id.request_currency);
        amountET = (EditText) findViewById(R.id.request_amount);
        remarkET = (EditText) findViewById(R.id.request_remark);
        confirmTV = (TextView) findViewById(R.id.request_submit);
        cancelTV = (TextView) findViewById(R.id.request_cancel);

        limitTV.setText(getString(R.string.me_top_up_limit));
        limitTV.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        limitTV.getPaint().setAntiAlias(true);
        limitTV.setVisibility(View.VISIBLE);

        titleTV.setText(getString(R.string.service_transfer));

        currencyET.setInputType(InputType.TYPE_NULL);

        backIV.setOnClickListener(clickListener);
        limitTV.setOnClickListener(clickListener);
        currencyET.setOnClickListener(clickListener);
        confirmTV.setOnClickListener(clickListener);
        cancelTV.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_title_back:
                    onBackPressed();
                    break;
                case R.id.app_title_action:
                    IntentUtils.startActivity(TransferRequestActivity.this, LimitActivity.class);
                    break;
                case R.id.request_submit:
                    attemptSubmit();
                    break;
                case R.id.request_currency:
                    break;
                case R.id.request_cancel:
                    break;
            }
        }
    };

    private void attemptSubmit(){
        SoftInputUtils.closedSoftInput(TransferRequestActivity.this);
    }
}
