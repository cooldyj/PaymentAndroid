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

/**
 * 用户已认证Activity
 * Created by jerry on 2016/7/27.
 */
public class AccountVerifiedActivity extends BaseActivity {

    private TextView statusTV;
    private TextView accountTV;
    private TextView regionTV;
    private TextView emailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_account_verified_activity);
        initView();
    }

    private void initView() {
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        statusTV = (TextView) findViewById(R.id.verified_status);
        accountTV = (TextView) findViewById(R.id.verified_account);
        regionTV = (TextView) findViewById(R.id.verified_region);
        emailTV = (TextView) findViewById(R.id.verified_email);

        backIV.setOnClickListener(clickListener);
        titleTV.setText(getString(R.string.me_personal_verification));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setText("Verified", "Mike Jass", "America", "cooldyj@126.com");
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.app_title_back:
                    onBackPressed();
                    break;
            }
        }
    };

    private void setText(String status, String account, String region, String email){
        String statusText = getString(R.string.me_verify_status);
        String accountText = getString(R.string.me_account_name);
        String regionText = getString(R.string.me_region);
        String emailext = getString(R.string.me_email);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.gray));

        SpannableStringBuilder statusBuilder = new SpannableStringBuilder();
        SpannableStringBuilder accountBuilder = new SpannableStringBuilder();
        SpannableStringBuilder regionBuilder = new SpannableStringBuilder();
        SpannableStringBuilder emailBuilder = new SpannableStringBuilder();

        statusBuilder.append(statusText).append("\n").append(status);
        accountBuilder.append(accountText).append("\n").append(account);
        regionBuilder.append(regionText).append("\n").append(region);
        emailBuilder.append(emailext).append("\n").append(email);

        statusBuilder.setSpan(colorSpan, statusText.length(), statusBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        accountBuilder.setSpan(colorSpan, accountText.length(), accountBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        regionBuilder.setSpan(colorSpan, regionText.length(), regionBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        emailBuilder.setSpan(colorSpan, emailext.length(), emailBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        statusTV.setText(statusBuilder);
        accountTV.setText(accountBuilder);
        regionTV.setText(regionBuilder);
        emailTV.setText(emailBuilder);
    }
}
