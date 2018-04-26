package com.example.jinglinzichan.adapter;

import java.util.List;
import java.util.Map;

import com.example.jinglinzichan.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class TouBiaoAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> data;
	private ImageLoader imageLoader;

	public TouBiaoAdapter(Context context, List<Map<String, Object>> data) {
		this.context = context;
		this.data = data;
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
					R.layout.toubiao_content, null);
			viewHolder = new ViewHolder();
			viewHolder.xiangmumingcheng = (TextView) convertView
					.findViewById(R.id.name);

			viewHolder.touziqixian = (TextView) convertView
					.findViewById(R.id.touziqixian);

			viewHolder.daoqilixi = (TextView) convertView
					.findViewById(R.id.daoqilixi);

			viewHolder.xiangmuzijin = (TextView) convertView
					.findViewById(R.id.xiangmuzijin);

			viewHolder.lilv = (TextView) convertView.findViewById(R.id.rate);

			viewHolder.myProgressBar = (ProgressBar) convertView
					.findViewById(R.id.progressbar_updown);

			viewHolder.benxi = (TextView) convertView.findViewById(R.id.benxi);

			viewHolder.zhuangtai_lan = (TextView) convertView
					.findViewById(R.id.zhuangtai_lan);

			viewHolder.jixiri = (TextView) convertView
					.findViewById(R.id.jixiri);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.xiangmumingcheng.setText((String) data.get(position).get(
				"name"));

		viewHolder.xiangmuzijin.setText((String) data.get(position).get(
				"u_load_money"));

		viewHolder.lilv.setText((String) data.get(position).get("rate"));

		viewHolder.touziqixian.setText((String) data.get(position).get(
				"repay_time"));

		viewHolder.myProgressBar.incrementProgressBy((int) data.get(position)
				.get("progress_point"));

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

		if (repayment_id.equals("1")) {
			viewHolder.daoqilixi.setText("预期收益："
					+ (String) data.get(position).get("interest_amount"));
		} else {
			if (repayment_id.equals("2")) {
				viewHolder.daoqilixi.setText("月还本息："
						+ (String) data.get(position).get("interest_amount"));
			} else {
				viewHolder.daoqilixi.setText("月还利息："
						+ (String) data.get(position).get("interest_amount"));
			}
		}

		String deal_status = data.get(position).get("deal_status").toString();
		String remain_time = data.get(position).get("remain_time").toString();
		if (deal_status.equals("0")) {
			viewHolder.zhuangtai_lan.setText("申请中");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.investment_btn01);
		} else if (deal_status.equals("5")) {
			viewHolder.zhuangtai_lan.setText("已还清");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.investment_btn02);
		} else if (deal_status.equals("4")) {
			viewHolder.zhuangtai_lan.setText("还款中");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.investment_btn02);
		} else if (deal_status.equals("1")
				&& Integer.valueOf(remain_time).intValue() > 0) {
			viewHolder.zhuangtai_lan.setText("投资");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.investment_btn01);
		} else if (deal_status.equals("2")) {
			viewHolder.zhuangtai_lan.setText("已满标");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.investment_btn02);
		} else if (deal_status.equals("3")) {
			viewHolder.zhuangtai_lan.setText("已流标");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.investment_btn02);
		} else if (deal_status.equals("1")
				&& Integer.valueOf(remain_time).intValue() <= 0) {
			viewHolder.zhuangtai_lan.setText("已过期");
			viewHolder.zhuangtai_lan
					.setBackgroundResource(R.drawable.investment_btn02);
		}

		int progress_point = (int) data.get(position).get("progress_point");
		viewHolder.myProgressBar.setProgress(progress_point);

		viewHolder.jixiri.setText("计息日："
				+ data.get(position).get("Interest_date").toString());
		return convertView;
	}

	class ViewHolder {
		// 项目名称
		private TextView xiangmumingcheng;
		// 投资期限
		private TextView touziqixian;
		// 到期利息
		private TextView daoqilixi;
		// 项目金额
		private TextView xiangmuzijin, benxi;
		// 利率
		private TextView lilv;
		// 定义ProgressBar
		private ProgressBar myProgressBar;
		// 本息
		private TextView zhuangtai_lan;
		// 计息日
		private TextView jixiri;
	}
}
