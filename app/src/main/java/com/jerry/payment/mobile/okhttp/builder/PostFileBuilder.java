package com.jerry.payment.mobile.okhttp.builder;

import com.jerry.payment.mobile.okhttp.request.PostFileRequest;
import com.jerry.payment.mobile.okhttp.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * post 上传 File 请求的 Builder
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder>
{
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file)
    {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostFileRequest(url, tag, params, headers, file, mediaType,id).build();
    }


}
