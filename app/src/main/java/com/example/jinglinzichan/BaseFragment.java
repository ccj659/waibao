package com.example.jinglinzichan;

import com.example.jinglinzichan.view.HkDialogLoading;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {

	protected SharedPreferences mySharedPreferences;
	private HkDialogLoading dialogLoading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(getContentId(), null);
		// 提取用户名、密码
		mySharedPreferences = getActivity().getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		dialogLoading = new HkDialogLoading(getActivity());
		init(view);
		return view;
	}

	/**
	 * show Toast
	 * 
	 * @param message
	 */
	protected void showMessage(CharSequence message) {
		if (!getActivity().isDestroyed())
			Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}

	abstract public int getContentId();

	abstract public void init(View view);
	
	public void show() {
		dialogLoading.show(); // 显示加载中对话框
	}

	public void hide() {
		dialogLoading.dismiss(); // 隐藏加载中对话框
	}
}
