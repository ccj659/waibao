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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

//实名认证
public class RealNameAuthentication extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private EditText zhenshixingming, shenfenzhenghao;
	private LinearLayout queren_renzheng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shimingrenzheng);
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
		title.setText("实名认证");
		// 真实姓名
		zhenshixingming = (EditText) findViewById(R.id.zhenshixingming);
		// 身份证号
		shenfenzhenghao = (EditText) findViewById(R.id.shenfenzhenghao);
		// 确认设置
		queren_renzheng = (LinearLayout) findViewById(R.id.queren_renzheng);
		queren_renzheng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isopen();
			}
		});

	}

	// 实名认证
	public void isopen() {
		show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id",
				mySharedPreferences.getString("user_id", ""));
		params.addBodyParameter("idno", shenfenzhenghao.getText().toString());
		params.addBodyParameter("user_name_true", zhenshixingming.getText()
				.toString());

		pushData.httpClientSendWithToken(params, Constant.ISHE_IDNO,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						if (jo == null) {
							showMessage("连接网络失败，请检查网络");
						} else {
							hide();
							Log.e("实名认证", jo.toString());
							showMessage(jo.getString("msg"));
							if (jo.getString("status").equals("1")) {
								finish();
							}
						}
					}
				});
	}
}
