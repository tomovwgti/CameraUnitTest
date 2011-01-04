package com.tomovwgti.cut;

import java.io.File;
import java.io.InputStream;

import com.tomovwgti.lib.ContentInfo;
import com.tomovwgti.lib.ExifInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CameraIntentCheckActivity extends Activity {
	private static final int IMAGE_CAPTURE = 1234;
	private static final int FROM_CONTENT = 0;
	private static final int FROM_EXIF = 1;

	private Uri mImageUri;
	private ImageView imageView;
	private ContentInfo contentInfo;
	private ExifInfo exifInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameraview);

        Button btn = (Button)findViewById(R.id.gotocamera);
		// check SDCard
		if ( !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ) {
			btn.setEnabled(false);
			Toast.makeText(this, "Check SD Card", Toast.LENGTH_LONG).show();
			return;
		}
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imageView = null;
				System.gc();
		        long dateTaken = System.currentTimeMillis();  
		        String filename = createName(dateTaken) + ".jpg";  
			    
		        ContentValues values = new ContentValues();
			    values.put(MediaStore.Images.Media.TITLE, filename);
			    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
			    mImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			    Intent intent = new Intent();
			    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
			    startActivityForResult(intent, IMAGE_CAPTURE);
			}
		});
	}
	
    private static String createName(long dateTaken) {  
        return DateFormat.format("yyyy-MM-dd_kk.mm.ss", dateTaken).toString();  
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != IMAGE_CAPTURE) {
			return;
		}

		TextView imageSize = (TextView)findViewById(R.id.getpicturesize);
        imageView = (ImageView)findViewById(R.id.getimage);
        BitmapFactory.Options bitmapOpt = new BitmapFactory.Options();
        bitmapOpt.inJustDecodeBounds = true;
        InputStream imgis = null;
		try {
			imgis = getContentResolver().openInputStream(mImageUri);
	        BitmapFactory.decodeStream(imgis, null, bitmapOpt);
			imgis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bitmapOpt.outWidth;
		int height = bitmapOpt.outHeight;
		imageSize.setText(String.valueOf(width) + ", " + String.valueOf(height));
        imageView.setImageURI(mImageUri);
        
        // Content Prividerから情報を取得して設定
        contentInfo = new ContentInfo();
        contentInfo.setContentInfo(this, mImageUri);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, FROM_CONTENT, 0, "from Content");
		menu.add(0, FROM_EXIF, 1, "from Exif");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = null;
		switch ( item.getItemId() ) {
			case 0:  // Content Provider
				view = inflater.inflate(R.layout.contentview, null);
				((TextView) view.findViewById(R.id.cp_lat)).setText("LAT: " + contentInfo.getLatitude());
				((TextView) view.findViewById(R.id.cp_lon)).setText("LON: " + contentInfo.getLongitude());
				((TextView) view.findViewById(R.id.cp_orientation)).setText("ORIENTATION: " + contentInfo.getOrientation());
				((TextView) view.findViewById(R.id.cp_datataken)).setText("DATA_TAKEN: " + contentInfo.getDatataken());
				break;
			case 1: // Exif
				view = inflater.inflate(R.layout.exifview, null);
				File file = new File(contentInfo.getImagePath());
				exifInfo = new ExifInfo(file);
				((TextView) view.findViewById(R.id.exif_imagelength)).setText("IMAGE_LENGTH: " + exifInfo.getImageLength());
				((TextView) view.findViewById(R.id.exif_imagewidth)).setText("IMAGE_WIDTH: " + exifInfo.getImageWidth());
				((TextView) view.findViewById(R.id.exif_lat)).setText("LAT: " + exifInfo.getLatitude());
				((TextView) view.findViewById(R.id.exif_lat_ref)).setText("LAT_REF: " + exifInfo.getLatitudeRef());
				((TextView) view.findViewById(R.id.exif_lon)).setText("LON: " + exifInfo.getLongitude());
				((TextView) view.findViewById(R.id.exif_lon_ref)).setText("LON_REF: " + exifInfo.getLongitudeRef());
				((TextView) view.findViewById(R.id.exif_datetime)).setText("DATE_TIME: " + exifInfo.getDatetime());
				((TextView) view.findViewById(R.id.exif_make)).setText("MAKE: " + exifInfo.getMake());
				((TextView) view.findViewById(R.id.exif_model)).setText("MODEL: " + exifInfo.getModel());
				((TextView) view.findViewById(R.id.exif_orientation)).setText("ORIENTATION: " + exifInfo.getOrientation());
				((TextView) view.findViewById(R.id.exif_flash)).setText("FLASH: " + exifInfo.getFlash());
				((TextView) view.findViewById(R.id.exif_whitebalance)).setText("WHITE_BALANCE: " + exifInfo.getWhiteBalance());
				break;
		}
		new AlertDialog.Builder(this).setView(view).show();
		return true;
	}
}