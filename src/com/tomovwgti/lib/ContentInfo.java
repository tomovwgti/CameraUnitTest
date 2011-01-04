package com.tomovwgti.lib;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class ContentInfo {

	// インテントからの情報の格納先を準備する
	private String[] projection = 
		{ MediaStore.Images.ImageColumns.DATA, /*col1*/
		  MediaStore.Images.ImageColumns.LONGITUDE,
	      MediaStore.Images.ImageColumns.LATITUDE,
	      MediaStore.Images.ImageColumns.ORIENTATION,
	      MediaStore.Images.ImageColumns.DATE_TAKEN,
	    };

	private String imagePath;
	private double longitude;
	private double latitude;
	private String orientation;
	private String datataken;
	
	public void setContentInfo(Activity activity, Uri uri) {
		// インテントからの情報をカーソルに保存
		Cursor c = activity.managedQuery(uri, projection, null, null, null);
		// カーソルの先頭を取得
		if (c!=null && c.moveToFirst()) {
			imagePath = c.getString(0);
			longitude = c.getDouble(1);
			latitude = c.getDouble(2);
			orientation = c.getString(3);
			datataken = c.getString(4);
		}
	}

	public String[] getProjection() {
		return projection;
	}

	public String getImagePath() {
		return imagePath;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public String getOrientation() {
		return orientation;
	}

	public String getDatataken() {
		return datataken;
	}
}
