package com.steszyngagne.draw.Tools;

import android.view.MotionEvent;

import com.steszyngagne.draw.Shapes.Shape;
import com.steszyngagne.draw.ToolBox;
import com.steszyngagne.draw.Shapes.Rectangle;

public class RectangleTool extends RectangleBasedTool {

    public RectangleTool(ToolBox tbox) {
        super(tbox, ToolName.RECTANGLE);
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
        validateDraw();
    }

    @Override
    public void touchMove(MotionEvent event){
        float nx2 = event.getX();
        float ny2 = event.getY();
        checkForBackwards(nx2, ny2);
        validateDraw();
    }

}
