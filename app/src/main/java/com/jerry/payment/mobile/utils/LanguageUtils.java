package com.jerry.payment.mobile.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * 语言工具类
 * Created by jerry on 2016/8/15.
 */
public class LanguageUtils {

    /**
     * 切换应用语言
     */
    public static void switchLanguage(Context context, String lan){
        Resources resources = context.getResources();
        Configuration cf = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if(lan.equals("en")){
            cf.locale = Locale.ENGLISH;
        }else if(lan.equals("cn")){
            cf.locale = Locale.SIMPLIFIED_CHINESE;
        }
        resources.updateConfiguration(cf,dm);
        PreferencesUtils.setPreferences(context, PreferencesUtils.APP_CONTENT, "language", lan);
    }

}
