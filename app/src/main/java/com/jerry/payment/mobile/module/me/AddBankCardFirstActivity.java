package com.jerry.payment.mobile.module.me;

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
 * 添加银行卡第一步Activity
 * Created by jerry on 2016/7/26.
 */
public class AddBankCardFirstActivity extends BaseActivity {

    private EditText nameET;
    private EditText numET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_add_card_first_activity);
        initView();
    }

    private void initView() {
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        TextView nextTV = (TextView) findViewById(R.id.add_card_next);
        nameET = (EditText) findViewById(R.id.add_card_name);
        numET = (EditText) findViewById(R.id.add_card_num);

        backIV.setOnClickListener(clickListener);
        nextTV.setOnClickListener(clickListener);
        titleTV.setText(getString(R.string.me_add_bank_card));

        numET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == R.id.add_first_next || actionId == EditorInfo.IME_ACTION_DONE){
                    goNext();
                    return true;
                }
                return false;
            }
        });
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_title_back:
                    onBackPressed();
                    break;
                case R.id.add_card_next:
                    goNext();
                    break;
            }
        }
    };

    private void goNext(){
        SoftInputUtils.closedSoftInput(AddBankCardFirstActivity.this);
        IntentUtils.startActivity(AddBankCardFirstActivity.this, AddDebitCardActivity.class);
    }

}
