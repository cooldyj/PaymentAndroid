package com.jerry.payment.mobile.module.me;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.cache.CacheManager;
import com.jerry.payment.mobile.utils.CameraUtils;
import com.jerry.payment.mobile.utils.CropPhotoUtils;
import com.jerry.payment.mobile.widget.ListItemActivity;

import java.io.File;

/**
 * Modify user avatar Activity
 * Created by jerry on 2016/7/12.
 */
public class PersonalModifyAvatarActivity extends ListItemActivity {
    private final int MY_PERMISSION_REQUEST_STORAGE = 5;
    private final int MY_PERMISSION_REQUEST_CAMERA_ALL = 6;
    private final int MY_PERMISSION_REQUEST_CAMERA_STO = 7;
    private final int MY_PERMISSION_REQUEST_CAMERA_CAM = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleTV.setText(getString(R.string.me_modify_avatar));
        initItems();
    }

    private void initItems() {
        addItem(R.drawable.me_modify_avatar_choose, getString(R.string.me_modify_avatar_choose), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToGetLocalImg();
            }
        });
        addItem(R.drawable.me_modify_avatar_new, getString(R.string.me_modify_avatar_new), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToOpenCamera();
            }
        });
    }

    /**
     * 申请读写权限，并打开相册选择
     */
    private void tryToGetLocalImg() {
        int permissionCheck = ContextCompat.checkSelfPermission(PersonalModifyAvatarActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){ //未被授予权限
            // 判断是否需要解释获取权限原因
            if(ActivityCompat.shouldShowRequestPermissionRationale(PersonalModifyAvatarActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                // 向用户解释获取权限原因
                Snackbar.make(rootLL, PersonalModifyAvatarActivity.this.getString(R.string.get_local_img_reson), Snackbar.LENGTH_LONG).show();
            }
            // 无需解释，直接请求权限
            ActivityCompat.requestPermissions(PersonalModifyAvatarActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_STORAGE);
        }else { //已被授予权限
            //获取相册照片
            CameraUtils.getLocalImage(PersonalModifyAvatarActivity.this, CropPhotoUtils.PHOTO_REQUEST_GALLERY);
        }
    }

    /**
     * 申请相机和读写权限，并打开相机
     */
    private void tryToOpenCamera() {
        int storagePermission = ContextCompat.checkSelfPermission(PersonalModifyAvatarActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermisson = ContextCompat.checkSelfPermission(PersonalModifyAvatarActivity.this,
                Manifest.permission.CAMERA);
        if(storagePermission == PackageManager.PERMISSION_GRANTED
                && cameraPermisson != PackageManager.PERMISSION_GRANTED){ //有storage权限，没相机权限
            ActivityCompat.requestPermissions(PersonalModifyAvatarActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSION_REQUEST_CAMERA_CAM);
        }else if(storagePermission != PackageManager.PERMISSION_GRANTED
                && cameraPermisson == PackageManager.PERMISSION_GRANTED){ //有相机权限，没storage权限
            ActivityCompat.requestPermissions(PersonalModifyAvatarActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_CAMERA_STO);
        }else if(storagePermission != PackageManager.PERMISSION_GRANTED
                || cameraPermisson != PackageManager.PERMISSION_GRANTED){ //未被授予任何权限
            // 无需解释，直接请求权限
            ActivityCompat.requestPermissions(PersonalModifyAvatarActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    MY_PERMISSION_REQUEST_CAMERA_ALL);
        }else { //已被授予权限
            //创建相机拍照存储图片uri
            Uri imgUri = CropPhotoUtils.getOutputMediaFileUri(CacheManager.userAvatar, CropPhotoUtils.ORIGINAL_AVATAR);
            //启动相机拍照
            CameraUtils.startCamera(PersonalModifyAvatarActivity.this, CropPhotoUtils.PHOTO_REQUEST_TAKE_PHOTO, imgUri);
        }
    }

    /**
     * 读取相册图片开启剪裁
     */
    private void readLocalImage(Intent data) {
        if (data == null) {
            return;
        }
        Uri uri;
        uri = data.getData();
        if (uri != null) {
            startPhotoCrop(uri, null, CropPhotoUtils.PIC_REQUEST_CROP); // 图片裁剪
        }
    }

    /**
     * 启动剪裁功能(跳转到剪裁CropActivity)
     *
     * @param uri 待修改照片的原始路径
     * @param duplicatePath 根据Uri获取的文件路径
     * @param requestCode 启动裁剪请求码
     */
    private void startPhotoCrop(Uri uri, String duplicatePath, int requestCode) {
//        CropPhotoUtils.preCrop(this, uri, duplicatePath);
        Intent intent = new Intent(CropPhotoUtils.ACTION_CROP_IMAGE);
        //传递待修改照片的原始路径
        intent.putExtra(CropPhotoUtils.IMAGE_URI, uri);
        //传递剪裁照片的存储目录和名字
        intent.putExtra(CropPhotoUtils.CROP_PHOTO_DIR_PATH, CacheManager.userAvatar.getPath());
        intent.putExtra(CropPhotoUtils.CROP_PHOTO_NAME, CropPhotoUtils.CROP_AVATAR);
        //是否需要剪裁框
        // intent.putExtra(CropPhotoUtils.CROP_RECT_KEY, false);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropPhotoUtils.PHOTO_REQUEST_TAKE_PHOTO){ // 相机拍完照片返回处理
            if (resultCode == RESULT_OK) {
                if(CropPhotoUtils.getPhotoFileDir() != null){
                    //获取拍完照片存储路径
                    Uri uri = Uri.fromFile(new File(CropPhotoUtils.getPhotoFileDir(), CropPhotoUtils.ORIGINAL_AVATAR));
                    //开启裁剪
                    startPhotoCrop(uri, CropPhotoUtils.getUriString(PersonalModifyAvatarActivity.this, uri),
                            CropPhotoUtils.PIC_REQUEST_CROP);
                }
            }
        } else if (requestCode == CropPhotoUtils.PHOTO_REQUEST_GALLERY) { // 相册获取返回处理
            if (data != null) {
                readLocalImage(data);
            }
        } else if (requestCode == CropPhotoUtils.PIC_REQUEST_CROP) { // 照片剪裁后返回处理
            if (data != null) {
                setResult(RESULT_OK, data);
                PersonalModifyAvatarActivity.this.finish();
            }
        }
    }

    /**
     * 申请权限回调处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_REQUEST_STORAGE: //相册照片请求获取读写storage权限回调
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){ // 已经获取对应权限
                    //获取相册照片
                    CameraUtils.getLocalImage(PersonalModifyAvatarActivity.this, CropPhotoUtils.PHOTO_REQUEST_GALLERY);
                }else { // 未获取到授权，取消需要该权限的方法
                    Snackbar.make(rootLL, PersonalModifyAvatarActivity.this.getString(R.string.get_local_img_hint), Snackbar.LENGTH_LONG).show();
                }
                break;
            case MY_PERMISSION_REQUEST_CAMERA_STO: //相机的请求读写storage权限回调
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){ // 已经获取对应权限
                    //创建相机拍照存储图片uri
                    Uri imgUri = CropPhotoUtils.getOutputMediaFileUri(CacheManager.userAvatar, CropPhotoUtils.ORIGINAL_AVATAR);
                    //启动相机拍照
                    CameraUtils.startCamera(PersonalModifyAvatarActivity.this, CropPhotoUtils.PHOTO_REQUEST_TAKE_PHOTO, imgUri);
                }else { // 未获取到授权，取消需要该权限的方法
                    Snackbar.make(rootLL, PersonalModifyAvatarActivity.this.getString(R.string.camera_storage_hint), Snackbar.LENGTH_LONG).show();
                }
                break;
            case MY_PERMISSION_REQUEST_CAMERA_CAM: //相机的请求读写storage权限回调
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){ // 已经获取对应权限
                    //创建相机拍照存储图片uri
                    Uri imgUri = CropPhotoUtils.getOutputMediaFileUri(CacheManager.userAvatar, CropPhotoUtils.ORIGINAL_AVATAR);
                    //启动相机拍照
                    CameraUtils.startCamera(PersonalModifyAvatarActivity.this, CropPhotoUtils.PHOTO_REQUEST_TAKE_PHOTO, imgUri);
                }else { // 未获取到授权，取消需要该权限的方法
                    Snackbar.make(rootLL, PersonalModifyAvatarActivity.this.getString(R.string.camera_permission_hint), Snackbar.LENGTH_LONG).show();
                }
                break;
            case MY_PERMISSION_REQUEST_CAMERA_ALL: //相机的请求权限回调
                if(grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED){ // 已经获取对应权限
                    //创建相机拍照存储图片uri
                    Uri imgUri = CropPhotoUtils.getOutputMediaFileUri(CacheManager.userAvatar, CropPhotoUtils.ORIGINAL_AVATAR);
                    //启动相机拍照
                    CameraUtils.startCamera(PersonalModifyAvatarActivity.this, CropPhotoUtils.PHOTO_REQUEST_TAKE_PHOTO, imgUri);
                }else { // 未获取到授权，取消需要该权限的方法
                    Snackbar.make(rootLL, PersonalModifyAvatarActivity.this.getString(R.string.camera_all_hint), Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }
}
