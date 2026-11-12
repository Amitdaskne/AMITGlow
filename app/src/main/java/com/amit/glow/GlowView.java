package com.amit.glow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.View;

public class GlowView extends View {
    private Paint paint;
    private Paint clearPaint;
    private RectF rect;

    public GlowView(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30f);
        paint.setShadowLayer(40f, 0, 0, Color.parseColor("#00F0FF")); // Neon Cyan Glow
        setLayerType(LAYER_TYPE_SOFTWARE, paint); // Required for shadow on canvas

        // To make the center transparent
        clearPaint = new Paint();
        clearPaint.setColor(Color.TRANSPARENT);
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        
        rect = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // Draw background transparent
        canvas.drawColor(Color.TRANSPARENT);
        
        // Draw glowing border
        rect.set(15, 15, getWidth() - 15, getHeight() - 15);
        paint.setColor(Color.parseColor("#E23744")); // Zomato Red core
        canvas.drawRoundRect(rect, 50, 50, paint);
    }
}
