package com.jerry.payment.mobile.module.home;

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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.common.Env;
import com.jerry.payment.mobile.module.base.BaseFragment;
import com.jerry.payment.mobile.module.me.BankCardActivity;
import com.jerry.payment.mobile.module.me.MyBalanceActivity;
import com.jerry.payment.mobile.module.me.MyQRCodeActivity;
import com.jerry.payment.mobile.module.me.PersonalSettingActivity;
import com.jerry.payment.mobile.module.me.TopUpActivity;
import com.jerry.payment.mobile.module.me.WithDrawActivity;
import com.jerry.payment.mobile.module.service.TransactionActivity;
import com.jerry.payment.mobile.module.service.TransferFirstActivity;
import com.jerry.payment.mobile.module.service.zxing.ScanQRCodeActivity;
import com.jerry.payment.mobile.utils.IntentUtils;

/**
 * Home Main Page
 * Created by jerry on 2016/7/5.
 */
public class HomeFragment extends BaseFragment {

    private GridView mGridView;
    private BaseAdapter adapter;
    private final int MY_PERMISSION_REQUEST_CAMERA = 6;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.home_fragment, null);
        mGridView = (GridView) mView.findViewById(R.id.home_grid_layout);
        init();
        return mView;
    }

    private void init() {
        final int[] icons = {
                R.drawable.service_recharge
                , R.drawable.service_withdraw
                , R.drawable.service_scan
                , R.drawable.service_transfer
                , R.drawable.home_transactions
                , R.drawable.home_bank_card
                , R.drawable.home_account
                , R.drawable.me_setting
                , R.drawable.service_qr_code
        };

        final String[] texts = new String[]{
                getString(R.string.service_recharge)
                , getString(R.string.service_withdraw)
                , getString(R.string.service_scan)
                , getString(R.string.service_transfer)
                , getString(R.string.home_transactions)
                , getString(R.string.me_bank_card)
                , getString(R.string.me_balance)
                , getString(R.string.me_setting)
                , getString(R.string.service_qr_code)
        };

        final int totalOthersHeight = (int)(mContext.getResources().getDimension(R.dimen.app_tab_height))
                + (int)(mContext.getResources().getDimension(R.dimen.app_title_height))
                + Env.statusBarHeight;

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return texts.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if(convertView == null){
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.home_gridview_item, null);
                    viewHolder = new ViewHolder();
                    viewHolder.rl = (RelativeLayout) convertView.findViewById(R.id.home_grid_item_layout);
                    viewHolder.icon = (ImageView) convertView.findViewById(R.id.home_grid_icon);
                    viewHolder.text = (TextView) convertView.findViewById(R.id.home_grid_text);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.rl.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,
                        (Env.screenHeight - totalOthersHeight) / 3));
                viewHolder.icon.setImageResource(icons[position]);
                viewHolder.text.setText(texts[position]);
                return convertView;
            }
        };

        mGridView.setAdapter(adapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        IntentUtils.startActivity(mContext, TopUpActivity.class);
                        break;
                    case 1:
                        IntentUtils.startActivity(mContext, WithDrawActivity.class);
                        break;
                    case 2:
                        int permissionCheck = ContextCompat.checkSelfPermission(mContext,
                                Manifest.permission.CAMERA);
                        if(permissionCheck != PackageManager.PERMISSION_GRANTED){ //未被授予权限
                            // 判断是否需要解释获取权限原因
                            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                                // 向用户解释获取权限原因
                                Snackbar.make(mView, mContext.getString(R.string.request_camera_hint), Snackbar.LENGTH_LONG).show();
                            }
                            // 无需解释，直接请求权限
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSION_REQUEST_CAMERA);
                        }else { //已被授予权限
                            IntentUtils.startActivity(mContext, ScanQRCodeActivity.class);
                        }
                        break;
                    case 3:
                        IntentUtils.startActivity(mContext, TransferFirstActivity.class);
                        break;
                    case 4:
                        IntentUtils.startActivity(mContext, TransactionActivity.class);
                        break;
                    case 5:
                        IntentUtils.startActivity(mContext, BankCardActivity.class);
                        break;
                    case 6:
                        IntentUtils.startActivity(mContext, MyBalanceActivity.class);
                        break;
                    case 7:
                        IntentUtils.startActivity(mContext, PersonalSettingActivity.class);
                        break;
                    case 8:
                        IntentUtils.startActivity(mContext, MyQRCodeActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class ViewHolder{
        RelativeLayout rl;
        ImageView icon;
        TextView text;
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
                    Snackbar.make(mView, mContext.getString(R.string.open_camera_hint), Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

}
