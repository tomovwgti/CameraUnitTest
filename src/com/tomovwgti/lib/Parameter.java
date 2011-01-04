package com.tomovwgti.lib;

import android.hardware.Camera;
import android.util.Log;

/**
 * カメラのパラメータ
 * 
 */
public class Parameter {
	private String board;
	private String brand;
	private String device;
	private String model;
	private String product;
	private String release;
	private String sdk;
	
	private Camera.Size previewSize;
	private Camera.Size pictureSize;
	private int jpegQuality;
	private String focusMode;
	private String orientation;
	private StringBuffer sb = new StringBuffer();

	public void writeBuffer() {
		sb.append("BOARD   : " + board + "\n");
		sb.append("BRAND   : " + brand + "\n");
		sb.append("DEVICE  : " + device + "\n");
		sb.append("MODEL   : " + model + "\n");
		sb.append("PRODUCT : " + product + "\n");
		sb.append("RELEASE : " + release + "\n");
		sb.append("SDK     : " + sdk + "\n");
		sb.append("\n");
		sb.append("preview size w: " + previewSize.width + "\n");
		sb.append("preview size h: " + previewSize.height + "\n");
		sb.append("picture size w: " + pictureSize.width + "\n");
		sb.append("picture size h: " + pictureSize.height + "\n");
		sb.append("focus mode : " + focusMode + "\n");
		sb.append("orientation : " + orientation);

		Log.i("Parameter", sb.toString());
	}

	public String getBuffer() {
		return sb.toString();
	}
	
	public int getJpegQuality() {
		return jpegQuality;
	}
	public void setJpegQuality(int jpegQuality) {
		this.jpegQuality = jpegQuality;
	}
	public String getFocusMode() {
		return focusMode;
	}
	public void setFocusMode(String focusMode) {
		this.focusMode = focusMode;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public Camera.Size getPreviewSize() {
		return previewSize;
	}
	public void setPreviewSize(Camera.Size previewSize) {
		this.previewSize = previewSize;
	}
	public Camera.Size getPictureSize() {
		return pictureSize;
	}
	public void setPictureSize(Camera.Size pictureSize) {
		this.pictureSize = pictureSize;
	}
	public String getBoard() {
		return board;
	}
	public void setBoard(String board) {
		this.board = board;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	public String getSdk() {
		return sdk;
	}
	public void setSdk(String sdk) {
		this.sdk = sdk;
	}
	
}