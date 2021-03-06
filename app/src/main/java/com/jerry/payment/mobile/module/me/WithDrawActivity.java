package com.jerry.payment.mobile.module.me;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.module.service.LimitActivity;
import com.jerry.payment.mobile.utils.IntentUtils;
import com.jerry.payment.mobile.widget.CustomDialog;

/**
 * 提现Activity
 * Created by jerry on 2016/7/22.
 */
public class WithDrawActivity extends BaseActivity {

    private RelativeLayout bankRL;
    private EditText currencyET;
    private EditText amountET;
    private EditText withDrawTimeET;
    private TextView buttonTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_withdraw_activity);
        initView();
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        TextView limitTV = (TextView) findViewById(R.id.app_title_action);
        bankRL = (RelativeLayout) findViewById(R.id.withdraw_card_layout);
        currencyET = (EditText) findViewById(R.id.withdraw_currency);
        amountET = (EditText) findViewById(R.id.withdraw_amount);
        withDrawTimeET = (EditText) findViewById(R.id.withdraw_time);
        buttonTV = (TextView) findViewById(R.id.withdraw_submit);

        limitTV.setText(getString(R.string.me_withdraw_instuction));
        limitTV.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        limitTV.getPaint().setAntiAlias(true);
        limitTV.setVisibility(View.VISIBLE);

        currencyET.setInputType(InputType.TYPE_NULL);
        withDrawTimeET.setInputType(InputType.TYPE_NULL);

        backIV.setOnClickListener(clickListener);
        limitTV.setOnClickListener(clickListener);
        bankRL.setOnClickListener(clickListener);
        currencyET.setOnClickListener(clickListener);
        withDrawTimeET.setOnClickListener(clickListener);
        buttonTV.setOnClickListener(clickListener);
        titleTV.setText(getString(R.string.service_withdraw));
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_title_back:
                    onBackPressed();
                    break;
                case R.id.app_title_action:
                    IntentUtils.startActivity(WithDrawActivity.this, LimitActivity.class);
                    break;
                case R.id.withdraw_card_layout:
                    IntentUtils.startActivity(WithDrawActivity.this, ChooseBankCarActivity.class);
                    break;
                case R.id.withdraw_currency:
                    break;
                case R.id.withdraw_time:
                    break;
                case R.id.withdraw_submit:
                    CustomDialog.Builder builder = new CustomDialog.Builder(WithDrawActivity.this);
                    builder.setResult("SUCCESS!");
                    builder.setMessage("50000ERU");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.build().show();
                    break;
            }
        }
    };
}
