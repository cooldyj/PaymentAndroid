package com.jerry.payment.mobile.module.service;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.IntentUtils;
import com.jerry.payment.mobile.utils.SoftInputUtils;

/**
 * 转账第一步Activity
 * Created by jerry on 2016/7/22.
 */
public class TransferFirstActivity extends BaseActivity {

    private EditText accountET;
    private TextView submitTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_transfer_first_activity);
        initView();
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        accountET = (EditText) findViewById(R.id.transfer_account);
        submitTV = (TextView) findViewById(R.id.transfer_submit);

        accountET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == R.id.transfer_next || actionId == EditorInfo.IME_ACTION_DONE){
                    goNext();
                    return true;
                }
                return false;
            }
        });

        backIV.setOnClickListener(clickListener);
        titleTV.setText(getString(R.string.service_transfer));
        submitTV.setOnClickListener(clickListener);
    }

    private void goNext(){
        SoftInputUtils.closedSoftInput(TransferFirstActivity.this);
        IntentUtils.startActivity(TransferFirstActivity.this, TransferRequestActivity.class);
        TransferFirstActivity.this.finish();

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_title_back:
                    onBackPressed();
                    break;
                case R.id.transfer_submit:
                    goNext();
                    break;
            }
        }
    };
}
