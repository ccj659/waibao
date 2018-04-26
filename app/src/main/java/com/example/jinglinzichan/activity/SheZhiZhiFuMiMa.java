package com.example.jinglinzichan.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.example.jinglinzichan.view.HkDialogLoading;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//设置支付密码
public class SheZhiZhiFuMiMa extends Activity implements
		OnClickListener, BusinessResponse {

	private LinearLayout return_key;
	private TextView title;
	private ImageView huoquyanzhengma;
	private ImageView zhifumima_querenxiugai;
	private EditText zifumima_edit;
	private EditText zifumima_shurumima;
	private EditText zifumima_yanzhengma;
	private String zifumima_edits, zifumima_shurumimas, zifumima_yanzhengmas;
	private EditText zifumima_shoujihao;
	private SharedPreferences mySharedPreferences;
	private String user_id;
	private String zifumima_shoujihaos;
	private HkDialogLoading dialogLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shezhizhifumima);
		dialogLoading = new HkDialogLoading(this);
		// 返回键
		return_key = (LinearLayout) findViewById(R.id.return_key);
		return_key.setOnClickListener(this);
		// 标题字体
		title = (TextView) findViewById(R.id.title);
		title.setText("设置支付密码");
		// 输入密码
		zifumima_edit = (EditText) findViewById(R.id.zifumima_edit);
		zifumima_edit.setKeyListener(DialerKeyListener.getInstance());
		// 再次输入密码
		zifumima_shurumima = (EditText) findViewById(R.id.zifumima_shurumima);
		zifumima_shurumima.setKeyListener(DialerKeyListener.getInstance());
		// 手机号
		zifumima_shoujihao = (EditText) findViewById(R.id.zifumima_shoujihao);
		zifumima_shoujihao.setInputType(InputType.TYPE_CLASS_NUMBER);
		// 验证码
		zifumima_yanzhengma = (EditText) findViewById(R.id.zifumima_yanzhengma);
		zifumima_yanzhengma.setInputType(InputType.TYPE_CLASS_NUMBER);
		// 获取验证码
		huoquyanzhengma = (ImageView) findViewById(R.id.huoquyanzhengma);
		huoquyanzhengma.setOnClickListener(this);
		// 确认设置
		zhifumima_querenxiugai = (ImageView) findViewById(R.id.zhifumima_querenxiugai);
		zhifumima_querenxiugai.setOnClickListener(this);

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
		case R.id.huoquyanzhengma:
			// 手机号
			zifumima_shoujihaos = zifumima_shoujihao.getText().toString();
			zhifumimaduanxin();
			break;
		case R.id.zhifumima_querenxiugai:
			// 输入密码
			zifumima_edits = zifumima_edit.getText().toString();
			// 再次输入密码
			zifumima_shurumimas = zifumima_shurumima.getText().toString();
			// 手机号
			zifumima_shoujihaos = zifumima_shoujihao.getText().toString();
			// 验证码
			zifumima_yanzhengmas = zifumima_yanzhengma.getText().toString();
			if (!zifumima_edits.equals("") && !zifumima_shurumimas.equals("")
					&& !zifumima_shoujihao.equals("")
					&& !zifumima_yanzhengmas.equals("")
					&& zifumima_edits.equals(zifumima_shurumimas)) {
				isopen();
			} else if (zifumima_edits != zifumima_shurumimas) {
				Toast.makeText(this, "两次支付密码输入不相同,请重新输入", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(this, "请将信息补充完整", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

	// 设置支付密码
	public void isopen() {
		dialogLoading.show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("mobile", zifumima_shoujihaos);
		params.addBodyParameter("jiaoyipw", zifumima_edits);
		params.addBodyParameter("pw_code", zifumima_yanzhengmas);

		pushData.httpClientSendWithToken(params, Constant.MODIFY_PAYMENT, this);
	}

	// 设置支付密码短信
	public void zhifumimaduanxin() {
		dialogLoading.show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("mobile", zifumima_shoujihaos);

		pushData.httpClientSendWithToken(params, Constant.GET_MOBILE_CODE_PW,
				this);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo)
			throws JSONException {
		dialogLoading.dismiss(); // 隐藏加载中对话框
		if (jo == null) {
			Toast.makeText(this, "连接网络失败，请检查网络", Toast.LENGTH_SHORT).show();
		} else {
			if (url.equals(Constant.MODIFY_PAYMENT)) {
				Log.e("设置支付密码", jo.toString());
				String status = jo.getString("status");
				String msg = jo.getString("msg");
				Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
				if (status.equals("1")) {
					finish();
				}
			} else if (url.equals(Constant.GET_MOBILE_CODE_PW)) {
				Log.e("设置支付密码短信", jo.toString());
				String msg = jo.getString("msg");
				Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
			}
		}
	}
}
