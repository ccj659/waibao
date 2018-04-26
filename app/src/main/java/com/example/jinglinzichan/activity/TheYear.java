package com.example.jinglinzichan.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.adapter.TheYearAdapter;
import com.example.jinglinzichan.bean.TheYearBean;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;

//�ҵ�����
@SuppressLint("InflateParams")
public class TheYear extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private ListView theyear_list;
	private List<TheYearBean> theyear;
	private TheYearAdapter adapter;
	private TextView leijiyaoqing;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theyear);
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
		title.setText("�ҵ�����");
		// �ۼ�����
		leijiyaoqing = (TextView) findViewById(R.id.leijiyaoqing);
		// �б�
		theyear_list = (ListView) findViewById(R.id.theyear_list);

		// ��������
		id = getIntent().getStringExtra("id");

		initData();
	}

	// �����껯�����ϸ
	public void initData() {
		show();
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", id);

		pushData.httpClientSendWithToken(params, Constant.USER_WEL,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide();
						if (jo == null) {
							showMessage("��������ʧ�ܣ���������");
						} else {
							Log.e("�����껯�����ϸ", jo.toString());
							leijiyaoqing.setText(jo.getJSONObject("data")
									.getString("user_name_true") + "�껯���ϸĿ��");
							theyear = new Gson().fromJson(
									jo.getJSONObject("data").getString("list"),
									new TypeToken<ArrayList<TheYearBean>>() {
									}.getType());
							adapter = new TheYearAdapter(theyear);
							theyear_list.setAdapter(adapter);
						}
					}
				});
	}
}
