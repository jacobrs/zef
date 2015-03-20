package com.steszyngagne.draw.Shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.steszyngagne.draw.ToolBox;

public class Rectangle extends Shape {

    private float x1, y1, x2, y2;

    public Rectangle(float x1, float y1, float x2, float y2, int strokeColor, int width) {
        this(x1, y1, x2, y2, strokeColor, width, Color.TRANSPARENT);
    }

    public Rectangle(float x1, float y1, float x2, float y2, int strokeColor, int strokeWidth, int fillColor) {
        super(strokeColor, fillColor, strokeWidth);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Paint paint, Canvas canvas, Boolean preview) {

        paint.setPathEffect(null);

        if(preview) {
            paint =new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.GRAY);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(2);
            paint.setPathEffect(new DashPathEffect(new float[]{4.0f, 4.0f}, 1.0f));
            canvas.drawRect(x1, y1, x2, y2, paint);
        }else{
            if(fillColor != Color.TRANSPARENT) {
                paint.setColor(fillColor);
                paint.setStyle(Style.FILL);
                canvas.drawRect(x1,y1,x2,y2, paint);
            }
            if(strokeColor != Color.TRANSPARENT && strokeWidth > 0) {
                paint.setStyle(Style.STROKE);
                paint.setColor(strokeColor);
                paint.setStrokeWidth(strokeWidth);
                paint.setStrokeCap(Paint.Cap.ROUND);
                canvas.drawRect(x1,y1,x2,y2, paint);
            }
        }

    }

}
