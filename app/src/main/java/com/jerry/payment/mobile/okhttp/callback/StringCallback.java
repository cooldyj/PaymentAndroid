package com.jerry.payment.mobile.okhttp.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * 自定义的 String CallBack 返回 String
 */
public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        return response.body().string();
    }
}
