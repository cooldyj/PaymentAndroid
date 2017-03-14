package com.jerry.payment.mobile.module.login;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.RegExpUtils;
import com.jerry.payment.mobile.utils.SoftInputUtils;

/**
 * 注册Activity
 * Created by jerry on 2016/7/15.
 */
public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private EditText countryET;
    private EditText emailET;
    private EditText vCodeET;
    private EditText pswET;
    private EditText phoneET;
    private TextView sendCodeTV;
    private TextView policyTV;
    private TextView submitTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_sign_up_activity);
        initView();
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        countryET = (EditText) findViewById(R.id.sign_up_country);
        emailET = (EditText) findViewById(R.id.sign_up_email);
        vCodeET = (EditText) findViewById(R.id.sign_up_v_code);
        pswET = (EditText) findViewById(R.id.sign_up_psw);
        phoneET = (EditText) findViewById(R.id.sign_up_phone);
        sendCodeTV = (TextView) findViewById(R.id.sign_up_send_code);
        policyTV = (TextView) findViewById(R.id.sign_up_policy);
        submitTV = (TextView) findViewById(R.id.sign_up_submit);

        sendCodeTV.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        sendCodeTV.getPaint().setAntiAlias(true);
        policyTV.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        policyTV.getPaint().setAntiAlias(true);

        backIV.setOnClickListener(this);
        titleTV.setText(getString(R.string.login_register));
        countryET.setOnClickListener(this);
        sendCodeTV.setOnClickListener(this);
        policyTV.setOnClickListener(this);
        submitTV.setOnClickListener(this);

        countryET.setInputType(InputType.TYPE_NULL);

        phoneET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.submit || actionId == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.app_title_back){
            onBackPressed();
        }else if(id == R.id.sign_up_country){
            Toast.makeText(this, "Country", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.sign_up_submit){
            attemptLogin();
        }
    }

    private void attemptLogin() {
        SoftInputUtils.closedSoftInput(SignUpActivity.this);
        emailET.setError(null);
        vCodeET.setError(null);
        pswET.setError(null);
        phoneET.setError(null);
        String email = emailET.getText().toString();
        String vCode = vCodeET.getText().toString();
        String password = pswET.getText().toString();
        String phone = phoneET.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //验证手机号
        if(TextUtils.isEmpty(phone)){
            phoneET.setError(getString(R.string.register_error_phone_null));
            focusView = phoneET;
            cancel = true;
        } else if (!RegExpUtils.isPswValidated(phone)) {
            phoneET.setError(getString(R.string.register_error_invalid_phone));
            focusView = phoneET;
            cancel = true;
        }

        //验证密码
        if(TextUtils.isEmpty(password)){
            pswET.setError(getString(R.string.login_error_psw_null));
            focusView = pswET;
            cancel = true;
        } else if (!RegExpUtils.isPswValidated(password)) {
            pswET.setError(getString(R.string.login_error_invalid_psw));
            focusView = pswET;
            cancel = true;
        }

        //验证验证码
        if (TextUtils.isEmpty(vCode)) {
            vCodeET.setError(getString(R.string.register_error_invalid_code));
            focusView = vCodeET;
            cancel = true;
        }

        //验证邮箱
        if(TextUtils.isEmpty(email)){
            emailET.setError(getString(R.string.login_error_email_null));
            focusView = emailET;
            cancel = true;
        } else if (!RegExpUtils.isPswValidated(email)) {
            emailET.setError(getString(R.string.login_error_invalid_email));
            focusView = emailET;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Toast.makeText(SignUpActivity.this, "Sign up ing", Toast.LENGTH_SHORT).show();
        }
    }
}
