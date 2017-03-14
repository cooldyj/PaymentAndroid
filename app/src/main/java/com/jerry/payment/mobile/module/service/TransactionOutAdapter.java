package com.jerry.payment.mobile.module.service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.model.TransactionList;
import com.jerry.payment.mobile.utils.TimeUtils;

/**
 * Created by jerry on 2016/8/19.
 */
public class TransactionOutAdapter extends RecyclerView.Adapter<TransactionOutAdapter.ViewHolder> {

    private TransactionList transactionList;
    private Context context;

    public TransactionOutAdapter(Context mContext, TransactionList transactionList) {
        this.context = mContext;
        this.transactionList = transactionList;
    }

    public void resetData(TransactionList transactionList){
        this.transactionList = transactionList;
    }

    @Override
    public TransactionOutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.service_transaction_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return transactionList == null || transactionList.getList() == null ? 0 : transactionList.getList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView typeTV;
        TextView numTV;
        TextView balanceTV;
        TextView dateTV;

        public ViewHolder(View itemView) {
            super(itemView);
            typeTV = (TextView) itemView.findViewById(R.id.transaction_type);
            numTV = (TextView) itemView.findViewById(R.id.transaction_num);
            balanceTV = (TextView) itemView.findViewById(R.id.transaction_balance);
            dateTV = (TextView) itemView.findViewById(R.id.transaction_time);
        }
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

