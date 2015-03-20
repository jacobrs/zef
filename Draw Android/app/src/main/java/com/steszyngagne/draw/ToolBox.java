package com.steszyngagne.draw;

import com.steszyngagne.draw.Tools.*;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

public class ToolBox {

    private DrawingView drawing;
    private Tool currentTool;
    private int strokeWidth;
    private int strokeColor;
    private int fillColor;
    public static Paint PREVIEW_PAINT;

    public ToolBox(DrawingView drawing) {
        super();
        this.drawing = drawing;
        this.strokeWidth = 2;
        this.strokeColor = Color.BLACK;
        this.fillColor = Color.TRANSPARENT;

        PREVIEW_PAINT =new Paint();
        PREVIEW_PAINT.setStyle(Paint.Style.STROKE);
        PREVIEW_PAINT.setColor(Color.GRAY);
        PREVIEW_PAINT.setStrokeCap(Paint.Cap.ROUND);
        PREVIEW_PAINT.setStrokeWidth(2);
        PREVIEW_PAINT.setPathEffect(new DashPathEffect(new float[]{4.0f, 4.0f}, 1.0f));
    }

    public void changeTool(ToolName name) {
        if (currentTool == null || currentTool.getName() != name) {
            switch (name) {
                case LINE:
                    currentTool = new LineTool(this);
                    break;
                case RECTANGLE:
                    currentTool = new RectangleTool(this);
                    break;
                case SQUARE:
                    currentTool = new SquareTool(this);
                    break;
                case OVAL:
                    currentTool = new OvalTool(this);
                    break;
                case CIRCLE:
                    currentTool = new CircleTool(this);
                    break;
                default:
                    currentTool = new LineTool(this);
                    break;
            }
        }
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public ToolName getCurrentToolName() {
        return currentTool.getName();
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int color) {
        this.strokeColor = color;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int color) {
        this.fillColor = color;
    }

    public DrawingView getDrawingView() {
        return drawing;
    }

}
