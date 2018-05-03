package com.example.jinglinzichan.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.example.jinglinzichan.utils.SharedPreferencesUtils;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

//��¼
public class Login extends BaseActivity {

    private LinearLayout return_key;
    private EditText user_name, password;
    private TextView zhuce, title;
    private TextView denglu, wangjimima;
    private CheckBox rember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initUI();
    }

    public void initUI() {
        // ���ؼ�
        return_key = (LinearLayout) findViewById(R.id.return_key);
        return_key.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                huiyuanzhongxinx();
                finish();
            }
        });
        title = (TextView) findViewById(R.id.title);
        title.setText("��¼�˺�");
        // ��������
        wangjimima = (TextView) findViewById(R.id.wangjimima);
        wangjimima.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Forgot.class));
            }
        });
        // ע��
        zhuce = (TextView) findViewById(R.id.zhuce);
        zhuce.setText(Html.fromHtml("<font color='#000'>��ûע�᣿</font>����ע��"));
        zhuce.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
        // ��¼
        denglu = (TextView) findViewById(R.id.denglu);
        denglu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                isopen();
            }
        });
        // �û���
        user_name = (EditText) findViewById(R.id.user_name);
        // ����
        password = (EditText) findViewById(R.id.password);

        rember = (CheckBox) findViewById(R.id.rember);
        String name = (String) SharedPreferencesUtils.get(SharedPreferencesUtils.LOGIN_NAME, "");
        if (!TextUtils.isEmpty(name)) {
            user_name.setText(name);

        }



        rember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!TextUtils.isEmpty(user_name.getText().toString())) {
                        SharedPreferencesUtils.set(SharedPreferencesUtils.LOGIN_NAME, user_name.getText().toString());
                    }
                } else {
                    SharedPreferencesUtils.remove(SharedPreferencesUtils.LOGIN_NAME);

                }
            }
        });
    }

    // ��¼
    public void isopen() {
        show();
        PushData pushData = PushData.getInstance();
        RequestParams params = new RequestParams();
        params.addBodyParameter("user_name", user_name.getText().toString());
        params.addBodyParameter("password", password.getText().toString());

        pushData.httpClientSendWithToken(params, Constant.LOGIN,
                new BusinessResponse() {

                    @Override
                    public void OnMessageResponse(String url, JSONObject jo)
                            throws JSONException {
                        hide();
                        if (jo == null) {
                            showMessage("��������ʧ�ܣ���������");
                        } else {
                            if (url.equals(Constant.LOGIN)) {
                                Log.e("��¼", jo.toString());
                                if (jo.getString("status").equals("1")) {
                                    SharedPreferences.Editor editor = mySharedPreferences
                                            .edit();
                                    editor.putString("user_id",
                                            jo.getString("user_id"));
                                    // �ύ��ǰ����
                                    editor.commit();
                                    huiyuanzhongxin();
                                    finish();
                                }
                                showMessage(jo.getString("msg"));
                            }
                        }
                    }
                });
    }

    public void huiyuanzhongxin() {
        Intent intent = new Intent();
        intent.setAction("com.zhongtoulvyou");
        intent.putExtra("index", 3);
        sendBroadcast(intent);
    }

    public void huiyuanzhongxinx() {
        Intent intent = new Intent();
        intent.setAction("com.zhongtoulvyou");
        intent.putExtra("index", 0);
        sendBroadcast(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (rember.isChecked()) {
            if (!TextUtils.isEmpty(user_name.getText().toString())) {
                SharedPreferencesUtils.set(SharedPreferencesUtils.LOGIN_NAME, user_name.getText().toString());
            }
        } else {
            SharedPreferencesUtils.remove(SharedPreferencesUtils.LOGIN_NAME);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            huiyuanzhongxinx();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // ��ȡ�����ǰ��locationλ��
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // ���������������򣬱������EditText���¼�
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
