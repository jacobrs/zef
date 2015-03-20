package com.steszyngagne.draw.Shapes;


import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Shape {

    protected int strokeColor;
    protected int fillColor;
    protected int strokeWidth;

    public Shape(int strokeColor, int fillColor, int lineWidth) {
        super();
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.strokeWidth = lineWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public abstract void draw(Paint paint, Canvas canvas, Boolean preview);

}
