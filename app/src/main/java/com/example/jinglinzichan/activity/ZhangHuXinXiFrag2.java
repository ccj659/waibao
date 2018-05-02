package com.example.jinglinzichan.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.utils.Constant;

import org.apache.http.util.EncodingUtils;

import static android.content.Context.MODE_PRIVATE;

//����
@SuppressLint("SetJavaScriptEnabled")
public class ZhangHuXinXiFrag2 extends android.support.v4.app.Fragment {

	private WebView openanaccount_web;
	private View tab1view;
	private SharedPreferences mySharedPreferences;


	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		tab1view=inflater.inflate(R.layout.openanaccount, container, false);
		return tab1view;

	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initUI();


	}
	

	public void initUI() {


		mySharedPreferences = getActivity().getSharedPreferences("user", MODE_PRIVATE);

		// ����
		openanaccount_web = (WebView) tab1view.findViewById(R.id.openanaccount_web);
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
