package com.tomovwgti.lib;

import java.io.File;
import java.io.IOException;

import android.media.ExifInterface;

public class ExifInfo {
	private String datetime;
	private String flash;
	private String latitude;
	private String latitudeRef;
	private String longitude;
	private String longitudeRef;
	private String imageLength;
	private String imageWidth;
	private String make;
	private String model;
	private String orientation;
	private String whiteBalance;

	public ExifInfo(File file) {
		try {
			ExifInterface exif = new ExifInterface(file.toString());
			GetExif(exif);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private void GetExif(ExifInterface exif)
    {
    	datetime     = exif.getAttribute(ExifInterface.TAG_DATETIME);
    	flash        = exif.getAttribute(ExifInterface.TAG_FLASH);
    	latitude     = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
    	latitudeRef  = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
    	longitude    = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
    	longitudeRef = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
    	imageLength  = exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
    	imageWidth   = exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
    	make         = exif.getAttribute(ExifInterface.TAG_MAKE);
    	model        = exif.getAttribute(ExifInterface.TAG_MODEL);
    	orientation  = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
    	whiteBalance = exif.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
    }

	public String getDatetime() {
		return datetime;
	}

	public String getFlash() {
		return flash;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLatitudeRef() {
		return latitudeRef;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getLongitudeRef() {
		return longitudeRef;
	}

	public String getImageLength() {
		return imageLength;
	}

	public String getImageWidth() {
		return imageWidth;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public String getOrientation() {
		return orientation;
	}

	public String getWhiteBalance() {
		return whiteBalance;
	}
}
