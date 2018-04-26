package com.example.jinglinzichan.activity;

import org.apache.http.util.EncodingUtils;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.utils.Constant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

//充值
@SuppressLint("SetJavaScriptEnabled")
public class Recharge extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private WebView recharge_web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recharge);
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
		title.setText("充值");
		// 内容
		recharge_web = (WebView) findViewById(R.id.recharge_web);
		// 对webview进行设置
		WebSettings settingswebxiaotieshi = recharge_web.getSettings();
		// 允许浏览器缩放
		settingswebxiaotieshi.setBuiltInZoomControls(true);
		// 支持js
		settingswebxiaotieshi.setJavaScriptEnabled(true);
		// 设置网页默认文字编码
		settingswebxiaotieshi.setDefaultTextEncodingName("utf-8");
		// 滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
		recharge_web.setScrollBarStyle(0);
		// 是否可以获取焦点
		recharge_web.requestFocus();
		// 配置自定义WebViewClient,实现拦截过滤
		recharge_web.setWebViewClient(new MyWebViewClient());

		// 需要访问的网址
		String url = Constant.SERVER_URL1 + Constant.INCHARGE;
		// post访问需要提交的参数
		String postDate = "user_id="
				+ mySharedPreferences.getString("user_id", "");
		// 通过EncodingUtils.getBytes(data, charset)方法进行转换
		recharge_web.postUrl(url, EncodingUtils.getBytes(postDate, "BASE64"));
	}

	/**
	 * 重写WebViewClient,自定义webview内的事件拦截过滤
	 */
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.indexOf("tel:") < 0) {// 页面上有数字会导致连接电话
				view.loadUrl(url);
			}
			return true;
		}
	}
}
