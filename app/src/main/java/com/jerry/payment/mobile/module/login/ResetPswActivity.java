package com.jerry.payment.mobile.module.login;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.SoftInputUtils;

/**
 * 重设密码Activity
 * Created by jerry on 2016/7/26.
 */
public class ResetPswActivity extends BaseActivity {

    private EditText pswET;
    private EditText reEnterPswET;
    private TextView submitTV;
    private TextView cancelTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_reset_psw_activity);
        initView();
    }

    private void initView() {
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        pswET = (EditText) findViewById(R.id.reset_psw);
        reEnterPswET = (EditText) findViewById(R.id.reset_re_psw);
        submitTV = (TextView) findViewById(R.id.reset_submit);
        cancelTV = (TextView) findViewById(R.id.reset_cancel);

        backIV.setOnClickListener(clickListener);
        submitTV.setOnClickListener(clickListener);
        cancelTV.setOnClickListener(clickListener);
        titleTV.setText(getString(R.string.me_reset_psw));

        reEnterPswET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == R.id.reset_re_reter || actionId == EditorInfo.IME_ACTION_DONE){
                    submit();
                    return true;
                }
                return false;
            }
        });
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.app_title_back:
                    onBackPressed();
                    break;
                case R.id.reset_submit:
                    submit();
                    break;
                case R.id.reset_cancel:
                    break;
            }
        }
    };

    private void submit() {
        SoftInputUtils.closedSoftInput(ResetPswActivity.this);
        Toast.makeText(ResetPswActivity.this, "submit", Toast.LENGTH_SHORT).show();
    }
}
