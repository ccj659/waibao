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

//ƽ̨����
@SuppressLint("SetJavaScriptEnabled")
public class Notice extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private ListView notice_list;
	private List<NoticeBean> notice;
	private NoticeAdapter noticeadapter;
	private int ss = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice);
		initUI();
	}

	public void initUI() {
		// ���ؼ�
		return_key = (LinearLayout) findViewById(R.id.return_key);
		return_key.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// ��������
		title = (TextView) findViewById(R.id.title);
		title.setText("ƽ̨����");
		// �б�
		notice_list = (ListView) findViewById(R.id.notice_list);
		// ѡ�����
		notice_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long arg3) {
				// ��ת
				Intent intent = new Intent(Notice.this, NoticeDetails.class);
				intent.putExtra("id", notice.get(position).getId());
				startActivity(intent);
			}
		});
		isopen();
	}

	// �����б�
	public void isopen() {
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("p", "1");
		params.addBodyParameter("pid", "1");

		pushData.httpClientSendWithToken(params, Constant.NEWS_LIST_NOTICE,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						if (jo == null) {
							showMessage("��������ʧ�ܣ���������");
						} else {
							Log.e("�����б�", jo.toString());
							if (jo.getString("status").equals("1")) {
								notice = new Gson().fromJson(
										jo.getString("data"),
										new TypeToken<ArrayList<NoticeBean>>() {
										}.getType());
								noticeadapter = new NoticeAdapter(notice, ss);
								notice_list.setAdapter(noticeadapter);
							}
						}
					}
				});
	}
}
