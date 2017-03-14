package com.jerry.payment.mobile.module.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.BitmapUtils;

/**
 * 我的二维码Activity
 * Created by jerry on 2016/7/18.
 */
public class MyQRCodeActivity extends BaseActivity {

    private ImageView avatarIV;
    private ImageView qrIV;
    private TextView nickNameTV;
    private TextView countryTV;
    private TextView emailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_qrcode_activity);
        initView();
    }

    private void initView() {
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        avatarIV = (ImageView) findViewById(R.id.me_qr_avatar);
        nickNameTV = (TextView) findViewById(R.id.me_qr_name);
        countryTV = (TextView) findViewById(R.id.me_qr_country);
        emailTV = (TextView) findViewById(R.id.me_qr_email);
        qrIV = (ImageView) findViewById(R.id.me_qr_img);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleTV.setText(getString(R.string.me_qr_code));

        qrIV.setImageBitmap(BitmapUtils.createQRCodeBitmap(MyQRCodeActivity.this, "com.ibs.payment.mobile.lithuania"));
    }

}
