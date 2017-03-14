package com.jerry.payment.mobile.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jerry.payment.mobile.common.Env;

import java.util.Hashtable;

/**
 * Bitmap处理工具类
 * Created by jerry on 2016/8/2.
 */
public class BitmapUtils {

    /**
     * 通过传入的字符串生成二维码Bitmap
     */
    public static Bitmap createQRCodeBitmap(String contentStr, int width, int height) {
        if (TextUtils.isEmpty(contentStr))
            return null;

        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(contentStr,
                    BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过传入的字符串生成二维码Bitmap
     */
    public static Bitmap createQRCodeBitmap(Context context, String contentStr) {
        int width = Env.screenWidth - DisplayUtils.convertDIP2PX(context, 80);
        return createQRCodeBitmap(contentStr, width, width);
    }

}
