package com.goka.droidlgtm.view;

import com.goka.droidlgtm.camera.CameraPreview;
import com.goka.droidlgtm.component.OverLayView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends ActionBarActivity {

    private OverLayView mOverLayView;
    private CameraPreview mPreview;
    private RelativeLayout mLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Requires RelativeLayout.
        mLayout = new RelativeLayout(this);
        setContentView(mLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPreview = new CameraPreview(this, CameraPreview.CameraMode.BACK, CameraPreview.LayoutMode.FitToParent);
        LayoutParams previewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        // Un-comment below lines to specify the size.
        //previewLayoutParams.height = 500;
        //previewLayoutParams.width = 500;

        // Un-comment below line to specify the position.
        //mPreview.setCenterPosition(270, 130);

        mPreview.setPictureCallback(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                Bitmap cameraBitmap = takenBitmap(bytes);
                MediaStore.Images.Media.insertImage(MainActivity.this.getContentResolver(), cameraBitmap, "LGTM", null);
            }
        });

        mLayout.addView(mPreview, 0, previewLayoutParams);

        mOverLayView = new OverLayView(this);
        addContentView(mOverLayView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop();
        mLayout.removeView(mPreview); // This is necessary.
        mPreview = null;
    }


    private Bitmap takenBitmap(byte[] data) {

        // Camera
        Bitmap cameraMap = BitmapFactory.decodeByteArray(data, 0,
                data.length, null);

        // Overlay
        Bitmap overlayMap = mOverLayView.getDrawingCache();

        // EmptyBitmap
        Bitmap offBitmap = Bitmap.createBitmap(cameraMap.getWidth(),
                cameraMap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas offScreen = new Canvas(offBitmap);
        offScreen.drawBitmap(
                cameraMap,
                null,
                new Rect(0, 0, cameraMap.getWidth(), cameraMap.getHeight()), null);
        offScreen.drawBitmap(
                overlayMap,
                null,
                new Rect(0, 0, cameraMap.getWidth(), cameraMap.getHeight()), null);
        return offBitmap;
    }

}
