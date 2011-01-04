package com.tomovwgti.cut;

import com.tomovwgti.cut.device.AndroidDevice;
import com.tomovwgti.cut.device.AndroidHtc21;
import com.tomovwgti.cut.device.AndroidStandard21;
import com.tomovwgti.cut.device.AndroidStandard22;
import com.tomovwgti.lib.DeviceInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class OrientationCheckActivity extends Activity {
	
	private DeviceInfo mInfo;
	private CameraPreview mCameraPreview;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        int number = getIntent().getIntExtra("DEVICE", 0);
        mInfo = getIntent().getParcelableExtra("INFO");
        AndroidDevice device = getInstance(number);

        mCameraPreview = new CameraPreview(this, device);
        setContentView(mCameraPreview);
	}

    private AndroidDevice getInstance(int number) {
    	switch(number) {
    		case 0:
    			return new AndroidStandard22(this, mInfo);
    		case 1:
    			return new AndroidStandard21(this, mInfo);
    		case 2:
    			return new AndroidHtc21(this, mInfo);
    		case 3:
    			return new AndroidStandard21(this, mInfo);
    	}
    	return null;
    }

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		// Orientation Changed
		boolean portrait = (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT);
		
		// Set Orientation
		mCameraPreview.setOrientation(portrait);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem item = menu.add(0, 0, 0, "Settings");
		item.setIcon(android.R.drawable.ic_menu_preferences);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		LayoutInflater factory = LayoutInflater.from(this);
		final View dialogView = factory.inflate(R.layout.dialogview, null);
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Camera Parameter");
        makeDialogText(dialogView);
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setPositiveButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
        });
        alertDialogBuilder.create().show();
		return true;
	}
	
	private void makeDialogText(View view) {
		Camera.Parameters param = mCameraPreview.getParameter();		
		TextView previewSize = (TextView)view.findViewById(R.id.set_previewsize);
		TextView pictureSize = (TextView)view.findViewById(R.id.set_picturesize);
		TextView focusMode = (TextView)view.findViewById(R.id.set_focusmode);
		previewSize.setText(param.getPreviewSize().width + ", " + param.getPreviewSize().height);
		pictureSize.setText(param.getPictureSize().width + ", " + param.getPictureSize().height);
		focusMode.setText(param.getFocusMode());
	}
}