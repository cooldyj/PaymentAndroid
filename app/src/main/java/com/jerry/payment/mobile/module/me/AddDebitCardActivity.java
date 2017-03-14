package com.jerry.payment.mobile.module.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.SoftInputUtils;

/**
 * 添加借记卡卡Activity
 * Created by jerry on 2016/7/26.
 */
public class AddDebitCardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_add_debit_activity);
        initView();
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        TextView confirmTV = (TextView) findViewById(R.id.add_debit_submit);

        backIV.setOnClickListener(clickListener);
        confirmTV.setOnClickListener(clickListener);
        titleTV.setText(getString(R.string.me_add_bank_card));
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_title_back:
                    onBackPressed();
                    break;
                case R.id.add_debit_submit:
                    submit();
                    break;
            }
        }
    };

    private void submit(){
        SoftInputUtils.closedSoftInput(AddDebitCardActivity.this);
    }

}
