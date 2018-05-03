package com.example.jinglinzichan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.adapter.TouBiaoAdapter;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.example.jinglinzichan.view.PullDownView;
import com.example.jinglinzichan.view.PullDownView.OnPullDownListener;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//我的投标
public class TouBiaoJiLu extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private PullDownView toubiaojilu_list;
	private TouBiaoAdapter adapter;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private Map<String, Object> map;
	private String id;
	private int pageNumber = 1;
	private int yy = 0;
	private LinearLayout quanbu;
	private TextView quanbu_text;
	private View quanbu_view;
	private LinearLayout huankuan;
	private TextView huankuan_text;
	private View huankuan_view;
	private LinearLayout yihuanqing;
	private TextView yihuanqing_text;
	private View yihuanqing_view;
	private String mode = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toubiaojilu);
		// 返回键
		return_key = (LinearLayout) findViewById(R.id.return_key);
		return_key.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 标题字体
		title = (TextView) findViewById(R.id.title);
		title.setText("我的投标");
		// 全部
		quanbu = (LinearLayout) findViewById(R.id.quanbu);
		quanbu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mode = "";
				quanbu_text.setTextColor(getResources().getColor(R.color.red));
				huankuan_text.setTextColor(0xff8d8d8d);
				yihuanqing_text.setTextColor(0xff8d8d8d);
				quanbu_view.setVisibility(View.VISIBLE);
				huankuan_view.setVisibility(View.INVISIBLE);
				yihuanqing_view.setVisibility(View.INVISIBLE);
				data.clear();
				adapter.notifyDataSetChanged();
				isopen();
			}
		});
		quanbu_text = (TextView) findViewById(R.id.quanbu_text);
		quanbu_view = (View) findViewById(R.id.quanbu_view);
		// 还款中
		huankuan = (LinearLayout) findViewById(R.id.huankuan);
		huankuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mode = "ing";
				quanbu_text.setTextColor(0xff8d8d8d);
				huankuan_text.setTextColor(getResources().getColor(R.color.red));
				yihuanqing_text.setTextColor(0xff8d8d8d);
				quanbu_view.setVisibility(View.INVISIBLE);
				huankuan_view.setVisibility(View.VISIBLE);
				yihuanqing_view.setVisibility(View.INVISIBLE);
				data.clear();
				adapter.notifyDataSetChanged();
				isopen();
			}
		});
		huankuan_text = (TextView) findViewById(R.id.huankuan_text);
		huankuan_view = (View) findViewById(R.id.huankuan_view);
		// 已还清
		yihuanqing = (LinearLayout) findViewById(R.id.yihuanqing);
		yihuanqing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mode = "over";
				quanbu_text.setTextColor(0xff8d8d8d);
				huankuan_text.setTextColor(0xff8d8d8d);
				yihuanqing_text.setTextColor(getResources().getColor(R.color.red));
				quanbu_view.setVisibility(View.INVISIBLE);
				huankuan_view.setVisibility(View.INVISIBLE);
				yihuanqing_view.setVisibility(View.VISIBLE);
				data.clear();
				adapter.notifyDataSetChanged();
				isopen();
			}
		});
		yihuanqing_text = (TextView) findViewById(R.id.yihuanqing_text);
		yihuanqing_view = (View) findViewById(R.id.yihuanqing_view);
		// 列表
		toubiaojilu_list = (PullDownView) findViewById(R.id.toubiaojilu_list);
		// 数据监听
		toubiaojilu_list.setOnPullDownListener(new OnPullDownListener() {

			@Override
			public void onRefresh() {
				// 刷新方法
				pageNumber = 1;
				yy = 0;
				data = new ArrayList<Map<String, Object>>();
				adapter = new TouBiaoAdapter(TouBiaoJiLu.this, data);
				toubiaojilu_list.getListView().setAdapter(adapter);
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
		toubiaojilu_list.getListView().setOnItemClickListener(
				new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View v,
							int position, long arg3) {
						map = data.get(position - 1);
						id = (String) map.get("id");
						// 跳转
						Intent intent = new Intent(TouBiaoJiLu.this,
								XiangQing.class);
						intent.putExtra("id", id);
						intent.putExtra("load_id", (String) map.get("load_id"));
						startActivity(intent);
					}
				});

		isopen();
	}

	// 我的投标
	public void isopen() {
		show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id",
				mySharedPreferences.getString("user_id", ""));
		params.addBodyParameter("p", pageNumber + "");
		params.addBodyParameter("mode", mode);

		pushData.httpClientSendWithToken(params, Constant.INVEST,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide();// 隐藏加载中对话框
						if (jo == null) {
							showMessage("连接网络失败，请检查网络");
						} else {
							Log.e("我的投标", jo.toString());
							for (int i = 0; i < jo.getJSONArray("data")
									.length(); i++) {
								JSONObject jsonObject = jo.getJSONArray("data")
										.getJSONObject(i);
								id = jsonObject.getString("id");

								map = new HashMap<String, Object>();
								// 一条新闻的字典
								map.put("id", id);
								if (jsonObject.getString("is_tou").equals("1")) {
									map.put("name",
											jsonObject.getString("name")
													+ "-投资有效");
								} else {
									map.put("name",
											jsonObject.getString("name"));
								}
								map.put("repay_time",
										jsonObject.getString("repay_time")
												+ "个月");
								map.put("rate",
										"利率：" + jsonObject.getString("rate")
												+ "%");
								map.put("u_load_money",
										"已投￥"
												+ jsonObject
														.getString("u_load_money"));
								map.put("interest_amount",
										jsonObject.getString("interest_amount"));
								map.put("deal_status",
										jsonObject.getString("deal_status"));
								map.put("progress_point",
										jsonObject.getInt("progress_point"));
								map.put("remain_time",
										jsonObject.getString("remain_time"));
								map.put("repayment_id",
										jsonObject.getString("repayment_id"));
								map.put("is_tou",
										jsonObject.getString("is_tou"));
								map.put("Interest_date",
										jsonObject.getString("Interest_date"));
								map.put("load_id",
										jsonObject.getString("load_id"));
								
								data.add(map);// 放入列表
							}
							adapter = new TouBiaoAdapter(TouBiaoJiLu.this, data);
							toubiaojilu_list.getListView().setAdapter(adapter);
							toubiaojilu_list.getListView()
									.setSelection(yy * 10);
							toubiaojilu_list.RefreshComplete();
							toubiaojilu_list.notifyDidMore();
							toubiaojilu_list.setShowFooter();
						}
					}
				});
	}
}
