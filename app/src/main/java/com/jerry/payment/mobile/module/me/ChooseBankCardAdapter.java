package com.jerry.payment.mobile.module.me;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.model.BankCardInfo;

import java.util.List;

/**
 * 银行卡列表适配器
 * Created by jerry on 2016/7/19.
 */
public class ChooseBankCardAdapter extends BaseAdapter {

    private List<BankCardInfo> bankCardInfos;
    private Context context;

    public ChooseBankCardAdapter(Context context, List<BankCardInfo> bankCardInfos) {
        this.context = context;
        this.bankCardInfos = bankCardInfos;
    }

    @Override
    public int getCount() {
        return bankCardInfos == null ? 0 : bankCardInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return bankCardInfos == null ? 0 : bankCardInfos.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.me_bank_card_choose_item, null);
            holder.iconIV = (ImageView) convertView.findViewById(R.id.bank_card_img);
            holder.nameTV = (TextView) convertView.findViewById(R.id.bank_card_name);
            holder.numTV = (TextView) convertView.findViewById(R.id.bank_card_num);
            holder.typeTV = (TextView) convertView.findViewById(R.id.bank_card_type);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        BankCardInfo info = bankCardInfos.get(position);
        if(info != null){
            holder.iconIV.setImageResource(Integer.parseInt(info.getIconUrl()));
            holder.nameTV.setText(info.getBankName());
            holder.numTV.setText(info.getBankNumber());
            holder.typeTV.setText(info.getBankType());
        }
        return convertView;
    }

    class ViewHolder{
        ImageView iconIV;
        TextView nameTV;
        TextView numTV;
        TextView typeTV;
    }
}
