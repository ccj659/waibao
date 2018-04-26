package com.example.jinglinzichan.adapter;

import java.util.List;
import java.util.Map;

import com.example.jinglinzichan.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class HuanKuanAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> data;
	private int selectID;
	private OnMyCheckChangedListener mCheckChange;

	public HuanKuanAdapter(Context context, List<Map<String, Object>> data) {
		this.context = context;
		this.data = data;
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

	// �Զ����ѡ�з���
	public void setSelectID(int position) {
		selectID = position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.changhuan_contents, null);
			viewHolder = new ViewHolder();
			viewHolder.dijiqi = (TextView) convertView
					.findViewById(R.id.dijiqi);

			viewHolder.shijian = (TextView) convertView
					.findViewById(R.id.shijian);

			viewHolder.yihuanjine = (TextView) convertView
					.findViewById(R.id.yihuanjine);

			viewHolder.guanlifei = (TextView) convertView
					.findViewById(R.id.guanlifei);

			viewHolder.daihuanbenxi = (TextView) convertView
					.findViewById(R.id.daihuanbenxi);

			viewHolder.yuqifei = (TextView) convertView
					.findViewById(R.id.yuqifei);

			viewHolder.daihuanzonge = (TextView) convertView
					.findViewById(R.id.daihuanzonge);

			viewHolder.zhuangtai = (TextView) convertView
					.findViewById(R.id.zhuangtai);

			viewHolder.heduidingdan_bangding = (RadioButton) convertView
					.findViewById(R.id.heduidingdan_bangding);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.dijiqi.setText((String) data.get(position)
				.get("l_key_index"));

		viewHolder.shijian.setText((String) data.get(position).get(
				"repay_day_format"));

		viewHolder.yihuanjine.setText((String) data.get(position).get(
				"month_has_repay_money_all_format"));

		viewHolder.guanlifei.setText((String) data.get(position).get(
				"month_manage_money_format"));

		viewHolder.daihuanbenxi.setText((String) data.get(position).get(
				"month_repay_money_format"));

		viewHolder.yuqifei.setText((String) data.get(position)
				.get("repay_time"));

		viewHolder.daihuanzonge.setText((String) data.get(position).get(
				"month_need_all_repay_money_format"));

		viewHolder.zhuangtai.setText((String) data.get(position).get(
				"status_format"));

		String status = (String) data.get(position).get("status");
		if (status.equals("1")) {
			viewHolder.zhuangtai.setText("״̬����ǰ");
		} else if (status.equals("2")) {
			viewHolder.zhuangtai.setText("״̬��׼ʱ����");
		} else if (status.equals("3")) {
			viewHolder.zhuangtai.setText("״̬�����ڻ���");
		} else if (status.equals("4")) {
			viewHolder.zhuangtai.setText("״̬����������");
		} else if (status.equals("5")) {
			viewHolder.zhuangtai.setText("״̬�����ֻ���");
		} else if (status.equals("6")) {
			viewHolder.zhuangtai.setText("״̬��������");
		}

		String has_repay = (String) data.get(position).get("has_repay");
		if (has_repay.equals("1")) {
			viewHolder.heduidingdan_bangding.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.heduidingdan_bangding.setVisibility(View.VISIBLE);
			// ���ķ������жϵ�ѡ��ť�����µ�λ����֮ǰ��λ���Ƿ���ȣ�Ȼ������Ӧ�Ĳ�����
			if (selectID == position) {
				viewHolder.heduidingdan_bangding
						.setButtonDrawable(R.drawable.xuanzhong);
			} else {
				viewHolder.heduidingdan_bangding
						.setButtonDrawable(R.drawable.weixuanzhong);
			}
			viewHolder.heduidingdan_bangding
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// ��һ�����˼�������һ�������������itemClick�ĵ�����������������RadioButton�ĵ��������
							selectID = position;
							if (mCheckChange != null)
								mCheckChange.setSelectID(selectID);
						}
					});
		}

		return convertView;
	}

	// �ص�������������OnClickListener�ɣ��Ǻ�
	public void setOncheckChanged(OnMyCheckChangedListener l) {
		mCheckChange = l;
	}

	// ���{�ӿ�
	public interface OnMyCheckChangedListener {
		void setSelectID(int selectID);
	}

	class ViewHolder {
		// ��Ŀ����
		private TextView dijiqi, shijian, yihuanjine, guanlifei, daihuanbenxi,
				yuqifei, daihuanzonge, zhuangtai;
		private RadioButton heduidingdan_bangding;
	}
}
