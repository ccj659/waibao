package com.example.jinglinzichan.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.example.jinglinzichan.view.HkDialogLoading;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

//修改手机
public class XiuGaiShouJi extends Activity implements OnClickListener,
		BusinessResponse {

	private LinearLayout return_key;
	private TextView title;
	private EditText xiugai_jiushouji;
	private TextView xiugai_huoquyanzhengma;
	private EditText xiugai_yanzhengma;
	private EditText xiugai_shoujihao;
	private TextView xiugai_xinshouji;
	private EditText xiugai_xinyanzhengma;
	private String xiugai_jiushoujis;
	private String xiugai_shoujihaos;
	private String xiugai_yanzhengmas;
	private String xiugai_xinyanzhengmas;
	private SharedPreferences mySharedPreferences;
	private String user_id;
	private ImageView xiugaishouji;
	private HkDialogLoading dialogLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xiugaishouji);
		dialogLoading = new HkDialogLoading(this);
		// 返回键
		return_key = (LinearLayout) findViewById(R.id.return_key);
		return_key.setOnClickListener(this);
		// 标题字体
		title = (TextView) findViewById(R.id.title);
		title.setText("修改手机");
		// 旧手机
		xiugai_jiushouji = (EditText) findViewById(R.id.xiugai_jiushouji);
		xiugai_jiushouji.setKeyListener(DialerKeyListener.getInstance());
		// 获取验证码
		xiugai_huoquyanzhengma = (TextView) findViewById(R.id.xiugai_huoquyanzhengma);
		xiugai_huoquyanzhengma.setOnClickListener(this);
		// 验证码
		xiugai_yanzhengma = (EditText) findViewById(R.id.xiugai_yanzhengma);
		// 新手机
		xiugai_shoujihao = (EditText) findViewById(R.id.xiugai_shoujihao);
		xiugai_shoujihao.setKeyListener(DialerKeyListener.getInstance());
		// 获取验证码
		xiugai_xinshouji = (TextView) findViewById(R.id.xiugai_xinshouji);
		xiugai_xinshouji.setOnClickListener(this);
		// 验证码
		xiugai_xinyanzhengma = (EditText) findViewById(R.id.xiugai_xinyanzhengma);
		// 提交
		xiugaishouji = (ImageView) findViewById(R.id.xiugaishouji);
		xiugaishouji.setOnClickListener(this);

		// 提取用户名、密码
		mySharedPreferences = this.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		user_id = mySharedPreferences.getString("user_id", "");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_key:
			finish();
			break;
		case R.id.xiugai_huoquyanzhengma:
			xiugai_jiushoujis = xiugai_jiushouji.getText().toString();
			jiushouji();
			break;
		case R.id.xiugai_xinshouji:
			xiugai_shoujihaos = xiugai_shoujihao.getText().toString();
			xinshouji();
			break;
		case R.id.xiugaishouji:
			xiugai_jiushoujis = xiugai_jiushouji.getText().toString();
			xiugai_shoujihaos = xiugai_shoujihao.getText().toString();
			xiugai_yanzhengmas = xiugai_yanzhengma.getText().toString();
			xiugai_xinyanzhengmas = xiugai_xinyanzhengma.getText().toString();
			if (!xiugai_jiushoujis.equals("") && !xiugai_shoujihaos.equals("")
					&& !xiugai_yanzhengmas.equals("")
					&& !xiugai_xinyanzhengmas.equals("")) {
				isopen();
			} else if (xiugai_jiushoujis.equals("")
					&& xiugai_shoujihaos.equals("")
					&& xiugai_yanzhengmas.equals("")
					&& xiugai_xinyanzhengmas.equals("")) {
				Toast.makeText(this, "请将信息补充完整", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

	// 修改手机
	public void isopen() {
		dialogLoading.show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("old_mobile", xiugai_jiushoujis);
		params.addBodyParameter("old_code", xiugai_yanzhengmas);
		params.addBodyParameter("mobile", xiugai_shoujihaos);
		params.addBodyParameter("code", xiugai_xinyanzhengmas);

		pushData.httpClientSendWithToken(params, Constant.MODIFY_MOBILE, this);
	}

	// 旧手机号短信
	public void jiushouji() {
		dialogLoading.show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("old_mobile", xiugai_jiushoujis);

		pushData.httpClientSendWithToken(params, Constant.GET_MOBILE_CODE_BD,
				this);
	}

	// 新手机号短信
	public void xinshouji() {
		dialogLoading.show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("mobile", xiugai_shoujihaos);

		pushData.httpClientSendWithToken(params, Constant.GET_MOBILE_CODE_NEW,
				this);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo)
			throws JSONException {
		dialogLoading.dismiss(); // 隐藏加载中对话框
		if (jo == null) {
			Toast.makeText(this, "连接网络失败，请检查网络", Toast.LENGTH_SHORT).show();
		} else {
			if (url.equals(Constant.MODIFY_MOBILE)) {
				Log.e("修改手机", jo.toString());
				String status = jo.getString("status");
				String msg = jo.getString("msg");
				Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
				if (status.equals("1")) {
					finish();
				}
			} else if (url.equals(Constant.GET_MOBILE_CODE_BD)) {
				Log.e("旧手机号短信", jo.toString());
				String msg = jo.getString("msg");
				Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
			} else if (url.equals(Constant.GET_MOBILE_CODE_NEW)) {
				Log.e("新手机号短信", jo.toString());
				String msg = jo.getString("msg");
				Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
			}
		}
	}
}
