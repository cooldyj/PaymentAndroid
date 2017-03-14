package com.jerry.payment.mobile.module.me;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.IntentUtils;

/**
 * 用户未认证Activity
 * Created by jerry on 2016/7/27.
 */
public class AccountUnVerifyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_account_unverify_activity);
        initView();
    }

    private void initView() {
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        TextView statusTV = (TextView) findViewById(R.id.unverify_status);
        TextView buttonTV = (TextView) findViewById(R.id.unverify_submit);
        TextView policyTV = (TextView) findViewById(R.id.unverify_policy);

        backIV.setOnClickListener(clickListener);
        buttonTV.setOnClickListener(clickListener);
        policyTV.setOnClickListener(clickListener);
        titleTV.setText(getString(R.string.me_personal_verification));
        statusTV.setText(getStringText());
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.app_title_back:
                    onBackPressed();
                    break;
                case R.id.unverify_submit:
                    IntentUtils.startActivity(AccountUnVerifyActivity.this, AccountVerifiedActivity.class);
                    break;
                case R.id.unverify_policy:
                    break;
            }
        }
    };

    private SpannableStringBuilder getStringText(){
        String status = getString(R.string.me_verify_status);
        String unverified = getString(R.string.me_verify_no);
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(status).append(unverified);

        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(getResources().getColor(R.color.list_item_text_color));
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.deep_red));

        ssb.setSpan(colorSpan1, 0, status.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(colorSpan2, status.length(), ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ssb;
    }

}
