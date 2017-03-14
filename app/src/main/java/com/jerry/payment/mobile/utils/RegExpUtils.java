package com.jerry.payment.mobile.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * Created by jerry on 2016/7/18.
 */
public class RegExpUtils {

    /**
     * 邮箱正则表达式
     */
    private final static String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    /**
     * 国内手机正则表达式
     */
    private final static String PHONE_PATTERN = "^1[3,4,5,7,8]\\d{9}$";

    /**
     * 匹配正则表达式
     *
     * @param value
     *            需要匹配的数据
     * @param regExp
     *            正则表达式
     * @return boolean 如果匹配正确返回True,否则返回False
     */
    public static boolean isMatch(String value, String regExp) {

        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(value);

        return m.find();
    }

    /**
     * 验证邮箱是否符合规则
     */
    public static boolean isEmailValidated(String value) {
        return isMatch(value, EMAIL_PATTERN);
    }

    /**
     * 验证手机号是否符合规则
     */
    public static boolean isPhoneValidated(String value) {
        // TODO: 2016/7/18 add new pattern
        return !TextUtils.isEmpty(value) && value.length() > 10;
    }

    /**
     * 验证密码是否符合规则
     */
    public static boolean isPswValidated(String value) {
        // TODO: 2016/7/18 add new pattern
        return !TextUtils.isEmpty(value) && value.length() > 5;
    }

    /**
     * 验证昵称是否符合规则
     */
    public static boolean isNickNameValidated(String value) {
        // TODO: 2016/7/18 add new pattern
        return !TextUtils.isEmpty(value);
    }

    /**
     * 验证昵称是否符合规则
     */
    public static boolean isAddressValidated(String value) {
        // TODO: 2016/7/18 add new pattern
        return !TextUtils.isEmpty(value) && value.length() > 6;
    }


    /**
     * 验证国内手机号是否符合规则
     */
    public static boolean isChinaPhoneValidated(String value) {
        return isMatch(value, PHONE_PATTERN);
    }

}
