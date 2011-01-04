package com.tomovwgti.cut;

import com.tomovwgti.lib.DeviceInfo;
import com.tomovwgti.lib.Parameter;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class CameraUnitTestActivity extends Activity {
	
	private int selectDevice;
	private DeviceInfo mInfo;
	private Camera mCamera;
	private static Parameter mParameter = new Parameter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView modelText = (TextView)findViewById(R.id.model);
        TextView widthText = (TextView)findViewById(R.id.width);
        TextView heightText = (TextView)findViewById(R.id.height);
        TextView verText = (TextView)findViewById(R.id.version);
        Spinner modeSpinner = (Spinner)findViewById(R.id.select_mode);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.mode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);    
        modeSpinner.setAdapter(adapter);
        Button checkOrientation = (Button)findViewById(R.id.orientationcheck);
        Button checkIntent = (Button)findViewById(R.id.cameraintent);
        
        mInfo = new DeviceInfo(this);
        modelText.setText(mInfo.model);
        widthText.setText(String.valueOf(mInfo.width));
        heightText.setText(String.valueOf(mInfo.height));
        verText.setText(String.valueOf(mInfo.version));
        
        mCamera = Camera.open();
        Camera.Parameters params = mCamera.getParameters();
        int pictureWidth  = params.getPictureSize().width;
        int pictureHeight = params.getPictureSize().height;
        int previewWidth  = params.getPreviewSize().width;
        int previewHeight = params.getPreviewSize().height;
        
        // パラメータの保存
        mParameter.setBoard(Build.BOARD);
        mParameter.setBrand(Build.BRAND);
        mParameter.setDevice(Build.DEVICE);
        mParameter.setModel(Build.MODEL);
        mParameter.setProduct(Build.PRODUCT);
        mParameter.setRelease(Build.VERSION.RELEASE);
        mParameter.setSdk(Build.VERSION.SDK);
        mParameter.setPictureSize(params.getPictureSize());
        mParameter.setPreviewSize(params.getPreviewSize());
        mParameter.setFocusMode(params.getFocusMode());
        mParameter.setJpegQuality(params.getJpegQuality());
        mParameter.setOrientation(params.get("orientation"));

        mCamera.release();

        mParameter.writeBuffer();

        TextView pictureText = (TextView)findViewById(R.id.picturesize);
        TextView previewText = (TextView)findViewById(R.id.previewsize);
        pictureText.setText(String.valueOf(pictureWidth) + ", " + String.valueOf(pictureHeight));
        previewText.setText(String.valueOf(previewWidth) + ", " + String.valueOf(previewHeight));

        modeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selectDevice = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
        
        checkOrientation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), OrientationCheckActivity.class);
				intent.putExtra("DEVICE", selectDevice);
				intent.putExtra("INFO", mInfo);
				startActivity(intent);
			}
        });
        
        checkIntent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), CameraIntentCheckActivity.class);
				startActivity(intent);
			}
        });
    }

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Send Paremeter").setIcon(android.R.drawable.ic_dialog_email);
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:tomovwgti@gmail.com"));
		intent.putExtra(Intent.EXTRA_SUBJECT,"[CUT]Camera Parameter");
		intent.putExtra(Intent.EXTRA_TEXT, mParameter.getBuffer());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		return true;
	}
}