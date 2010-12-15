package com.tomovwgti.cut;

import java.io.IOException;

import com.tomovwgti.cut.device.AndroidDevice;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mHolder;
    private Camera mCamera;
    private AndroidDevice mDevice;
 
    CameraPreview(Context context, AndroidDevice device) {
        super(context);
        mDevice = device;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
 
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	mCamera = Camera.open();
        try {
        	mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
 
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    	mCamera.stopPreview();
    	mDevice.setParameter(mCamera);
        mCamera.startPreview();
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    	mCamera.stopPreview();
    	mCamera.release();
    }
    
    public void setOrientation(boolean portrait) {
    	String toastText;
    	if ( portrait ) {
    		toastText = "Portrait";
    	} else {
    		toastText = "Landscape";
    	}
    	Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT).show();

		mCamera.stopPreview();
		mDevice.changeOrientation();
		mCamera.startPreview();
    }

    public Camera.Parameters getParameter() {
    	return mCamera.getParameters();
    }
}