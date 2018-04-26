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

//����
@SuppressLint("SetJavaScriptEnabled")
public class Withdrawals extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private WebView withdrawals_web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.withdrawals);
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
		title.setText("����");

		// ����
		withdrawals_web = (WebView) findViewById(R.id.withdrawals_web);
		// ��webview��������
		WebSettings settingswebxiaotieshi = withdrawals_web.getSettings();
		// �������������
		settingswebxiaotieshi.setBuiltInZoomControls(true);
		// ֧��js
		settingswebxiaotieshi.setJavaScriptEnabled(true);
		// ������ҳĬ�����ֱ���
		settingswebxiaotieshi.setDefaultTextEncodingName("GBK");
		// ���������Ϊ0ָ��������ռ�ÿռ䣬ֱ�Ӹ�������ҳ��
		withdrawals_web.setScrollBarStyle(0);
		// �Ƿ���Ի�ȡ����
		withdrawals_web.requestFocus();
		// �����Զ���WebViewClient,ʵ�����ع���
		withdrawals_web.setWebViewClient(new MyWebViewClient());

		// ��Ҫ���ʵ���ַ
		String url = Constant.SERVER_URL1 + Constant.CARRY;
		// post������Ҫ�ύ�Ĳ���
		String postDate = "user_id="
				+ mySharedPreferences.getString("user_id", "") + "&bid="
				+ getIntent().getStringExtra("bid");
		// ͨ��EncodingUtils.getBytes(data, charset)��������ת��
		withdrawals_web
				.postUrl(url, EncodingUtils.getBytes(postDate, "BASE64"));

		// ������Ӧjs ��Alert()����
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

			// ������Ӧjs ��Confirm()����
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
	 * ��дWebViewClient,�Զ���webview�ڵ��¼����ع���
	 */
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.indexOf("tel:") < 0) {// ҳ���������ֻᵼ�����ӵ绰
				view.loadUrl(url);
			}
			return true;
		}
	}
}
