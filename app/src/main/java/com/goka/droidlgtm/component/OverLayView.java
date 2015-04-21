package com.goka.droidlgtm.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by katsuyagoto on 2015/04/21.
 */
public class OverLayView extends View {

    private static final String LGTM_STR = "LGTM";
    private static final float TEXT_SIZE = 30;

    private int mWidth;
    private int mHeight;
    private Paint mPaint;

    public OverLayView(Context context) {
        super(context);
        setDrawingCacheEnabled(true);
        setFocusable(true);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(TEXT_SIZE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawText(LGTM_STR, mWidth/2, mHeight/2, mPaint);
    }

}