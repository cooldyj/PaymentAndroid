package com.jerry.payment.mobile.module.me;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
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
 * 修改昵称，邮箱，地址，电话通用Activity
 * Created by jerry on 2016/7/15.
 */
public class ModifyCommonActivity extends BaseActivity {

    private String title = "";
    private int icon;
    private String type;
    private String hint;
    private EditText editET;
    private TextView submitTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_common_activity);
        initData();
        initView();
    }

    private void initData(){
        Intent data = getIntent();
        if(data != null){
            title = data.getStringExtra("title");
            icon = data.getIntExtra("icon", 0);
            type = data.getStringExtra("type");
            hint = data.getStringExtra("hint");
        }
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        editET = (EditText) findViewById(R.id.modify_item);
        submitTV = (TextView) findViewById(R.id.modify_submit);

        if(icon != 0){
            Drawable drawable = getResources().getDrawable(icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            editET.setCompoundDrawables(drawable, null, null, null);
            editET.setCompoundDrawablePadding(25);
        }
        editET.setHint(hint);

        editET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == R.id.submit || actionId == EditorInfo.IME_ACTION_DONE){
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        if(type.equals("nickname")){
            editET.setInputType(InputType.TYPE_CLASS_TEXT);
        }else if(type.equals("email")){
            editET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }else if(type.equals("address")){
            editET.setInputType(InputType.TYPE_CLASS_TEXT);
        }else if(type.equals("phone")){
            editET.setInputType(InputType.TYPE_CLASS_PHONE);
        }

        backIV.setOnClickListener(clickListener);
        titleTV.setText(title);
        submitTV.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_title_back:
                    onBackPressed();
                    break;
                case R.id.modify_submit:
                    attemptLogin();
                    break;
            }
        }
    };

    private void attemptLogin() {
        SoftInputUtils.closedSoftInput(ModifyCommonActivity.this);
        editET.setError(null);
        String text = editET.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(type.equals("nickname")){
            if (TextUtils.isEmpty(text)) {
                editET.setError(getString(R.string.me_error_nickname_null));
                focusView = editET;
                cancel = true;
            } else if (!RegExpUtils.isNickNameValidated(text)) {
                editET.setError(getString(R.string.me_error_invalid_nickname));
                focusView = editET;
                cancel = true;
            }
        }else if(type.equals("email")){
            if (TextUtils.isEmpty(text)) {
                editET.setError(getString(R.string.login_error_email_null));
                focusView = editET;
                cancel = true;
            } else if (!RegExpUtils.isEmailValidated(text)) {
                editET.setError(getString(R.string.login_error_invalid_email));
                focusView = editET;
                cancel = true;
            }
        }else if(type.equals("address")){
            if (TextUtils.isEmpty(text)) {
                editET.setError(getString(R.string.me_error_address_null));
                focusView = editET;
                cancel = true;
            } else if (!RegExpUtils.isAddressValidated(text)) {
                editET.setError(getString(R.string.me_error_invalid_address));
                focusView = editET;
                cancel = true;
            }
        }else if(type.equals("phone")){
            if (TextUtils.isEmpty(text)) {
                editET.setError(getString(R.string.register_error_phone_null));
                focusView = editET;
                cancel = true;
            } else if (!RegExpUtils.isPhoneValidated(text)) {
                editET.setError(getString(R.string.register_error_invalid_phone));
                focusView = editET;
                cancel = true;
            }
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            submit();
        }
    }

    private void submit(){
        if(type.equals("nickname")){
        }else if(type.equals("email")){
        }else if(type.equals("address")){
        }else if(type.equals("phone")){
        }
        Toast.makeText(ModifyCommonActivity.this, "Submitting", Toast.LENGTH_SHORT).show();
    }
}
