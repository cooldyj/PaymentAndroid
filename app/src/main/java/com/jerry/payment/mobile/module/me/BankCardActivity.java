package com.jerry.payment.mobile.module.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.model.BankCardInfo;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的银行卡Activity
 * Created by jerry on 2016/7/18.
 */
public class BankCardActivity extends BaseActivity {

    private ListView listView;
    private BankCardListAdapter adapter;
    private List<BankCardInfo> infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_bank_card_activity);
        initData();
        initView();
    }

    private void initData(){
        infos = new ArrayList<>();
        adapter = new BankCardListAdapter(BankCardActivity.this, infos);
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        listView = (ListView) findViewById(R.id.bank_card_list_view);
        TextView addTV = (TextView) findViewById(R.id.bank_card_add) ;

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleTV.setText(getString(R.string.me_bank_card));
        listView.setAdapter(adapter);

        addTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(BankCardActivity.this, AddBankCardFirstActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        infos.clear();

        BankCardInfo info1 = new BankCardInfo();
        BankCardInfo info2= new BankCardInfo();
        BankCardInfo info3 = new BankCardInfo();
        info1.setBankName("China Merchants Bank");
        info1.setBankNumber("6214 **** **** 5988");
        info1.setBankType("Credit Card");
        info1.setIconUrl(String.valueOf(R.drawable.me_cmb_icon));

        info2.setBankName("China Construction Bank");
        info2.setBankNumber("6217 **** **** 3426");
        info2.setBankType("Credit Card");
        info2.setIconUrl(String.valueOf(R.drawable.me_cbc_icon));

        info3.setBankName("Industrial and Commercial Bank");
        info3.setBankNumber("6218 **** **** 1234");
        info3.setBankType("Credit Card");
        info3.setIconUrl(String.valueOf(R.drawable.me_icbc_icon));
        infos.add(info1);
        infos.add(info2);
        infos.add(info3);

        adapter.notifyDataSetChanged();
    }
}
