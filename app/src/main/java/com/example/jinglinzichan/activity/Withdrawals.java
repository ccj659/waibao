package com.example.jinglinzichan.activity;

import org.apache.http.util.EncodingUtils;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.utils.Constant;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.webkit.JsResult;

//提现
@SuppressLint("SetJavaScriptEnabled")
public class Withdrawals extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private WebView withdrawals_web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.withdrawals);
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
		title.setText("提现");

		// 内容
		withdrawals_web = (WebView) findViewById(R.id.withdrawals_web);
		// 对webview进行设置
		WebSettings settingswebxiaotieshi = withdrawals_web.getSettings();
		// 允许浏览器缩放
		settingswebxiaotieshi.setBuiltInZoomControls(true);
		// 支持js
		settingswebxiaotieshi.setJavaScriptEnabled(true);
		// 设置网页默认文字编码
		settingswebxiaotieshi.setDefaultTextEncodingName("GBK");
		// 滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
		withdrawals_web.setScrollBarStyle(0);
		// 是否可以获取焦点
		withdrawals_web.requestFocus();
		// 配置自定义WebViewClient,实现拦截过滤
		withdrawals_web.setWebViewClient(new MyWebViewClient());

		// 需要访问的网址
		String url = Constant.SERVER_URL1 + Constant.CARRY;
		// post访问需要提交的参数
		String postDate = "user_id="
				+ mySharedPreferences.getString("user_id", "") + "&bid="
				+ getIntent().getStringExtra("bid");
		// 通过EncodingUtils.getBytes(data, charset)方法进行转换
		withdrawals_web
				.postUrl(url, EncodingUtils.getBytes(postDate, "BASE64"));

		// 设置响应js 的Alert()函数
		withdrawals_web.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				AlertDialog.Builder b = new AlertDialog.Builder(
						Withdrawals.this);
				b.setTitle("Alert");
				b.setMessage(message);
				b.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
						 			int which) {
								result.confirm();
							}
						});
				b.setCancelable(false);
				b.create().show();
				return true;
			}

			// 设置响应js 的Confirm()函数
			@Override
			public boolean onJsConfirm(WebView view, String url,
					String message, final JsResult result) {
				AlertDialog.Builder b = new AlertDialog.Builder(
						Withdrawals.this);
				b.setTitle("Confirm");
				b.setMessage(message);
				b.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								result.confirm();
							}
						});
				b.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								result.cancel();
							}
						});
				b.create().show();
				return true;
			}
		});
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
