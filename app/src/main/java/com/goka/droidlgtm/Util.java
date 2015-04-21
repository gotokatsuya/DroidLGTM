package com.goka.droidlgtm;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.Display;
import android.view.Surface;

/**
 * Created by katsuyagoto on 2015/04/21.
 */
public class Util {

    public static String createNameFromCurrentTime() {
        long dateTaken = System.currentTimeMillis();
        return DateFormat.format("yyyy - MM - dd_kk.mm.ss", dateTaken).toString();
    }

    public static int getDisplayRotation(Activity activity) {
        int angle;
        Display display = activity.getWindowManager().getDefaultDisplay();
        switch (display.getRotation()) {
            case Surface.ROTATION_0: // This is display orientation
                angle = 90; // This is camera orientation
                break;
            case Surface.ROTATION_90:
                angle = 0;
                break;
            case Surface.ROTATION_180:
                angle = 270;
                break;
            case Surface.ROTATION_270:
                angle = 180;
                break;
            default:
                angle = 90;
                break;
        }
        return angle;
    }
}
