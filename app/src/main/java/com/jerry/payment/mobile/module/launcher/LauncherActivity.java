package com.jerry.payment.mobile.module.launcher;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.common.Env;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.module.main.MainActivity;
import com.jerry.payment.mobile.utils.IntentUtils;

/**
 * 启动图页
 * Created by jerry on 2016/7/14.
 */
public class LauncherActivity extends BaseActivity{

    final int DELAY_SECONDS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Env.isFirstIn){
                    IntentUtils.startActivity(LauncherActivity.this, AppGuideActivity.class);
                }else {
                    IntentUtils.startActivity(LauncherActivity.this, MainActivity.class);
                }
                LauncherActivity.this.finish();
            }
        }, DELAY_SECONDS);
    }
}
