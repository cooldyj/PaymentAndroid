package com.jerry.payment.mobile.module.service;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.model.TransactionList;
import com.jerry.payment.mobile.utils.TimeUtils;


/**
 * 交易记录Adapter
 * Created by jerry on 2016/7/25.
 */
public class TransactionListAdapter extends BaseAdapter {

    private TransactionList transactionList;
    private Context context;

    public TransactionListAdapter(Context context, TransactionList transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    public void resetData(TransactionList transactionList){
        this.transactionList = transactionList;
    }

    @Override
    public int getCount() {
        return transactionList == null || transactionList.getList() == null ? 0 : transactionList.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return transactionList == null || transactionList.getList() == null ? 0 : transactionList.getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.service_transaction_item, null);
            holder.typeTV = (TextView) convertView.findViewById(R.id.transaction_type);
            holder.numTV = (TextView) convertView.findViewById(R.id.transaction_num);
            holder.balanceTV = (TextView) convertView.findViewById(R.id.transaction_balance);
            holder.dateTV = (TextView) convertView.findViewById(R.id.transaction_time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        TransactionList.ListBean info = transactionList.getList().get(position);
        if(info != null){
            if(info.getTransactionType() == 0){
                holder.typeTV.setText(context.getString(R.string.service_outflow));
            }else {
                holder.typeTV.setText(context.getString(R.string.service_inflow));
            }
            holder.numTV.setText(getStringText(String.valueOf(info.getTransactionNumber()), info.getCurrency()));
            StringBuilder sb = new StringBuilder();
            sb.append(context.getString(R.string.service_transaction_balance))
                    .append(info.getBalance()).append(info.getCurrency());
            holder.balanceTV.setText(sb);
            holder.dateTV.setText(TimeUtils.getTimeFromStampWithHour(info.getTransactionDate()));
        }
        return convertView;
    }

    class ViewHolder{
        TextView typeTV;
        TextView numTV;
        TextView balanceTV;
        TextView dateTV;
    }

    private SpannableStringBuilder getStringText(String amount, String currency){
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(amount).append(currency);

        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(context.getResources().getColor(R.color.list_item_text_color));
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(context.getResources().getColor(R.color.list_item_text_color));

        ssb.setSpan(colorSpan1, 0, amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(colorSpan2, amount.length(), ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ssb;
    }
}
