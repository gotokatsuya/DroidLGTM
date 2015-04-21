package com.goka.droidlgtm.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by katsuyagoto on 2015/04/21.
 */
public class OverLayView extends View {

    private static final String LGTM_STR = "LGTM";
    private static final float TEXT_SIZE = 120;

    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Rect mBounds;

    public OverLayView(Context context) {
        super(context);
        setDrawingCacheEnabled(true);
        setFocusable(true);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(TEXT_SIZE);
        mPaint.setAntiAlias(true);

        mBounds = new Rect();
        mPaint.getTextBounds(LGTM_STR, 0, LGTM_STR.length(), mBounds);
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
        canvas.drawText(LGTM_STR, mWidth/2 - mBounds.width()/2, mHeight/2 - mBounds.height()/2, mPaint);
    }

}