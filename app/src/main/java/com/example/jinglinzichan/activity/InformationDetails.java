package com.example.jinglinzichan.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

//��Ѷ����
public class InformationDetails extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private String id;
	private TextView text_web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultation_xiangqing);
		initUI();
	}

	public void initUI() {
		// ��������
		id = getIntent().getStringExtra("id");
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
		title.setText("��Ѷ����");
		// ����
		text_web = (TextView) findViewById(R.id.text_web);
		isopen();
	}

	// ��Ѷ����
	public void isopen() {
		show(); // ��ʾ�����жԻ���
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);

		pushData.httpClientSendWithToken(params, Constant.NEWS_ARTICLE,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide();
						if (jo == null) {
							showMessage("��������ʧ�ܣ���������");
						} else {
							Log.e("��Ѷ����", jo.toString());
							if (jo.getString("status").equals("1")) {
								text_web.setText(Html.fromHtml(jo
										.getJSONObject("news_article")
										.getString("sj_cnt")));
							} else {
								showMessage(jo.getString("msg"));
							}
						}
					}
				});
	}
}
