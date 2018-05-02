package com.example.jinglinzichan.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.bean.FundDetailsBean;
import com.example.jinglinzichan.view.ViewHolder;

import java.util.List;

@SuppressLint("InflateParams")
public class FundDetailsAdapter extends BaseAdapter {

	private List<FundDetailsBean> data;

	public FundDetailsAdapter(List<FundDetailsBean> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public FundDetailsBean getItem(int position) {
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
					R.layout.funddetails_content, null);
		}
		final FundDetailsBean item = getItem(position);
		TextView amount = ViewHolder.get(convertView, R.id.amount);
		amount.setText(item.getCreatime().substring(0, 10));
		TextView states = ViewHolder.get(convertView, R.id.states);
		states.setText(item.getCreatime().substring(11, 19));
		TextView name = ViewHolder.get(convertView, R.id.name);
		name.setText(item.getName());
		TextView money = ViewHolder.get(convertView, R.id.money);
		TextView account_money = ViewHolder
				.get(convertView, R.id.account_money);
		account_money.setText(item.getAccount_money());

		ImageView tupian = ViewHolder.get(convertView, R.id.tupian);
		if (item.getType().equals("2")) {
			tupian.setImageResource(R.drawable.zj_3);
			money.setTextColor(0xffff750f);
			money.setText(item.getMoney());
		} else if (item.getType().equals("3")) {
			tupian.setImageResource(R.drawable.zj_1);
			money.setTextColor(0xff74b600);
			money.setText(item.getMoney());
		} else if (item.getType().equals("4")) {
			tupian.setImageResource(R.drawable.zj_1);
			money.setTextColor(0xff74b600);
			money.setText(item.getMoney());
		} else if (item.getType().equals("5")) {
			tupian.setImageResource(R.drawable.zj_2);
			money.setTextColor(0xff74b600);
			money.setText(item.getMoney());
		} else if (item.getType().equals("6")) {
			tupian.setImageResource(R.drawable.zj_2);
			money.setTextColor(0xff74b600);
			money.setText(item.getMoney());
		} else if (item.getType().equals("9")) {
			tupian.setImageResource(R.drawable.zj_2);
			money.setTextColor(0xff74b600);
			money.setText(item.getMoney());
		} else if (item.getType().equals("10")) {
			tupian.setImageResource(R.drawable.zj_2);
			money.setTextColor(0xff74b600);
			money.setText(item.getMoney());
		} else if (item.getType().equals("14")) {
			tupian.setImageResource(R.drawable.zj_2);
			money.setTextColor(0xff74b600);
			money.setText(item.getMoney());
		} else if (item.getType().equals("16")) {
			tupian.setImageResource(R.drawable.zj_2);
			money.setTextColor(0xff74b600);
			money.setText(item.getMoney());
		} else if (item.getType().equals("19")) {
			tupian.setImageResource(R.drawable.zj_2);
			money.setTextColor(0xff74b600);
			money.setText(item.getMoney());
		} else if (item.getType().equals("22")) {
			tupian.setImageResource(R.drawable.zj_2);
			money.setTextColor(0xff74b600);
			money.setText(item.getMoney());
		} else if (item.getType().equals("23")) {
			tupian.setImageResource(R.drawable.zj_2);
			money.setTextColor(0xff74b600);
			money.setText(item.getMoney());
		}

		return convertView;
	}
}
