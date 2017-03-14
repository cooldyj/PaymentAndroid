package com.jerry.payment.mobile.okhttp.builder;

import com.jerry.payment.mobile.okhttp.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Get Post 请求的父类 Builder
 */
public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder>
{
    protected String url;      //请求的url
    protected Object tag;      //请求的tag,可以根据该tag取消这个请求
    protected Map<String, String> headers;      //请求头集合
    protected Map<String, String> params;      //请求体参数集合
    protected int id;

    public T id(int id)
    {
        this.id = id;
        return (T) this;
    }

    public T url(String url)
    {
        this.url = url;
        return (T) this;
    }


    public T tag(Object tag)
    {
        this.tag = tag;
        return (T) this;
    }

    public T headers(Map<String, String> headers)
    {
        this.headers = headers;
        return (T) this;
    }

    public T addHeader(String key, String val)
    {
        if (this.headers == null)
        {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return (T) this;
    }

    public abstract RequestCall build();
}
