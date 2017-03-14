package com.jerry.payment.mobile.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * 调用相机和本地照片工具类
 * Created by jerry on 2016/7/13.
 */
public class CameraUtils {

    /**
     * 启动相机拍照
     * @param activity 用于启动该方法的Activity
     * @param requestCode 请求码
     * @param uri 启动相机uri
     */
    public static void startCamera(Activity activity, int requestCode, Uri uri){
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("autofocus", true);// 自动对焦
        intent.putExtra("fullScreen", false);// 全屏
        if (uri != null) {
            //设置照片拍完后外部存储路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 获取相册照片
     *@param activity 用于启动该方法的Activity
     * @param requestCode 请求码
     */
    public static void getLocalImage(Activity activity, int requestCode) {
        // 捕获异常是防止有的机器不支持ACTION_PICK或ACTION_GET_CONTENT的动作
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            activity.startActivityForResult(intent, requestCode);
        }catch (Exception e1){
            try {
                Intent intent2 = new Intent();
                intent2.setType("image/*");
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                activity.startActivityForResult(intent2, requestCode);
                e1.printStackTrace();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }

    }

}
