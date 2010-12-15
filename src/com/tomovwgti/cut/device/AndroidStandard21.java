package com.tomovwgti.cut.device;

import com.tomovwgti.cut.DeviceInfo;

import android.app.Activity;
import android.hardware.Camera;

/**
 * Standard Device ver 2.1
 */
public class AndroidStandard21 extends AndroidDevice {

	public AndroidStandard21(Activity activity, DeviceInfo info) {
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
		String orientation = (isPortrait() == true) ? "portrait": "landscape";
		// 画面の向きが異なっている場合
		if(orientation.equals(mParam.get("orientation")) == false){
			mParam.set("orientation", orientation);
		}
	}
}