package com.oraro.sunlon.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.oraro.sunlon.sunlontesta.R;

/**
 * 根据图片自绘的mark点
 * @author 王子榕
 */
public class MarkView extends View {
    private Paint mPaint;


    public MarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap  bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.mark);
        canvas.drawBitmap(bitmap,0,0,mPaint);
    }
}
