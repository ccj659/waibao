package com.example.jinglinzichan.adapter;

import java.util.List;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.bean.HomepageBean;
import com.example.jinglinzichan.view.ViewHolder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

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
		xiangmuzijin.setText("��Ŀ��" + item.getBorrow_amount_format());

		TextView rate = ViewHolder.get(convertView, R.id.rate);
		rate.setText(item.getRate() + "%");

		TextView benxi = ViewHolder.get(convertView, R.id.benxi);

		TextView zhuangtai_lan = ViewHolder
				.get(convertView, R.id.zhuangtai_lan);

		ProgressBar progressbar_updown = ViewHolder.get(convertView,
				R.id.progressbar_updown);

		if (item.getRepayment_id().equals("1")) {
			benxi.setText("���ڻ���Ϣ");
		} else if (item.getRepayment_id().equals("2")) {
			benxi.setText("�ȶϢ");
		} else if (item.getRepayment_id().equals("3")) {
			benxi.setText("ÿ�¸�Ϣ�����ڻ���");
		} else if (item.getRepayment_id().equals("4")) {
			benxi.setText("�����̯����Ϣ�̶�");
		}

		if (qubie.equals("1")) {
			if (item.getRepayment_id().equals("1")) {
				daoqilixi.setText("Ԥ�����棺" + item.getMonth_repay_money_format());
			} else {
				if (item.getRepayment_id().equals("2")) {
					daoqilixi.setText("�»���Ϣ��"
							+ item.getMonth_repay_money_format());
				} else {
					daoqilixi.setText("�»���Ϣ��"
							+ item.getMonth_repay_money_format());
				}
			}
			touziqixian.setText(item.getRepay_time() + "����");
		} else if (qubie.equals("0")) {
			if (item.getRepayment_id().equals("1")) {
				daoqilixi.setText("Ԥ�����棺" + item.getMonth_repay_money_format());
			} else {
				if (item.getRepayment_id().equals("2")) {
					daoqilixi.setText("�»���Ϣ��"
							+ item.getMonth_repay_money_format());
				} else {
					daoqilixi.setText("�»���Ϣ��"
							+ item.getMonth_repay_money_format());
				}
			}
			touziqixian.setText(item.getRepay_time() + "����");
		} else if (qubie.equals("2")) {
			if (item.getRepayment_id().equals("1")) {
				daoqilixi.setText("Ԥ�����棺" + item.getMonth_repay_money_format());
			} else {
				if (item.getRepayment_id().equals("2")) {
					daoqilixi.setText("�»���Ϣ��"
							+ item.getMonth_repay_money_format());
				} else {
					daoqilixi.setText("�»���Ϣ��"
							+ item.getMonth_repay_money_format());
				}
			}
			touziqixian.setText(item.getU_load_money_format());
		} else if (qubie.equals("3")) {
			if (item.getRepayment_id().equals("1")) {
				daoqilixi.setText("Ԥ�����棺" + item.getMonth_repay_money());
			} else {
				if (item.getRepayment_id().equals("2")) {
					daoqilixi.setText("�»���Ϣ��" + item.getMonth_repay_money());
				} else {
					daoqilixi.setText("�»���Ϣ��" + item.getMonth_repay_money());
				}
			}
			touziqixian.setText(item.getRepay_time() + "����");
		}

		if (item.getDeal_status().equals("0")) {
			zhuangtai_lan.setText("������");
			zhuangtai_lan.setBackgroundResource(R.drawable.investment_btn01);
		} else if (item.getDeal_status().equals("5")) {
			zhuangtai_lan.setText("�ѻ���");
			zhuangtai_lan.setBackgroundResource(R.drawable.investment_btn02);
		} else if (item.getDeal_status().equals("4")) {
			zhuangtai_lan.setText("������");
			zhuangtai_lan.setBackgroundResource(R.drawable.investment_btn02);
		} else if (item.getDeal_status().equals("1")
				&& Integer.valueOf(item.getRemain_time()).intValue() > 0) {
			zhuangtai_lan.setText("Ͷ��");
			zhuangtai_lan.setBackgroundResource(R.drawable.investment_btn01);
		} else if (item.getDeal_status().equals("2")) {
			zhuangtai_lan.setText("������");
			zhuangtai_lan.setBackgroundResource(R.drawable.investment_btn02);
		} else if (item.getDeal_status().equals("3")) {
			zhuangtai_lan.setText("������");
			zhuangtai_lan.setBackgroundResource(R.drawable.investment_btn02);
		} else if (item.getDeal_status().equals("1")
				&& Integer.valueOf(item.getRemain_time()).intValue() <= 0) {
			zhuangtai_lan.setText("�ѹ���");
			zhuangtai_lan.setBackgroundResource(R.drawable.investment_btn02);
		}
		progressbar_updown.setProgress(item.getProgress_point());

		return convertView;
	}
}
