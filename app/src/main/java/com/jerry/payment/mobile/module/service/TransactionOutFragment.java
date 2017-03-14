package com.jerry.payment.mobile.module.service;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.common.Urls;
import com.jerry.payment.mobile.model.TransactionList;
import com.jerry.payment.mobile.module.base.BaseFragment;
import com.jerry.payment.mobile.okhttp.OkHttpUtils;
import com.jerry.payment.mobile.okhttp.callback.GsonCallBack;
import com.jerry.payment.mobile.utils.IntentUtils;
import com.jerry.payment.mobile.utils.Logs;

import okhttp3.Call;

/**
 * 转出记录Fragment
 * Created by jerry on 2016/7/25.
 */
public class TransactionOutFragment extends BaseFragment {

    private View rootView;
    private ListView listView;
    private TransactionListAdapter adapter;
    private TransactionList transactionList;
    private ProgressBar progressBar;

    public static TransactionOutFragment newInstance() {
        Bundle args = new Bundle();
        TransactionOutFragment fragment = new TransactionOutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initData(){
        transactionList = new TransactionList();
        adapter = new TransactionListAdapter(mContext, transactionList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.transaction_fragment, null);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView){
        progressBar = (ProgressBar) rootView.findViewById(R.id.app_progressbar);
        listView = (ListView) rootView.findViewById(R.id.transaction_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentUtils.startActivity(mContext, TransactionDetailActivity.class);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        loadData();
        loadDummyData();
    }

    private void loadData(){
        OkHttpUtils.get()
                .url(Urls.TRANSACTION_ALL)
                .build()
                .execute(new GsonCallBack<TransactionList>(TransactionList.class) {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logs.i("onError---" + e.getMessage());
                    }

                    @Override
                    public void onResponse(TransactionList response, int id) {
                        Logs.i("onResponse");
                        if (response != null && response.getList() != null && response.getList().size() > 0) {
                            if (transactionList.getList() != null)
                                transactionList.getList().clear();
                            transactionList = response;
                            adapter.resetData(transactionList);
                            adapter.notifyDataSetChanged();
                        } else {
                            Logs.i("data null");
                            if (transactionList.getList() != null)
                                transactionList.getList().clear();
                        }
                    }
                });
    }

    private void loadDummyData(){
        Gson gson = new Gson();
        TransactionList data = gson.fromJson("{ \"pageSize\":60, \"pageNo\":0, \"pageCount\":187, \"list\":[{ \"transactionId\":12312311, \"transactionType\":0, \"transactionNumber\":500.00, \"currency\":\"EUR\", \"balance\":600.12, \"transactionDate\":1470894118973 },{ \"transactionId\":12312313, \"transactionType\":0, \"transactionNumber\":20.22, \"currency\":\"EUR\", \"balance\":2200.66, \"transactionDate\":1470844113234 },{ \"transactionId\":12312315, \"transactionType\":0, \"transactionNumber\":20.22, \"currency\":\"EUR\", \"balance\":2200.66, \"transactionDate\":1470814111234 }] }", TransactionList.class);
        adapter.resetData(data);
        adapter.notifyDataSetChanged();
    }

}
