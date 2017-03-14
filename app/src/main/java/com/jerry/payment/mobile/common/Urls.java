package com.jerry.payment.mobile.common;

/**
 * 接口url类
 * Created by jerry on 2016/8/10.
 */
public class Urls {

    /**
     * 返回成功码
     */
    public static final int SUCCESS_CODE = 1010;
    /**
     * 返回失败码
     */
    public static final int ERROR_CODE = 1011;

    private static final String BaseUrl1 = "http://192.168.191.1:8181/LithuaniaModule/action/";

    //获取个人信息接口
    public static String PERSONAL_INFO = BaseUrl1 + "myAction.do";

    //获取全部交易记录接口
    public static String TRANSACTION_ALL = BaseUrl1 + "transactionAllList.do";

}
