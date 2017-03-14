package com.jerry.payment.mobile.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 裁剪照片工具类
 * Created by jerry on 2016/7/13.
 */
public class CropPhotoUtils {
    /**
     * 拍照请求码
     */
    public static final int PHOTO_REQUEST_TAKE_PHOTO = 5;
    /**
     * 相册请求码
     */
    public static final int PHOTO_REQUEST_GALLERY = 6;
    /**
     * 裁剪请求码
     */
    public static final int PIC_REQUEST_CROP = 7;
    /**
     * 原始头像名
     */
    public static final String ORIGINAL_AVATAR = "original_avatar";
    /** 剪裁头像名 */
    public static final String CROP_AVATAR = "cropped_avatar";
    /**
     * 裁剪Activity Action
     */
    public final static String ACTION_CROP_IMAGE = "android.intent.action.CROP.ibs";
    /**
     * 图片uri 传参key名
     */
    public final static String IMAGE_URI = "imageUri";
    /**
     * 裁剪图片uri 传参key名
     */
    public final static String CROP_IMAGE_URI = "cropImageUri";
    /**
     * 剪裁照片路径
     */
    public final static String CROP_PHOTO_DIR_PATH = "path";
    /**
     * 剪裁照片名称
     */
    public final static String CROP_PHOTO_NAME = "name";
    /**
     * 是否需要剪裁key,调用者传递
     */
    public static String CROP_RECT_KEY = "crop_rect_key";
    /**
     * 用来记录图片重复次数，和保存图片命名有关
     */
    public static final String CROP_DUPLICAITION_COUNT = "duplication";
    /**
     * 拍照存储目录
     */
    public static File dir;
    /**
     * 拍照照片的名称
     */
    public static String name;

    /**
     * 创建相机拍照存储图片uri
     */
    public static Uri getOutputMediaFileUri(File photoDir, String photoName) {
        dir = photoDir;
        name = photoName;
        return Uri.fromFile(new File(photoDir, photoName));
    }

    /**
     * 获取照片存储目录(相机拍的照片)
     */
    public static File getPhotoFileDir() {
        if (dir != null && dir.isDirectory()) {
            return dir;
        }
        return null;
    }

    /**
     * 剪裁之前的预处理
     *
     * @param context context
     * @param uri 待修改照片的原始路径
     * @param duplicatePath 根据Uri获取的文件路径
     */
    public static Uri preCrop(Context context, Uri uri, String duplicatePath) {
        Uri duplicateUri;
        if (duplicatePath == null) {
            duplicateUri = getDuplicateUri(context, uri);
        } else {
            duplicateUri = getDuplicateUri(uri, duplicatePath);
        }
        // rotateImage();
        return duplicateUri;
    }

    private static Uri getDuplicateUri(Context context, Uri uri) {
        Uri duplicateUri;
        String uriString = getUriString(context, uri);
        duplicateUri = getDuplicateUri(uri, uriString);
        return duplicateUri;
    }

    /**
     * 如果是拍照的话就直接获取了
     */
    private static Uri getDuplicateUri(Uri uri, String uriString) {
        Uri duplicateUri;
        String duplicatePath;
        duplicatePath = uriString.replace(".", "_duplicate.");
        // cropImagePath = uriString;
        // 判断原图是否旋转，旋转了进行修复
        rotateImage(uriString);
        duplicateUri = Uri.fromFile(new File(duplicatePath));
        return duplicateUri;
    }

    /**
     * 旋转图象
     */
    public static void rotateImage(String uriString) {
        try {
            // 读取图片信息
            ExifInterface exifInterface = new ExifInterface(uriString);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90
                    || orientation == ExifInterface.ORIENTATION_ROTATE_180
                    || orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                String value = String.valueOf(orientation);
                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, value);
                // exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION,
                // "no");
                exifInterface.saveAttributes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据Uri获取文件的路径
     */
    public static String getUriString(Context context, Uri uri) {
        String imgPath = null;
        if (uri != null) {
            String uriString = uri.toString();
            // 小米手机的适配问题，小米手机的uri以file开头，其他的手机都以content开头
            // 以content开头的uri表明图片插入数据库中了，而以file开头表示没有插入数据库
            // 所以就不能通过query来查询，否则获取的cursor会为null。
            if (uriString.startsWith("file")) {
                // uri的格式为file:///mnt....,将前七个过滤掉获取路径
                imgPath = uriString.substring(7, uriString.length());
                return imgPath;
            }
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            imgPath = cursor.getString(1); // 图片文件路径
            cursor.close();
        }
        return imgPath;
    }

    /**
     * 获取剪裁照片名称
     */
    public static String getCropPhotoFileName(String cropName) {
        if (cropName != null && !"".equals(cropName)) {
            if (cropName.endsWith(".jpg") || cropName.endsWith(".png") || cropName.endsWith(".jpeg")) {
                return cropName;
            } else {
                return cropName + ".jpg";
            }
        }
        return "crop_photo.jpg";
    }

    /**
     * 根据uri获取bitmap
     */
    public static Bitmap getBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            is = getInputStream(context, uri);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
        return bitmap;
    }

    /**
     * 获取输入流
     */
    public static InputStream getInputStream(Context context, Uri mUri) throws IOException {
        try {
            if (mUri.getScheme().equals("file")) {
                return new java.io.FileInputStream(mUri.getPath());
            } else {
                return context.getContentResolver().openInputStream(mUri);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }








    /**
     * 创建保存照片的文件
     */
    public static File getOutputMediaFile(File dir, String photoName) {
        if (dir == null) {
            return null;
        }
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null;
            }
        }
        File mediaFile;
        mediaFile = new File(dir.getPath() + File.separator + photoName + ".jpg");

        return mediaFile;
    }
}
