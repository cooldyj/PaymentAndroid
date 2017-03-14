package com.jerry.payment.mobile.module.service;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.me.TopUpActivity;
import com.jerry.payment.mobile.module.me.WithDrawActivity;
import com.jerry.payment.mobile.module.service.zxing.ScanQRCodeActivity;
import com.jerry.payment.mobile.utils.IntentUtils;
import com.jerry.payment.mobile.widget.ListItemFragment;

/**
 * Service Main Page
 * Created by jerry on 2016/7/5.
 */
public class ServiceFragment extends ListItemFragment {

    private final int MY_PERMISSION_REQUEST_CAMERA = 6;

    public static ServiceFragment newInstance() {
        Bundle args = new Bundle();
        ServiceFragment fragment = new ServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            initItems();
        }
        return rootView;
    }

    private void initItems(){
        addItem(R.drawable.service_scan, getString(R.string.service_scan), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.CAMERA);
                if(permissionCheck != PackageManager.PERMISSION_GRANTED){ //未被授予权限
                    // 判断是否需要解释获取权限原因
                    if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                        // 向用户解释获取权限原因
                        Snackbar.make(rootView, mContext.getString(R.string.request_camera_hint), Snackbar.LENGTH_LONG).show();
                    }
                    // 无需解释，直接请求权限
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSION_REQUEST_CAMERA);
                }else { //已被授予权限
                    IntentUtils.startActivity(mContext, ScanQRCodeActivity.class);
                }
            }
        });
        addItem(R.drawable.service_recharge, getString(R.string.service_recharge), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, TopUpActivity.class);
            }
        });
        addItem(R.drawable.service_withdraw, getString(R.string.service_withdraw), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, WithDrawActivity.class);
            }
        });
        addItem(R.drawable.service_transfer, getString(R.string.service_transfer), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, TransferFirstActivity.class);
            }
        });
        addItem(R.drawable.home_transactions, getString(R.string.home_transactions), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, TransactionActivity.class);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_REQUEST_CAMERA:
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // 已经获取对应权限
                    IntentUtils.startActivity(mContext, ScanQRCodeActivity.class);
                }else {
                    // 未获取到授权，取消需要该权限的方法
                    Snackbar.make(rootView, mContext.getString(R.string.open_camera_hint), Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }
}
