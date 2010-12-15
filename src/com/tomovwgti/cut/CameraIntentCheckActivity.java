package com.tomovwgti.cut;

import java.io.InputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CameraIntentCheckActivity extends Activity {
	private static final int IMAGE_CAPTURE = 1234;
	private Uri mImageUri;
	private ImageView imageView;
	
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
		if (requestCode == IMAGE_CAPTURE) {
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
		}
	}
}