package com.jerry.payment.mobile.okhttp.builder;

import com.jerry.payment.mobile.okhttp.OkHttpUtils;
import com.jerry.payment.mobile.okhttp.request.OtherRequest;
import com.jerry.payment.mobile.okhttp.request.RequestCall;

/**
 * (暂时不用)
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
