package com.jerry.payment.mobile.module.login;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.common.Env;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.IntentUtils;
import com.jerry.payment.mobile.utils.RegExpUtils;
import com.jerry.payment.mobile.utils.SoftInputUtils;

/**
 * Login Page
 * Created by jerry on 2016/7/7.
 */
public class LoginActivity extends BaseActivity {

    private EditText emailET;
    private EditText pswET;
    private TextView loginTV;
    private TextView linkTV;
    private TextView signUpTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
    }

    private void initView() {
        ImageView imgIV = (ImageView) findViewById(R.id.login_img);
//        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
//        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        emailET = (EditText) findViewById(R.id.login_user_name);
        pswET = (EditText) findViewById(R.id.login_psw);
        loginTV = (TextView) findViewById(R.id.login_btn);
        linkTV = (TextView) findViewById(R.id.login_link);
        signUpTV = (TextView) findViewById(R.id.login_sign_up);

        linkTV.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        linkTV.getPaint().setAntiAlias(true);
        signUpTV.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        signUpTV.getPaint().setAntiAlias(true);

        imgIV.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, Env.screenHeight / 4));

//        titleTV.setText(getString(R.string.login_sign_in));

//        backIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        pswET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.login || actionId == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        linkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(LoginActivity.this, ForgetPswActivity.class);
            }
        });

        signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(LoginActivity.this, SignUpActivity.class);
            }
        });
    }

    private void attemptLogin() {
        SoftInputUtils.closedSoftInput(LoginActivity.this);
        emailET.setError(null);
        pswET.setError(null);
        String email = emailET.getText().toString();
        String password = pswET.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(password)){
            pswET.setError(getString(R.string.login_error_psw_null));
            focusView = emailET;
            cancel = true;
        } else if (!RegExpUtils.isPswValidated(password)) {
            pswET.setError(getString(R.string.login_error_invalid_psw));
            focusView = pswET;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailET.setError(getString(R.string.login_error_email_null));
            focusView = emailET;
            cancel = true;
        } else if (!RegExpUtils.isEmailValidated(email)) {
            emailET.setError(getString(R.string.login_error_invalid_email));
            focusView = emailET;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Toast.makeText(LoginActivity.this, "Logging", Toast.LENGTH_SHORT).show();
        }
    }
}
