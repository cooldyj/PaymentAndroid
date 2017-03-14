package com.jerry.payment.mobile.module.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.login.LoginActivity;
import com.jerry.payment.mobile.utils.IntentUtils;
import com.jerry.payment.mobile.widget.CircleImageView;
import com.jerry.payment.mobile.widget.ListItemFragment;

/**
 * Me Main Page
 * Created by jerry on 2016/7/4.
 */
public class MeFragment extends ListItemFragment {

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            View avatarView = inflater.inflate(R.layout.me_avatar_item, null);
            initItems(avatarView);
        }
        return rootView;
    }

    private void initItems(View avatarView){
        CircleImageView avatarIV = (CircleImageView) avatarView.findViewById(R.id.me_avatar_img);
//        Picasso.with(mContext)
//                .load("http://img5.imgtn.bdimg.com/it/u=597626770,2732655523&fm=21&gp=0.jpg")
//                .placeholder(R.drawable.app_dialog_smaile)
//                .error(R.drawable.app_dialog_smaile)
//                .into(avatarIV);
        avatarIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, LoginActivity.class);
            }
        });
        addItem(avatarView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        addItem(R.drawable.me_setting, getString(R.string.me_setting), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, PersonalSettingActivity.class);
            }
        });
        addItem(R.drawable.home_account, getString(R.string.me_balance), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, MyBalanceActivity.class);
            }
        });
        addItem(R.drawable.home_bank_card, getString(R.string.me_bank_card), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, BankCardActivity.class);
            }
        });
        addItem(R.drawable.service_qr_code, getString(R.string.service_qr_code), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(mContext, MyQRCodeActivity.class);
            }
        });

    }
}
