package com.tomovwgti.cut;

import android.app.Activity;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;

public class DeviceInfo implements Parcelable {
	public String model;
	public int width;
	public int height;
	public String version;
	public int sdkint;
	
	public DeviceInfo(Activity context) {
		model = Build.MODEL;
		sdkint = Build.VERSION.SDK_INT;
		version = Build.VERSION.RELEASE;
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		width = metrics.widthPixels;
		height = metrics.heightPixels;
	}

    public static final Parcelable.Creator<DeviceInfo> CREATOR = new Parcelable.Creator<DeviceInfo>() {  

    	public DeviceInfo createFromParcel(Parcel in) {
    		return new DeviceInfo(in);  
    	}

    	public DeviceInfo[] newArray(int size) {  
    		return new DeviceInfo[size];  
    	}  
    };  

    private DeviceInfo(Parcel in) {
    	model = in.readString();
    	width = in.readInt();
    	height = in.readInt();
    	version = in.readString();
    	sdkint = in.readInt();
    }

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		out.writeString(model);
		out.writeInt(width);
		out.writeInt(height);
		out.writeString(version);
		out.writeInt(sdkint);
	}

	@Override
	public int describeContents() {
		return 0;
	}
}
