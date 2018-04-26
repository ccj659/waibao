package com.example.jinglinzichan.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.activity.XiangQing;
import com.example.jinglinzichan.adapter.InvestmentFragmentsAdapter;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.example.jinglinzichan.view.HkDialogLoading;
import com.example.jinglinzichan.view.PullDownView;
import com.example.jinglinzichan.view.PullDownView.OnPullDownListener;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

//借款投资
@SuppressLint("InflateParams")
public class InvestmentFragments extends Fragment implements BusinessResponse {

	private TextView title;
	private LinearLayout return_key;
	private PullDownView loaninvestment_list;
	private InvestmentFragmentsAdapter adapter;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private Map<String, Object> map;
	private String id;
	private String qubie = "0";
	private HkDialogLoading dialogLoading;
	private int pageNumber = 1;
	private int yy = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.loaninvestment, null);
		dialogLoading = new HkDialogLoading(getActivity());
		// 返回键
		return_key = (LinearLayout) v.findViewById(R.id.return_key);
		return_key.setVisibility(View.INVISIBLE);
		// 标题字体
		title = (TextView) v.findViewById(R.id.title);
		title.setText("借款投资");
		// 列表
		loaninvestment_list = (PullDownView) v
				.findViewById(R.id.loaninvestment_list);
		// 数据监听
		loaninvestment_list.setOnPullDownListener(new OnPullDownListener() {

			@Override
			public void onRefresh() {
				// 刷新方法
				pageNumber = 1;
				yy = 0;
				data = new ArrayList<Map<String, Object>>();
				adapter = new InvestmentFragmentsAdapter(getActivity(), data,
						qubie);
				loaninvestment_list.getListView().setAdapter(adapter);
				isopen();
			}

			@Override
			public void onMore() {
				pageNumber++;
				yy++;
				isopen();
			}
		});

		// 选项监听
		loaninvestment_list.getListView().setOnItemClickListener(
				new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View v,
							int position, long arg3) {
						map = data.get(position - 1);
						id = (String) map.get("id");
						// 跳转
						Intent intent = new Intent(getActivity(),
								XiangQing.class);
						intent.putExtra("id", id);
						intent.putExtra("load_id", "");
						startActivity(intent);
					}
				});

		isopen();
		return v;
	}

	// 借款投资的列表
	public void isopen() {
		dialogLoading.show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("p", pageNumber + "");

		pushData.httpClientSendWithToken(params, Constant.DEAL, this);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo)
			throws JSONException {
		dialogLoading.dismiss(); // 隐藏加载中对话框
		if (jo == null) {
			Toast.makeText(getActivity(), "连接网络失败，请检查网络", Toast.LENGTH_SHORT)
					.show();
		} else {
			if (url.equals(Constant.DEAL)) {
				Log.e("借款投资的列表", jo.toString());
				JSONArray jsonArray = jo.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					id = jsonObject.getString("id");
					String name = jsonObject.getString("name");
					String repay_time = jsonObject.getString("repay_time");
					String rate = jsonObject.getString("rate");
					String borrow_amount_format = jsonObject
							.getString("borrow_amount_format");
					String month_repay_money_format = jsonObject
							.getString("month_repay_money_format");
					String deal_status = jsonObject.getString("deal_status");
					int progress_point = jsonObject.getInt("progress_point");
					String remain_time = jsonObject.getString("remain_time");
					String repayment_id = jsonObject.getString("repayment_id");

					map = new HashMap<String, Object>();
					// 一条新闻的字典
					map.put("id", id);
					map.put("name", name);
					map.put("repay_time", repay_time + "个月");
					map.put("rate", "利率：" + rate + "%");
					map.put("borrow_amount_format", "项目金额："
							+ borrow_amount_format);
					map.put("month_repay_money_format",
							month_repay_money_format);
					map.put("deal_status", deal_status);
					map.put("progress_point", progress_point);
					map.put("remain_time", remain_time);
					map.put("repayment_id", repayment_id);
					data.add(map);// 放入列表
				}
				adapter = new InvestmentFragmentsAdapter(getActivity(), data,
						qubie);
				loaninvestment_list.getListView().setAdapter(adapter);
				loaninvestment_list.getListView().setSelection(yy * 10);
				loaninvestment_list.RefreshComplete();
				loaninvestment_list.notifyDidMore();
				loaninvestment_list.setShowFooter();
			}
		}
	}
}