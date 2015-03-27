package com.steszyngagne.draw.Tools;

import android.view.MotionEvent;

import com.steszyngagne.draw.Shapes.Rectangle;
import com.steszyngagne.draw.Shapes.Shape;
import com.steszyngagne.draw.ToolBox;

public class SquareTool extends RectangleBasedTool {

    public SquareTool(ToolBox tbox) {
        super(tbox, ToolName.SQUARE);
    }

    @Override
    public void addToDrawing(Boolean preview) {
        Rectangle line = new Rectangle(x1,y1,x2, y2, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
        toolbox.getDrawingView().addShape(line);
        toolbox.getDrawingView().invalidate();
    }

    @Override
    public Shape getPreview() {
        return new Rectangle(x1,y1,x2, y2, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
    }

    @Override
    public void touchEnd(MotionEvent event) {
        float nx2 = event.getX();
        float ny2 = event.getY();
        checkForBackwards(nx2, ny2);
        // width is less than height
        if(x2 - x1 < y2 - y1){
            if(RectangleBasedTool.BOTTOM) {
                y2 = y1 + (x2 - x1);
            }else{
                y1 = y2 - (x2 - x1);
            }
        }
        // height is less than or equal to width
        else{
            if(RectangleBasedTool.LEFT){
                x1 = x2 - (y2 - y1);
            }else{
                x2 = x1 + (y2 - y1);
            }
        }
        validateDraw();
    }

    @Override
    public void touchMove(MotionEvent event) {
        float nx2 = event.getX();
        float ny2 = event.getY();
        checkForBackwards(nx2, ny2);
        // width is less than height
        if(x2 - x1 < y2 - y1){
            if(RectangleBasedTool.BOTTOM) {
                y2 = y1 + (x2 - x1);
            }else{
                y1 = y2 - (x2 - x1);
            }
        }
        // height is less than or equal to width
        else{
            if(RectangleBasedTool.LEFT){
                x1 = x2 - (y2 - y1);
            }else{
                x2 = x1 + (y2 - y1);
            }
        }
        validateDraw();
    }

}
