package com.ben.app;

import android.app.Activity;
import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.FrameLayout;

import java.util.List;

public class CameraActivity extends Activity {

    private final static String LOG_TAG = "CAMERA_ACTIVITY";
    private Camera mCamera;
    private Camera.Parameters mCameraParameters;
    private CameraPreview mPreview;
    private FrameLayout mPreviewLayout;

    private static Boolean bImageValidated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    @Override
    protected void onPause(){
        super.onPause();
        releaseCamera();
    }

    @Override
    protected void onResume(){
        super.onResume();

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Define the camera parameters
        mCameraParameters = mCamera.getParameters();
        mCamera.setParameters(mCameraParameters);

        int height = getWindowManager().getDefaultDisplay().getHeight();
        int width = getWindowManager().getDefaultDisplay().getWidth();
        if(height > width) // Portrait
            mCamera.setDisplayOrientation(90);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        mPreviewLayout = (FrameLayout) findViewById(R.id.camera_preview);
        mPreviewLayout.addView(mPreview);

    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            Log.d(LOG_TAG, "Error gettin CameraInstance:" + e.getMessage());   // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    /** Release function **/
    private void releaseCamera(){
        if (mCamera != null){
            Log.d(LOG_TAG,"releaseCamera()");
            // release the camera for other applications
            mCamera.release();
            mCamera = null;
            mPreview.getHolder().removeCallback(mPreview);
            mPreview = null;
        }
    }











}

