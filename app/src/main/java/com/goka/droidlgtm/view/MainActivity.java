package com.goka.droidlgtm.view;

import com.goka.droidlgtm.Util;
import com.goka.droidlgtm.camera.CameraPreview;
import com.goka.droidlgtm.component.OverLayView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.text.format.DateFormat;
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

        // Requires RelativeLayout.
        mLayout = new RelativeLayout(this);
        setContentView(mLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPreview = new CameraPreview(this, CameraPreview.CameraMode.BACK, CameraPreview.LayoutMode.FitToParent);
        LayoutParams previewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        previewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);


        // Un-comment below lines to specify the size.
        //previewLayoutParams.height = 500;
        //previewLayoutParams.width = 500;

        // Un-comment below line to specify the position.
        //mPreview.setCenterPosition(270, 130);

        mPreview.setPictureCallback(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                Bitmap cameraBitmap = createTakenBitmap(bytes);
                String name = "LGTM " + Util.createNameFromCurrentTime() + ".jpg";
                MediaStore.Images.Media.insertImage(MainActivity.this.getContentResolver(), cameraBitmap, name, null);
            }
        });

        mLayout.addView(mPreview, 0, previewLayoutParams);

        mOverLayView = new OverLayView(this);
        addContentView(mOverLayView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop();
        mLayout.removeView(mPreview); // This is necessary.
        mPreview = null;
    }


    private Bitmap createTakenBitmap(byte[] data) {

        Matrix matrix = new Matrix();
        matrix.setRotate(Util.getDisplayRotation(this));

        // Camera
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Bitmap _cameraMap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        Bitmap cameraMap = Bitmap.createBitmap(_cameraMap, 0, 0, _cameraMap.getWidth(), _cameraMap.getHeight(), matrix, true);

        // Overlay
        Bitmap overlayMap = mOverLayView.getDrawingCache();

        // EmptyBitmap
        Bitmap offBitmap = Bitmap.createBitmap(cameraMap.getWidth(), cameraMap.getHeight(), Bitmap.Config.ARGB_8888);

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
