package com.example.jinglinzichan;

import com.example.jinglinzichan.view.HkDialogLoading;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Project Base Activity
 */
public class BaseActivity extends FragmentActivity {

	protected SharedPreferences mySharedPreferences;
	private HkDialogLoading dialogLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getActionBar().hide();
		// 提取用户名、密码
		mySharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
		dialogLoading = new HkDialogLoading(this);
	}

	/**
	 * show Toast
	 */
	protected void showMessage(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	protected boolean isLogin() {
		if (mySharedPreferences.getString("userId", "").equals("")) {
			return false;
		} else {
			return true;
		}
	}

	public void show() {
		dialogLoading.show(); // 显示加载中对话框
	}

	public void hide() {
		dialogLoading.dismiss(); // 隐藏加载中对话框
	}
}
