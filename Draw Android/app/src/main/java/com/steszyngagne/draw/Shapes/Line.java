package com.steszyngagne.draw.Shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

import com.steszyngagne.draw.ToolBox;

import org.json.JSONException;
import org.json.JSONObject;

public class Line extends Shape {

    private float x1, x2, y1, y2;

    public Line(float x1, float y1, float x2, float y2, int strokeColor, int strokeWidth) {
        super(strokeColor, Color.TRANSPARENT, strokeWidth);
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
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
            Path path = new Path();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            canvas.drawPath(path, paint);
        }else{
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            paint.setStrokeWidth(strokeWidth);
            paint.setStrokeCap(Paint.Cap.ROUND);
            Path path = new Path();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            canvas.drawPath(path, paint);
        }

    }

    @Override
    public JSONObject shapeToJson(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("shape", "Line");
            obj.put("x1", this.x1);
            obj.put("x2", this.x2);
            obj.put("y1", this.y1);
            obj.put("y2", this.y2);
            obj.put("fillColor", this.getFillColor());
            obj.put("strokeColor", this.getStrokeColor());
            obj.put("strokeColor", this.getStrokeWidth());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
