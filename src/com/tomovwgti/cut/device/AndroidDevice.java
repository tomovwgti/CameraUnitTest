package com.tomovwgti.cut.device;

import com.tomovwgti.cut.DeviceInfo;

import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

/**
 * Androidのデバイスクラス
 * 各デバイスは継承して作成する
 */
public abstract class AndroidDevice {

	protected final Activity mActivity;
	
	// 画面の幅・高さの設定
	protected int width;
	protected int height;

	// カメラパラメータ
	protected Camera mCamera;
	protected Parameters mParam;

	public AndroidDevice(Activity activity, DeviceInfo info) {
		mActivity = activity;
		width = info.width;
		height = info.height;

		// 幅よりも高さが大きい場合は、値を入れ替える。
		if(info.width > info.height){
			// 横モード
			width = info.width;
			height = info.height;
		} else {
			// 縦モード
			width = info.height;
			height = info.width;
		}
	}

	/**
	 * カメラパラメータの設定
	 * @param camera
	 */
	abstract public void setParameter(Camera camera);

	/**
	 * 画面の向きを設定
	 */
	abstract protected void setOrientation();

	/**
	 * is Portrait?
	 */
	protected boolean isPortrait() {
		return mActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}

	/**
	 * 画面の向きが切り替わった
	 */
	public void changeOrientation() {
		// 基本的には同じ実装になるはず。必要ならオーバライド
		setOrientation();
		// カメラへのパラメータ設定
		mCamera.setParameters(mParam);
	}
}