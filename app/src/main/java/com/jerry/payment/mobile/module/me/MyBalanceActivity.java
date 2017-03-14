package com.jerry.payment.mobile.module.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.common.Env;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.IntentUtils;

/**
 * 我的余额Activity
 * Created by jerry on 2016/7/21.
 */
public class MyBalanceActivity extends BaseActivity {

    private RelativeLayout topUpRL;
    private RelativeLayout withdrawRL;
    private TextView amountTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_balance_activity);
        initView();
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        topUpRL = (RelativeLayout) findViewById(R.id.balance_top_up_layout);
        withdrawRL = (RelativeLayout) findViewById(R.id.balance_withdraw_layout);
        RelativeLayout balanceItemRL = (RelativeLayout) findViewById(R.id.balance_item_layout);
        amountTV = (TextView) findViewById(R.id.balance_amount);
        TextView currencyTV = (TextView) findViewById(R.id.balance_currency);

        String currency = getString(R.string.service_transaction_currency) + getString(R.string.service_eur);
        currencyTV.setText(currency);

        balanceItemRL.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, Env.screenHeight / 10 * 3));

        backIV.setOnClickListener(clickListener);
        topUpRL.setOnClickListener(clickListener);
        withdrawRL.setOnClickListener(clickListener);
        titleTV.setText(getString(R.string.me_balance));
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_title_back:
                    onBackPressed();
                    break;
                case R.id.balance_top_up_layout:
                    IntentUtils.startActivity(MyBalanceActivity.this, TopUpActivity.class);
                    break;
                case R.id.balance_withdraw_layout:
                    IntentUtils.startActivity(MyBalanceActivity.this, WithDrawActivity.class);
                    break;
            }
        }
    };
}
