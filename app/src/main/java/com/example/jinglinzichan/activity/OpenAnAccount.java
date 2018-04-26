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

//����
@SuppressLint("SetJavaScriptEnabled")
public class OpenAnAccount extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private WebView openanaccount_web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.openanaccount);
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
		title.setText("����");
		// ����
		openanaccount_web = (WebView) findViewById(R.id.openanaccount_web);
		// ��webview��������
		WebSettings settingswebxiaotieshi = openanaccount_web.getSettings();
		// �������������
		settingswebxiaotieshi.setBuiltInZoomControls(true);
		// ֧��js
		settingswebxiaotieshi.setJavaScriptEnabled(true);
		// ������ҳĬ�����ֱ���
		settingswebxiaotieshi.setDefaultTextEncodingName("utf-8");
		// ���������Ϊ0ָ��������ռ�ÿռ䣬ֱ�Ӹ�������ҳ��
		openanaccount_web.setScrollBarStyle(0);
		// �Ƿ���Ի�ȡ����
		openanaccount_web.requestFocus();
		// �����Զ���WebViewClient,ʵ�����ع���
		openanaccount_web.setWebViewClient(new MyWebViewClient());

		// ��Ҫ���ʵ���ַ
		String url = Constant.SERVER_URL1 + Constant.YMD_ACCOUNT;
		// post������Ҫ�ύ�Ĳ���
		String postDate = "user_id="
				+ mySharedPreferences.getString("user_id", "");
		// ͨ��EncodingUtils.getBytes(data, charset)��������ת��
		openanaccount_web.postUrl(url,
				EncodingUtils.getBytes(postDate, "BASE64"));
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
