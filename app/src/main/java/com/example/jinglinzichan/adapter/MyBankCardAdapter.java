package com.example.jinglinzichan.adapter;

import java.util.List;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.bean.MyBankCardBean;
import com.example.jinglinzichan.view.ViewHolder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class MyBankCardAdapter extends BaseAdapter {

	private List<MyBankCardBean> data;

	public MyBankCardAdapter(List<MyBankCardBean> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public MyBankCardBean getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.wodeyinhangka_content, null);
		}
		final MyBankCardBean item = getItem(position);
		TextView bank_name = ViewHolder.get(convertView, R.id.bank_name);
		bank_name.setText(item.getBank_name());
		TextView real_name = ViewHolder.get(convertView, R.id.real_name);
		real_name.setText(item.getReal_name());
		TextView bankcard = ViewHolder.get(convertView, R.id.bankcard);
		bankcard.setText(item.getBankcard());

		return convertView;
	}
}
