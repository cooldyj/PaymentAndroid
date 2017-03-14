package com.jerry.payment.mobile;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.WindowManager;

import com.jerry.payment.mobile.cache.CacheManager;
import com.jerry.payment.mobile.common.Env;
import com.jerry.payment.mobile.okhttp.OkHttpUtils;
import com.jerry.payment.mobile.utils.LanguageUtils;
import com.jerry.payment.mobile.utils.PreferencesUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * project Application
 * Created by jerry on 2016/7/6.
 */
public class IbsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initAppSdDir();
        getIsFirstIn();
        getAppVersionInfo();
        getDisplayMetrics();
        getStatusBarHeight();
        initOkHttp();
        initLanguage();
    }

    /**
     * 初始化文件存储目录
     */
    private void initAppSdDir() {
        CacheManager.userAvatar = new File(Environment.getExternalStorageDirectory(), Env.sdName + "/userAvatar");
//        CacheManager.cacheDir = new File(Environment.getExternalStorageDirectory(), Env.sdName + "/cacheDir");
        if(Environment.getExternalStorageState().equals("mounted")) {
            if(!CacheManager.userAvatar.exists() || !CacheManager.userAvatar.isDirectory()){
                CacheManager.userAvatar.mkdirs();
            }
//            if(!CacheManager.cacheDir.exists() || !CacheManager.cacheDir.isDirectory()){
//                CacheManager.cacheDir.mkdirs();
//            }
        }

    }

    /**
     * 获取是否初次进入App
     */
    private void getIsFirstIn(){
        Env.isFirstIn = PreferencesUtils.getPreference(getApplicationContext(), PreferencesUtils.APP_CONTENT, "isFirstIn", true);
        if(Env.isFirstIn){
            PreferencesUtils.setPreferences(getApplicationContext(), PreferencesUtils.APP_CONTENT, "isFirstIn", false);
        }
    }

    /**
     * 获取版本信息
     */
    private void getAppVersionInfo() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(
                    this.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            Env.packageName = packageInfo.packageName;
            Env.versionCode = packageInfo.versionCode;
            Env.versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取屏幕分辨率和密度
     */
    private void getDisplayMetrics(){
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        int rotation = wm.getDefaultDisplay().getRotation();
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        Env.screenWidth = rotation == Surface.ROTATION_0 ? displayMetrics.widthPixels : displayMetrics.heightPixels;
        Env.screenHeight = rotation == Surface.ROTATION_0 ? displayMetrics.heightPixels : displayMetrics.widthPixels;
        Env.density = displayMetrics.density;
    }

    /**
     * 获取状态栏高度/像素
     */
    private void getStatusBarHeight() {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Env.statusBarHeight = statusBarHeight;
    }

    private void initOkHttp(){
//        Cache cache= new Cache(CacheManager.cacheDir, OkHttpUtils.CACHE_SIZE);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(6000L, TimeUnit.MILLISECONDS)
                .readTimeout(6000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(client);
    }

    private void initLanguage(){
        String language = PreferencesUtils.getPreference(getApplicationContext(), PreferencesUtils.APP_CONTENT, "language", "en");
        LanguageUtils.switchLanguage(getApplicationContext(), language);
    }
}
