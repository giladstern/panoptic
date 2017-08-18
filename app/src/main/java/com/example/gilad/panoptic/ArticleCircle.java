package com.example.gilad.panoptic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Gilad on 8/18/2017.
 */
public class ArticleCircle extends View{
    private Paint paint;
    private float xc;
    private float yc;
    private float radius;
    private String url;

    public ArticleCircle(Context context) {
        this(context, "");
    }
    public ArticleCircle(Context context, AttributeSet attributeSet) {
        this(context);
    }

    public ArticleCircle(Context context, String string) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(context, R.color.yellow));
        paint.setStyle(Paint.Style.FILL);
        xc = 0;
        yc = 0;
        radius = 0;
        url = string;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Account for padding
        float xpad = (float)(getPaddingLeft() + getPaddingRight());
        float ypad = (float)(getPaddingTop() + getPaddingBottom());
        float ww = (float) w - xpad;
        float hh = (float) h - ypad;

        xc = getPaddingLeft() + (ww / 2);
        yc = getPaddingLeft() + (hh / 2);
        radius = Math.min(ww,hh) / 2;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(xc,yc,radius,paint);
    }

    public String getUrl(){
        return url;
    }
}
