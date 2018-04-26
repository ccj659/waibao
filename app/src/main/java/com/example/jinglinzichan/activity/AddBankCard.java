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

//������п�
@SuppressLint("SetJavaScriptEnabled")
public class AddBankCard extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private WebView addbankcard_web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addbankcard);
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
		title.setText("������п�");

		// ����
		addbankcard_web = (WebView) findViewById(R.id.addbankcard_web);
		// ��webview��������
		WebSettings settingswebxiaotieshi = addbankcard_web.getSettings();
		// �������������
		settingswebxiaotieshi.setBuiltInZoomControls(true);
		// ֧��js
		settingswebxiaotieshi.setJavaScriptEnabled(true);
		// ������ҳĬ�����ֱ���
		settingswebxiaotieshi.setDefaultTextEncodingName("GBK");
		// ���������Ϊ0ָ��������ռ�ÿռ䣬ֱ�Ӹ�������ҳ��
		addbankcard_web.setScrollBarStyle(0);
		// �Ƿ���Ի�ȡ����
		addbankcard_web.requestFocus();
		// �����Զ���WebViewClient,ʵ�����ع���
		addbankcard_web.setWebViewClient(new MyWebViewClient());

		// ��Ҫ���ʵ���ַ
		String url = Constant.SERVER_URL + Constant.ADDBANK;
		// post������Ҫ�ύ�Ĳ���
		String postDate = "user_id="
				+ mySharedPreferences.getString("user_id", "");
		// ͨ��EncodingUtils.getBytes(data, charset)��������ת��
		addbankcard_web
				.postUrl(url, EncodingUtils.getBytes(postDate, "BASE64"));
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
