package com.example.jinglinzichan.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.bean.HomepageBean;
import com.example.jinglinzichan.view.ViewHolder;

import java.util.List;

@SuppressLint("InflateParams")
public class HomePageAdapter extends BaseAdapter {

	private List<HomepageBean> data;
	private String qubie;

	public HomePageAdapter(List<HomepageBean> data, String qubie) {
		this.data = data;
		this.qubie = qubie;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public HomepageBean getItem(int position) {
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
					R.layout.homepage_content, null);
		}
		final HomepageBean item = getItem(position);
		TextView name = ViewHolder.get(convertView, R.id.name);
		name.setText(item.getName());
		TextView touziqixian = ViewHolder.get(convertView, R.id.touziqixian);

		TextView daoqilixi = ViewHolder.get(convertView, R.id.daoqilixi);

		TextView xiangmuzijin = ViewHolder.get(convertView, R.id.xiangmuzijin);
		xiangmuzijin.setText("项目金额：" + item.getBorrow_amount_format());

		TextView rate = ViewHolder.get(convertView, R.id.rate);
		rate.setText(item.getRate() + "%");

		TextView benxi = ViewHolder.get(convertView, R.id.benxi);

		TextView zhuangtai_lan = ViewHolder
				.get(convertView, R.id.zhuangtai_lan);

		ProgressBar progressbar_updown = ViewHolder.get(convertView,
				R.id.progressbar_updown);

		if (item.getRepayment_id().equals("1")) {
			benxi.setText("到期还本息");
		} else if (item.getRepayment_id().equals("2")) {
			benxi.setText("等额本息");
		} else if (item.getRepayment_id().equals("3")) {
			benxi.setText("每月付息，到期还本");
		} else if (item.getRepayment_id().equals("4")) {
			benxi.setText("本金均摊，利息固定");
		}

		if (qubie.equals("1")) {
			if (item.getRepayment_id().equals("1")) {
				daoqilixi.setText("预期收益：" + item.getMonth_repay_money_format());
			} else {
				if (item.getRepayment_id().equals("2")) {
					daoqilixi.setText("月还本息："
							+ item.getMonth_repay_money_format());
				} else {
					daoqilixi.setText("月还利息："
							+ item.getMonth_repay_money_format());
				}
			}
			touziqixian.setText(item.getRepay_time() + "个月");
		} else if (qubie.equals("0")) {
			if (item.getRepayment_id().equals("1")) {
				daoqilixi.setText("预期收益：" + item.getMonth_repay_money_format());
			} else {
				if (item.getRepayment_id().equals("2")) {
					daoqilixi.setText("月还本息："
							+ item.getMonth_repay_money_format());
				} else {
					daoqilixi.setText("月还利息："
							+ item.getMonth_repay_money_format());
				}
			}
			touziqixian.setText(item.getRepay_time() + "个月");
		} else if (qubie.equals("2")) {
			if (item.getRepayment_id().equals("1")) {
				daoqilixi.setText("预期收益：" + item.getMonth_repay_money_format());
			} else {
				if (item.getRepayment_id().equals("2")) {
					daoqilixi.setText("月还本息："
							+ item.getMonth_repay_money_format());
				} else {
					daoqilixi.setText("月还利息："
							+ item.getMonth_repay_money_format());
				}
			}
			touziqixian.setText(item.getU_load_money_format());
		} else if (qubie.equals("3")) {
			if (item.getRepayment_id().equals("1")) {
				daoqilixi.setText("预期收益：" + item.getMonth_repay_money());
			} else {
				if (item.getRepayment_id().equals("2")) {
					daoqilixi.setText("月还本息：" + item.getMonth_repay_money());
				} else {
					daoqilixi.setText("月还利息：" + item.getMonth_repay_money());
				}
			}
			touziqixian.setText(item.getRepay_time() + "个月");
		}

		if (item.getDeal_status().equals("0")) {
			zhuangtai_lan.setText("申请中");
			zhuangtai_lan.setBackgroundResource(R.drawable.btn_bg_circle);
		} else if (item.getDeal_status().equals("5")) {
			zhuangtai_lan.setText("已还清");
			zhuangtai_lan.setBackgroundResource(R.drawable.btn_bg_circle_dark);
		} else if (item.getDeal_status().equals("4")) {
			zhuangtai_lan.setText("还款中");
			zhuangtai_lan.setBackgroundResource(R.drawable.btn_bg_circle_dark);
		} else if (item.getDeal_status().equals("1")
				&& Integer.valueOf(item.getRemain_time()).intValue() > 0) {
			zhuangtai_lan.setText("投资");
			zhuangtai_lan.setBackgroundResource(R.drawable.btn_bg_circle);
		} else if (item.getDeal_status().equals("2")) {
			zhuangtai_lan.setText("已满标");
			zhuangtai_lan.setBackgroundResource(R.drawable.btn_bg_circle_dark);
		} else if (item.getDeal_status().equals("3")) {
			zhuangtai_lan.setText("已流标");
			zhuangtai_lan.setBackgroundResource(R.drawable.btn_bg_circle_dark);
		} else if (item.getDeal_status().equals("1")
				&& Integer.valueOf(item.getRemain_time()).intValue() <= 0) {
			zhuangtai_lan.setText("已过期");
			zhuangtai_lan.setBackgroundResource(R.drawable.btn_bg_circle_dark);
		}
		progressbar_updown.setProgress(item.getProgress_point());

		return convertView;
	}
}
