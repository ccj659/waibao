package com.example.jinglinzichan.adapter;

import java.util.List;
import java.util.Map;

import com.example.jinglinzichan.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class ChangHuanDaiKuanAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> data;
	private String panduan;

	public ChangHuanDaiKuanAdapter(Context context,
			List<Map<String, Object>> data, String panduan) {
		this.context = context;
		this.data = data;
		this.panduan = panduan;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.changhuandaikuan_content, null);
			viewHolder = new ViewHolder();
			viewHolder.changhuan_mingcheng = (TextView) convertView
					.findViewById(R.id.changhuan_mingcheng);

			viewHolder.changhuan_qixian = (TextView) convertView
					.findViewById(R.id.changhuan_qixian);

			viewHolder.changhuan_lixi = (TextView) convertView
					.findViewById(R.id.changhuan_lixi);

			viewHolder.changhuan_lilv = (TextView) convertView
					.findViewById(R.id.changhuan_lilv);

			viewHolder.changhuan_benxi = (TextView) convertView
					.findViewById(R.id.changhuan_benxi);

			viewHolder.changhuan_jine = (TextView) convertView
					.findViewById(R.id.changhuan_jine);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.changhuan_mingcheng.setText((String) data.get(position).get(
				"name"));

		viewHolder.changhuan_qixian.setText((String) data.get(position).get(
				"borrow_amount_format"));

		viewHolder.changhuan_lixi.setText((String) data.get(position).get(
				"rate"));

		viewHolder.changhuan_jine.setText((String) data.get(position).get(
				"repay_time"));

		if (panduan.equals("0")) {
			viewHolder.changhuan_benxi.setVisibility(View.VISIBLE);
			viewHolder.changhuan_lilv.setVisibility(View.VISIBLE);

			viewHolder.changhuan_benxi.setText((String) data.get(position).get(
					"month_repay_money_format"));
			if ((String) data.get(position).get("end_repay_time") == (String) data
					.get(position).get("next_repay_time")) {
				viewHolder.changhuan_lilv.setText("本期还款："
						+ (String) data.get(position).get(
								"true_last_month_repay_money"));
			} else {
				viewHolder.changhuan_lilv.setText("本期还款："
						+ (String) data.get(position).get(
								"true_month_repay_money"));
			}
		} else {
			viewHolder.changhuan_benxi.setVisibility(View.GONE);
			viewHolder.changhuan_lilv.setVisibility(View.GONE);
		}

		return convertView;
	}

	class ViewHolder {
		// 项目名称
		private TextView changhuan_mingcheng, changhuan_qixian, changhuan_lixi,
				changhuan_lilv, changhuan_benxi, changhuan_jine;
	}
}
