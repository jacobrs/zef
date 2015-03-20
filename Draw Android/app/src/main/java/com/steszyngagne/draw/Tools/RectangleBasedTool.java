package com.steszyngagne.draw.Tools;

import com.steszyngagne.draw.Picture;
import com.steszyngagne.draw.ToolBox;
import android.view.MotionEvent;

public abstract class RectangleBasedTool extends Tool {

    protected float x1;
    protected float y1;
    protected float x2;
    protected float y2;

    public static boolean LEFT;
    public static boolean BOTTOM;

    public RectangleBasedTool(ToolBox tbox, ToolName name) {
        super(tbox, name);
    }

    @Override
    public void touchStart(MotionEvent event) {
        x1 = x2 = event.getX();
        y1 = y2 = event.getY();
    }

    public void checkForBackwards(float newx, float newy){
        if(newx > x1 && newx > x2){
            x2 = newx;
            LEFT = false;
        }else if(newx < x1 && newx < x2){
            LEFT = true;
            x1 = newx;
        }else if(newx > x1 && newx < x2){
            if(LEFT) {
                x1 = newx;
            }else{
                x2 = newx;
            }
        }

        if(newy > y1 && newy > y2){
            y2 = newy;
            BOTTOM = true;
        }else if(newy < y1 && newy < y2){
            BOTTOM = false;
            y1 = newy;
        }else if(newy > y1 && newy < y2){
            if(BOTTOM) {
                y2 = newy;
            }else{
                y1 = newy;
            }
        }
    }

    @Override
    public void touchEnd(MotionEvent event) {
        x2 = event.getX();
        y2 = event.getY();
        validateDraw();
    }

    @Override
    public void touchMove(MotionEvent event) {
        x2 = event.getX();
        y2 = event.getY();
        validateDraw();
    }

    @Override
    public void validateDraw(){
        if(super.preview){
            Picture.PREVIEW = getPreview();
            toolbox.getDrawingView().invalidate();
        }
        else{
            addToDrawing(false);
        }
    }
}
