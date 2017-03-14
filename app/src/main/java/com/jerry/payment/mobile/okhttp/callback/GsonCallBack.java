package com.jerry.payment.mobile.okhttp.callback;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.jerry.payment.mobile.common.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * 自定义的 Gson CallBack 返回 传入的实体类对象
 * Created by jerry on 2016/8/10.
 */
public abstract class GsonCallBack<T> extends Callback<T> {

    private Class<T> cls;

    public GsonCallBack(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String resp = response.body().string();
        if (!TextUtils.isEmpty(resp)) {
            try {
                JSONObject jsonObject = new JSONObject(resp);
                int respCode = jsonObject.optInt("responseCode");
                String errorMessage = jsonObject.optString("errorMessage");
                JSONObject data = jsonObject.optJSONObject("bizData");
                if (respCode == Urls.SUCCESS_CODE) {
                    Gson gson = new Gson();
                    return gson.fromJson(data.toString(), cls);
                } else {
                    setResponseCode(Urls.ERROR_CODE);
                    setErrorMessage(errorMessage);
                    return null;
                }

            } catch (JSONException e) {
                setResponseCode(Urls.ERROR_CODE);
                setErrorMessage(e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        setResponseCode(Urls.ERROR_CODE);
        setErrorMessage("Response Data Is Null");
        return null;
    }
}
