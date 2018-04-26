package com.example.jinglinzichan.adapter;

import java.util.List;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.bean.TenderDetailsBean;
import com.example.jinglinzichan.view.ViewHolder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class TenderDetailsAdapter extends BaseAdapter {

	private List<TenderDetailsBean> data;

	public TenderDetailsAdapter(List<TenderDetailsBean> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public TenderDetailsBean getItem(int position) {
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
					R.layout.tenderdetails_content, null);
		}
		final TenderDetailsBean item = getItem(position);
		TextView user_name_true = ViewHolder.get(convertView,
				R.id.user_name_true);
		user_name_true.setText(item.getUser_name_true());
		TextView num_nh = ViewHolder.get(convertView, R.id.num_nh);
		num_nh.setText("年化金额：" + item.getNum_nh() + "元");

		return convertView;
	}
}
