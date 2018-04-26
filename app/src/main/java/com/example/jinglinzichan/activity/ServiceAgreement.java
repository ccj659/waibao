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
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

//服务协议
public class ServiceAgreement extends BaseActivity {

	private LinearLayout return_key;
	private TextView title, text_web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultation_xiangqing);
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
		title.setText("服务协议");
		// 内容
		text_web = (TextView) findViewById(R.id.text_web);
		text_web.setMovementMethod(ScrollingMovementMethod.getInstance()); 
		
		isopen();
	}

	// 资讯详情
	public void isopen() {
		show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", "31");

		pushData.httpClientSendWithToken(params, Constant.NEWS_ARTICLE,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide(); // 隐藏加载中对话框
						if (jo == null) {
							showMessage("连接网络失败，请检查网络");
						} else {
							Log.e("服务协议", jo.toString());
							String status = jo.getString("status");
							if (status.equals("1")) {
								JSONObject news_article = jo
										.getJSONObject("news_article");
								text_web.setText(Html.fromHtml(news_article
										.getString("sj_cnt")));
							}
						}
					}
				});
	}
}
