//package com.example.jinglinzichan.activity;
//
//import org.apache.http.util.EncodingUtils;
//
//import com.example.jinglinzichan.R;
//import com.example.jinglinzichan.utils.Constant;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.view.View.OnClickListener;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
////������п�
//@SuppressLint("SetJavaScriptEnabled")
//public class TianJiaYinHangKa extends Activity implements
//		OnClickListener {
//
//	private LinearLayout return_key;
//	private TextView title;
//	private WebView chengzhi_web;
//	private SharedPreferences mySharedPreferences;
//	private String user_id;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.chongzhi);
//		// ���ؼ�
//		return_key = (LinearLayout) findViewById(R.id.return_key);
//		return_key.setOnClickListener(this);
//		// ��������
//		title = (TextView) findViewById(R.id.title);
//		title.setText("������п�");
//		// ����
//		chengzhi_web = (WebView) findViewById(R.id.chengzhi_web);
//		// ��webview��������
//		WebSettings settingswebxiaotieshi = chengzhi_web.getSettings();
//		// �������������
//		settingswebxiaotieshi.setBuiltInZoomControls(true);
//		// ֧��js
//		settingswebxiaotieshi.setJavaScriptEnabled(true);
//		// ������ҳĬ�����ֱ���
//		settingswebxiaotieshi.setDefaultTextEncodingName("GBK");
//		// ���������Ϊ0ָ��������ռ�ÿռ䣬ֱ�Ӹ�������ҳ��
//		chengzhi_web.setScrollBarStyle(0);
//		// �Ƿ���Ի�ȡ����
//		chengzhi_web.requestFocus();
//		// �����Զ���WebViewClient,ʵ�����ع���
//		chengzhi_web.setWebViewClient(new MyWebViewClient());
//
//		// ��ȡ�û���������
//		mySharedPreferences = this.getSharedPreferences("user",
//				Activity.MODE_PRIVATE);
//		user_id = mySharedPreferences.getString("user_id", "");
//
//		// ��Ҫ���ʵ���ַ
//		String url = Constant.SERVER_URL + Constant.ADDBANK;
//		// post������Ҫ�ύ�Ĳ���
//		String postDate = "user_id=" + user_id;
//		// ͨ��EncodingUtils.getBytes(data, charset)��������ת��
//		chengzhi_web.postUrl(url, EncodingUtils.getBytes(postDate, "BASE64"));
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.return_key:
//			finish();
//			break;
//		default:
//			break;
//		}
//	}
//
//	/**
//	 * ��дWebViewClient,�Զ���webview�ڵ��¼����ع���
//	 */
//	private class MyWebViewClient extends WebViewClient {
//		@Override
//		public boolean shouldOverrideUrlLoading(WebView view, String url) {
//			if (url.indexOf("tel:") < 0) {// ҳ���������ֻᵼ�����ӵ绰
//				view.loadUrl(url);
//			}
//			return true;
//		}
//	}
//}
