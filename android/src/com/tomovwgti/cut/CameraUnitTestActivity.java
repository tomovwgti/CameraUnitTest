package com.tomovwgti.cut;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class CameraUnitTestActivity extends Activity {
	
	private int selectDevice;
	private DeviceInfo mInfo;
	private Camera mCamera;

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
        mCamera.release();

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
}