package com.jerry.payment.mobile.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.main.MainActivity;
import com.jerry.payment.mobile.utils.LanguageUtils;
import com.jerry.payment.mobile.widget.ListItemActivity;

/**
 * Change Language Activity
 * Created by jerry on 2016/7/12.
 */
public class ChangeLanguageActivity extends ListItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleTV.setText(getString(R.string.me_modify_language));
        initItems();
    }

    private void initItems() {
        addItem(R.drawable.me_modify_avatar_choose, "中文", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageUtils.switchLanguage(ChangeLanguageActivity.this, "cn");
                Intent intent = new Intent(ChangeLanguageActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        addItem(R.drawable.me_modify_avatar_new, "English", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageUtils.switchLanguage(ChangeLanguageActivity.this, "en");
                Intent intent = new Intent(ChangeLanguageActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

}
