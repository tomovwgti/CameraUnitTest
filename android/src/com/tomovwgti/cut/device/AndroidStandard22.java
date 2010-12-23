package com.tomovwgti.cut.device;

import com.tomovwgti.cut.DeviceInfo;

import android.app.Activity;
import android.hardware.Camera;

/**
 * Standard Device ver 2.2
 */
public class AndroidStandard22 extends AndroidDevice {

	public AndroidStandard22(Activity activity, DeviceInfo info) {
		super(activity, info);
	}

	@Override
	public void setParameter(Camera camera) {
		mCamera = camera;
		// カメラパラメータの取得
		mParam = camera.getParameters();
		// プレビューサイズの設定
		mParam.setPreviewSize(width, height);
		// 起動時の画面の向き(Override)
		setOrientation();
		// カメラへパラメータを設定する
		mCamera.setParameters(mParam);
	}

	@Override
	protected void setOrientation() {
		// Android 2.2以降の場合
		int degrees = (isPortrait() == true) ? 90: 0;
		mCamera.setDisplayOrientation(degrees);
	}
}