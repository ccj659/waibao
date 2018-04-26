package com.example.jinglinzichan.activity;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.adapter.MyBankCardAdapter;
import com.example.jinglinzichan.bean.MyBankCardBean;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

//我的银行卡
public class MyBankCard extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private ListView bankcard_list;
	private MyBankCardAdapter adapter;
	private List<MyBankCardBean> yinhangka;
	private LinearLayout tianjiayinhangka;
	private int s = 0;
	private String msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybankcard);
		initUI();
	}

	public void initUI() {
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
		title.setText("我的银行卡");
		tianjiayinhangka = (LinearLayout) findViewById(R.id.tianjiayinhangka);
		tianjiayinhangka.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (s == 0) {
					startActivity(new Intent(MyBankCard.this, AddBankCard.class));
				} else {
					showMessage(msg);
				}
			}
		});
		// 列表
		bankcard_list = (ListView) findViewById(R.id.bankcard_list);
		bank();
		addbank();
	}

	// 我的银行卡
	public void bank() {
		show();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id",
				mySharedPreferences.getString("user_id", ""));
		PushData.getInstance().httpClientSendWithToken(params, Constant.BANK,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide();
						if (jo == null) {
							showMessage("连接网络失败，请检查网络");
						} else {
							Log.e("我的银行卡", jo.toString());
							if (jo.getString("status").equals("1")) {
								yinhangka = new Gson().fromJson(
										jo.getString("list"),
										new TypeToken<ArrayList<MyBankCardBean>>() {
										}.getType());
								adapter = new MyBankCardAdapter(yinhangka);
								bankcard_list.setAdapter(adapter);
							}
						}
					}
				});
	}

	// 添加银行卡
	public void addbank() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id",
				mySharedPreferences.getString("user_id", ""));
		PushData.getInstance().httpClientSendWithToken(params,
				Constant.ADDBANK, new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						if (jo == null) {
							showMessage("连接网络失败，请检查网络");
						} else {
							Log.e("添加银行卡", jo.toString());
							if (jo.getString("status").equals("0")) {
								msg = jo.getString("msg");
								s = 1;
							}
						}
					}
				});
	}
}
