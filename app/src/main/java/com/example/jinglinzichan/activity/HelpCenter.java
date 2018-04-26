package com.example.jinglinzichan.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.adapter.NoticeAdapter;
import com.example.jinglinzichan.bean.NoticeBean;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

//帮助中心
@SuppressLint("SetJavaScriptEnabled")
public class HelpCenter extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private List<NoticeBean> notice;
	private NoticeAdapter noticeadapter;
	private ListView helppcenter_list;
	private TextView changjianwenti, zaixianbangzhu;
	private int ss = 0;
	private LinearLayout zaixianbangzhu_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helpcenter);
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
		title.setText("帮助中心");
		// 在线帮助
		zaixianbangzhu = (TextView) findViewById(R.id.zaixianbangzhu);
		zaixianbangzhu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				zaixianbangzhu.setTextColor(0xff01b1ed);
				changjianwenti.setTextColor(0xff414141);
				zaixianbangzhu_.setVisibility(View.VISIBLE);
				helppcenter_list.setVisibility(View.GONE);
			}
		});
		zaixianbangzhu_ = (LinearLayout) findViewById(R.id.zaixianbangzhu_);
		// 常见问题
		changjianwenti = (TextView) findViewById(R.id.changjianwenti);
		changjianwenti.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changjianwenti.setTextColor(0xff01b1ed);
				zaixianbangzhu.setTextColor(0xff414141);
				zaixianbangzhu_.setVisibility(View.GONE);
				helppcenter_list.setVisibility(View.VISIBLE);
				isopen();
			}
		});
		// 列表
		helppcenter_list = (ListView) findViewById(R.id.helppcenter_list);
		// 选项监听
		helppcenter_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long arg3) {
				// 跳转
				Intent intent = new Intent(HelpCenter.this, NoticeDetails.class);
				intent.putExtra("id", notice.get(position).getId());
				startActivity(intent);
			}
		});

		zaixianbangzhu_.setVisibility(View.VISIBLE);
		helppcenter_list.setVisibility(View.GONE);
	}

	// 常见问题
	public void isopen() {
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("p", "1");
		params.addBodyParameter("pid", "21");

		pushData.httpClientSendWithToken(params, Constant.NEWS_LIST_NOTICE,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						if (jo == null) {
							showMessage("连接网络失败，请检查网络");
						} else {
							Log.e("常见问题", jo.toString());
							if (jo.getString("status").equals("1")) {
								notice = new Gson().fromJson(
										jo.getString("data"),
										new TypeToken<ArrayList<NoticeBean>>() {
										}.getType());
								noticeadapter = new NoticeAdapter(notice, ss);
								helppcenter_list.setAdapter(noticeadapter);
							}
						}
					}
				});
	}
}
