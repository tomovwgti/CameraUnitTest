package com.tomovwgti.cut.device;

import com.tomovwgti.lib.DeviceInfo;

import android.app.Activity;
import android.hardware.Camera;

/**
 * HTC for ver 2.1
 */
public class AndroidHtc21 extends AndroidDevice {

	public AndroidHtc21(Activity activity, DeviceInfo info) {
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
			// HTC Desire は、縦モード時にも "landscape" を設定しなければ
			// 正しく動作しなかったため、縦・横どちらでも "landscape"
			mParam.set("orientation", "landscape");
		}
	}
}
