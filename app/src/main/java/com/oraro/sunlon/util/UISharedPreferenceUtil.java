/*
 * 文件名: UISharedPreferenceUtil.java
 * @author ChenLong
 * @version [1.0] 
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.oraro.sunlon.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * [UISharedPreferenceUtil]<BR>
 * [功能详细描述]
 * 
 * @author ChenLong
 * @version [1.0]
 */
public class UISharedPreferenceUtil {
	private static final String TAG = UISharedPreferenceUtil.class.getName();

	private static UISharedPreferenceUtil mUISharedPreferenceUtil;

	/**
	 * sharedpreferences 对象
	 */
	private SharedPreferences mSharedPreferences;

	private Context mContext;

	private UISharedPreferenceUtil(Context mContext) {
		this.mContext = mContext;
	}

	public static UISharedPreferenceUtil getInstall(Context mContext) {
		if (null == mUISharedPreferenceUtil) {
			mUISharedPreferenceUtil = new UISharedPreferenceUtil(mContext);
		}
		return mUISharedPreferenceUtil;
	}

	/**
	 * 
	 * 获取SharedPreferences对象 [功能详细描述]
	 * 
	 * @return
	 */
	private SharedPreferences getSharedPreferences(Context mContext) {
		if (null == mSharedPreferences) {
			mSharedPreferences = mContext.getSharedPreferences(
					"config", Context.MODE_PRIVATE);
		}
		return mSharedPreferences;
	}

	/**
	 * GetStringSharedPreferences
	 * 
	 * @param
	 *
	 * @param key
	 *            key
	 * @return String
	 * @see [类、类#方法、类#成员]
	 */
	public String getStringSharedPreferences(String key) {

		return getSharedPreferences(mContext).getString(key, "");

	}

	/**
	 * SaveStringSharedPreferences
	 * 
	 * @param
	 *
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @see [类、类#方法、类#成员]
	 */
	public void saveStringSharedPreferences(String key, String value) {
		Editor mEditor = getSharedPreferences(mContext).edit();
		mEditor.putString(key, value);
		mEditor.commit();
	}

	/**
	 * ClearStringSharedPreferences
	 * 
	 * @param
	 *
	 * @see [类、类#方法、类#成员]
	 */
	public void clearStringSharedPreferences() {
		// FindBugs NM_METHOD_NAMING_CONVENTION 方法不能以大写字母开头
		Editor mEditor = getSharedPreferences(mContext).edit();
		mEditor.clear();
		mEditor.commit();
	}

	/**
	 * SaveBooleanSharedPreferences
	 * 
	 * @param
	 *
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @see [类、类#方法、类#成员]
	 */
	public void saveBooleanSharedPreferences(String key, boolean value) {
		Editor mEditor = getSharedPreferences(mContext).edit();
		mEditor.putBoolean(key, value);
		mEditor.commit();
	}

	/**
	 * getBooleanSharedPreferences
	 * 
	 * @param
	 *
	 * @param key
	 *            key
	 * @return boolean
	 * @see [类、类#方法、类#成员]
	 */
	public boolean getBooleanSharedPreferences(String key) {
		return getSharedPreferences(mContext).getBoolean(key, false);
	}

	public void saveIntSharedPreferences(String key, int value) {
		Editor mEditor = getSharedPreferences(mContext).edit();
		mEditor.putInt(key, value);
		mEditor.commit();
	}

	public int getIntSharedPreferences(String key) {
		return getSharedPreferences(mContext).getInt(key,-1);
	}

	/**
	 * getBooleanSharedPreferences
	 * 
	 * @param key
	 *            key
	 * @return boolean
	 * @see [类、类#方法、类#成员]
	 */
	public boolean getBooleanSharedPreferencesDefaultTrue(String key) {
		return getSharedPreferences(mContext).getBoolean(key, true);
	}

}
