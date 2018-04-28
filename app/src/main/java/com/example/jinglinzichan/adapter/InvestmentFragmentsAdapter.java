package com.example.jinglinzichan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jinglinzichan.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;
import java.util.Map;

@SuppressLint("InflateParams")
public class InvestmentFragmentsAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> data;
	private ImageLoader imageLoader;
	private String qubie;

	public InvestmentFragmentsAdapter(Context context,
			List<Map<String, Object>> data, String qubie) {
		this.context = context;
		this.data = data;
		this.qubie = qubie;
		initImageLoader();
	}

	private void initImageLoader() {
		@SuppressWarnings("deprecation")
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				context).defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new WeakMemoryCache());

		ImageLoaderConfiguration config = builder.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
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
					R.layout.homepage_content, null);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);

			viewHolder.touziqixian = (TextView) convertView
					.findViewById(R.id.touziqixian);

			viewHolder.daoqilixi = (TextView) convertView
					.findViewById(R.id.daoqilixi);
			
			viewHolder.rate = (TextView) convertView
					.findViewById(R.id.rate);

			viewHolder.xiangmuzijin = (TextView) convertView
					.findViewById(R.id.xiangmuzijin);

			viewHolder.benxi = (TextView) convertView.findViewById(R.id.benxi);

			viewHolder.zhuangtai_lan = (TextView) convertView
					.findViewById(R.id.zhuangtai_lan);

			viewHolder.myProgressBar = (ProgressBar) convertView
					.findViewById(R.id.progressbar_updown);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.name.setText((String) data.get(position).get("name"));
		viewHolder.rate.setText((String) data.get(position).get("rate"));

		viewHolder.xiangmuzijin.setText((String) data.get(position).get(
				"borrow_amount_format"));

		String repayment_id = data.get(position).get("repayment_id").toString();
		if (repayment_id.equals("1")) {
			viewHolder.benxi.setText("到期还本息");
		} else if (repayment_id.equals("2")) {
			viewHolder.benxi.setText("等额本息");
		} else if (repayment_id.equals("3")) {
			viewHolder.benxi.setText("每月付息，到期还本");
		} else if (repayment_id.equals("4")) {
			viewHolder.benxi.setText("本金均摊，利息固定");
		}

		if (qubie.equals("1")) {
			if (repayment_id.equals("1")) {
				viewHolder.daoqilixi.setText("预期收益："
						+ (String) data.get(position).get(
								"month_repay_money_format"));
			} else {
				if (repayment_id.equals("2")) {
					viewHolder.daoqilixi.setText("月还本息："
							+ (String) data.get(position).get(
									"month_repay_money_format"));
				} else {
					viewHolder.daoqilixi.setText("月还利息："
							+ (String) data.get(position).get(
									"month_repay_money_format"));
				}
			}

			viewHolder.touziqixian.setText((String) data.get(position).get(
					"repay_time"));
		} else if (qubie.equals("0")) {
			if (repayment_id.equals("1")) {
				viewHolder.daoqilixi.setText("预期收益："
						+ (String) data.get(position).get(
								"month_repay_money_format"));
			} else {
				if (repayment_id.equals("2")) {
					viewHolder.daoqilixi.setText("月还本息："
							+ (String) data.get(position).get(
									"month_repay_money_format"));
				} else {
					viewHolder.daoqilixi.setText("月还利息："
							+ (String) data.get(position).get(
									"month_repay_money_format"));
				}
			}

			viewHolder.touziqixian.setText((String) data.get(position).get(
					"repay_time"));
		} else if (qubie.equals("2")) {
			if (repayment_id.equals("1")) {
				viewHolder.daoqilixi.setText("预期收益："
						+ (String) data.get(position).get(
								"month_repay_money_format"));
			} else {
				if (repayment_id.equals("2")) {
					viewHolder.daoqilixi.setText("月还本息："
							+ (String) data.get(position).get(
									"month_repay_money_format"));
				} else {
					viewHolder.daoqilixi.setText("月还利息："
							+ (String) data.get(position).get(
									"month_repay_money_format"));
				}
			}

			viewHolder.touziqixian.setText((String) data.get(position).get(
					"u_load_money_format"));
		} else if (qubie.equals("3")) {
			if (repayment_id.equals("1")) {
				viewHolder.daoqilixi.setText("预期收益："
						+ (String) data.get(position).get("month_repay_money"));
			} else {
				if (repayment_id.equals("2")) {
					viewHolder.daoqilixi.setText("月还本息："
							+ (String) data.get(position).get(
									"month_repay_money"));
				} else {
					viewHolder.daoqilixi.setText("月还利息："
							+ (String) data.get(position).get(
									"month_repay_money"));
				}
			}

			viewHolder.touziqixian.setText((String) data.get(position).get(
					"repay_time"));
		}

		String deal_status = data.get(position).get("deal_status").toString();
		String remain_time = data.get(position).get("remain_time").toString();
		if (deal_status.equals("0")) {
			viewHolder.zhuangtai_lan.setText("申请中");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.btn_bg_circle);
		} else if (deal_status.equals("5")) {
			viewHolder.zhuangtai_lan.setText("已还清");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.btn_bg_circle_dark);
		} else if (deal_status.equals("4")) {
			viewHolder.zhuangtai_lan.setText("还款中");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.btn_bg_circle_dark);
		} else if (deal_status.equals("1")
				&& Integer.valueOf(remain_time).intValue() > 0) {
			viewHolder.zhuangtai_lan.setText(" 投资 ");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.btn_bg_circle);
		} else if (deal_status.equals("2")) {
			viewHolder.zhuangtai_lan.setText("已满标");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.btn_bg_circle_dark);
		} else if (deal_status.equals("3")) {
			viewHolder.zhuangtai_lan.setText("已流标");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.btn_bg_circle_dark);
		} else if (deal_status.equals("1")
				&& Integer.valueOf(remain_time).intValue() <= 0) {
			viewHolder.zhuangtai_lan.setText("已过期");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.btn_bg_circle_dark);
		}

		int progress_point = (int) data.get(position).get("progress_point");
		viewHolder.myProgressBar.setProgress(progress_point);

		return convertView;
	}

	class ViewHolder {
		// 项目名称
		private TextView name;
		// 投资期限
		private TextView touziqixian;
		// 到期利息
		private TextView daoqilixi;
		//利率
		private TextView rate;
		// 项目金额
		private TextView xiangmuzijin;
		// 本息
		private TextView benxi;
		private TextView zhuangtai_lan;
		// 定义ProgressBar
		private ProgressBar myProgressBar;
	}
}
