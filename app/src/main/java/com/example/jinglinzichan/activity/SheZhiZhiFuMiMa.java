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

//����֧������
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
		// ���ؼ�
		return_key = (LinearLayout) findViewById(R.id.return_key);
		return_key.setOnClickListener(this);
		// ��������
		title = (TextView) findViewById(R.id.title);
		title.setText("����֧������");
		// ��������
		zifumima_edit = (EditText) findViewById(R.id.zifumima_edit);
		zifumima_edit.setKeyListener(DialerKeyListener.getInstance());
		// �ٴ���������
		zifumima_shurumima = (EditText) findViewById(R.id.zifumima_shurumima);
		zifumima_shurumima.setKeyListener(DialerKeyListener.getInstance());
		// �ֻ���
		zifumima_shoujihao = (EditText) findViewById(R.id.zifumima_shoujihao);
		zifumima_shoujihao.setInputType(InputType.TYPE_CLASS_NUMBER);
		// ��֤��
		zifumima_yanzhengma = (EditText) findViewById(R.id.zifumima_yanzhengma);
		zifumima_yanzhengma.setInputType(InputType.TYPE_CLASS_NUMBER);
		// ��ȡ��֤��
		huoquyanzhengma = (ImageView) findViewById(R.id.huoquyanzhengma);
		huoquyanzhengma.setOnClickListener(this);
		// ȷ������
		zhifumima_querenxiugai = (ImageView) findViewById(R.id.zhifumima_querenxiugai);
		zhifumima_querenxiugai.setOnClickListener(this);

		// ��ȡ�û���������
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
			// �ֻ���
			zifumima_shoujihaos = zifumima_shoujihao.getText().toString();
			zhifumimaduanxin();
			break;
		case R.id.zhifumima_querenxiugai:
			// ��������
			zifumima_edits = zifumima_edit.getText().toString();
			// �ٴ���������
			zifumima_shurumimas = zifumima_shurumima.getText().toString();
			// �ֻ���
			zifumima_shoujihaos = zifumima_shoujihao.getText().toString();
			// ��֤��
			zifumima_yanzhengmas = zifumima_yanzhengma.getText().toString();
			if (!zifumima_edits.equals("") && !zifumima_shurumimas.equals("")
					&& !zifumima_shoujihao.equals("")
					&& !zifumima_yanzhengmas.equals("")
					&& zifumima_edits.equals(zifumima_shurumimas)) {
				isopen();
			} else if (zifumima_edits != zifumima_shurumimas) {
				Toast.makeText(this, "����֧���������벻��ͬ,����������", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(this, "�뽫��Ϣ��������", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

	// ����֧������
	public void isopen() {
		dialogLoading.show(); // ��ʾ�����жԻ���
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("mobile", zifumima_shoujihaos);
		params.addBodyParameter("jiaoyipw", zifumima_edits);
		params.addBodyParameter("pw_code", zifumima_yanzhengmas);

		pushData.httpClientSendWithToken(params, Constant.MODIFY_PAYMENT, this);
	}

	// ����֧���������
	public void zhifumimaduanxin() {
		dialogLoading.show(); // ��ʾ�����жԻ���
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
		dialogLoading.dismiss(); // ���ؼ����жԻ���
		if (jo == null) {
			Toast.makeText(this, "��������ʧ�ܣ���������", Toast.LENGTH_SHORT).show();
		} else {
			if (url.equals(Constant.MODIFY_PAYMENT)) {
				Log.e("����֧������", jo.toString());
				String status = jo.getString("status");
				String msg = jo.getString("msg");
				Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
				if (status.equals("1")) {
					finish();
				}
			} else if (url.equals(Constant.GET_MOBILE_CODE_PW)) {
				Log.e("����֧���������", jo.toString());
				String msg = jo.getString("msg");
				Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
			}
		}
	}
}
