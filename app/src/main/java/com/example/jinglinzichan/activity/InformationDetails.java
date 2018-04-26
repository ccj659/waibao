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

//资讯详情
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
		// 接收数据
		id = getIntent().getStringExtra("id");
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
		title.setText("资讯详情");
		// 内容
		text_web = (TextView) findViewById(R.id.text_web);
		isopen();
	}

	// 资讯详情
	public void isopen() {
		show(); // 显示加载中对话框
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
							showMessage("连接网络失败，请检查网络");
						} else {
							Log.e("资讯详情", jo.toString());
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
