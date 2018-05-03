package com.example.jinglinzichan.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.jinglinzichan.App;

/**
 * SharedPreferences��һ�������࣬����setParam���ܱ���String, Integer, Boolean, Float,
 * Long���͵Ĳ��� ͬ������getParam���ܻ�ȡ���������ֻ����������
 */
public class SharedPreferencesUtils {

    private static final String FILE_NAME = "sharepreference";


    public static final String LOGIN_NAME = "LOGIN_NAME";


    /**
     * �������ݵķ�����������Ҫ�õ��������ݵľ������ͣ�Ȼ��������͵��ò�ͬ�ı��淽��
     *
     * @param key    ����
     * @param object ֵ  (null����в���)
     */
    public static boolean set(String key, Object object) {
        if (key == null)
            return false;
        try {
            SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            if (object == null) {
                editor.remove(key);
                editor.apply();
                return true;
            }
            String type = object.getClass().getSimpleName();
            if ("String".equals(type)) {
                editor.putString(key, (String) object);
            } else if ("Integer".equals(type)) {
                editor.putInt(key, (Integer) object);
            } else if ("Boolean".equals(type)) {
                editor.putBoolean(key, (Boolean) object);
            } else if ("Float".equals(type)) {
                editor.putFloat(key, (Float) object);
            } else if ("Long".equals(type)) {
                editor.putLong(key, (Long) object);
            }
            editor.apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean remove(String key) {
        SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
        return true;
    }

    /**
     * �õ��������ݵķ��������Ǹ���Ĭ��ֵ�õ���������ݵľ������ͣ�Ȼ���������ڵķ�����ȡֵ
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        try {
            String type = defaultObject.getClass().getSimpleName();
            SharedPreferences sp = App.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            if ("String".equals(type)) {
                return sp.getString(key, (String) defaultObject);
            } else if ("Integer".equals(type)) {
                return sp.getInt(key, (Integer) defaultObject);
            } else if ("Boolean".equals(type)) {
                return sp.getBoolean(key, (Boolean) defaultObject);
            } else if ("Float".equals(type)) {
                return sp.getFloat(key, (Float) defaultObject);
            } else if ("Long".equals(type)) {
                return sp.getLong(key, (Long) defaultObject);
            }
            return defaultObject;
        } catch (Exception e) {
            e.printStackTrace();
            return defaultObject;
        }
    }

}