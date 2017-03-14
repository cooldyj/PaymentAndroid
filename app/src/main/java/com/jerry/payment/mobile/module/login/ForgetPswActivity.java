package com.jerry.payment.mobile.module.login;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.IntentUtils;
import com.jerry.payment.mobile.utils.RegExpUtils;
import com.jerry.payment.mobile.utils.SoftInputUtils;

/**
 * 忘记密码Activity
 * Created by jerry on 2016/7/15.
 */
public class ForgetPswActivity extends BaseActivity {

    private EditText emailET;
    private EditText vCodeET;
    private TextView sendCodeTV;
    private TextView submitTV;
    private TextView cancelTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_forget_psw_activity);
        initView();
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        emailET = (EditText) findViewById(R.id.forget_email);
        vCodeET = (EditText) findViewById(R.id.forget_v_code);
        sendCodeTV = (TextView) findViewById(R.id.forget_send_code);
        submitTV = (TextView) findViewById(R.id.forget_submit);
        cancelTV = (TextView) findViewById(R.id.forget_cancel);

        sendCodeTV.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        sendCodeTV.getPaint().setAntiAlias(true);

        titleTV.setText(getString(R.string.login_forget_psw));
        backIV.setOnClickListener(clickListener);
        sendCodeTV.setOnClickListener(clickListener);
        submitTV.setOnClickListener(clickListener);
        cancelTV.setOnClickListener(clickListener);

        vCodeET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.app_title_back){
                onBackPressed();
            }else if(id == R.id.forget_send_code){

            }else if(id == R.id.forget_submit){
                attemptLogin();
            }else if(id == R.id.forget_cancel){

            }
        }
    };

    private void attemptLogin() {
        SoftInputUtils.closedSoftInput(ForgetPswActivity.this);
        emailET.setError(null);
        vCodeET.setError(null);
        String email = emailET.getText().toString();
        String vCode = vCodeET.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(vCode)) {
            vCodeET.setError(getString(R.string.register_error_invalid_code));
            focusView = vCodeET;
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
            IntentUtils.startActivity(ForgetPswActivity.this, ResetPswActivity.class);
        }
    }
}
