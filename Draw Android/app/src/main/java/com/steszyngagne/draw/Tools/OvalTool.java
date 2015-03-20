package com.steszyngagne.draw.Tools;

import android.view.MotionEvent;

import com.steszyngagne.draw.Shapes.Oval;
import com.steszyngagne.draw.Shapes.Shape;
import com.steszyngagne.draw.ToolBox;

public class OvalTool extends RectangleBasedTool {

    public OvalTool(ToolBox tbox) {
        super(tbox, ToolName.OVAL);
    }

    @Override
    public void addToDrawing(Boolean preview) {
        Oval line = new Oval(x1,y1,x2, y2, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
        toolbox.getDrawingView().addShape(line);
        toolbox.getDrawingView().invalidate();
    }

    @Override
    public Shape getPreview() {
        return new Oval(x1,y1,x2, y2, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
    }

    @Override
    public void touchEnd(MotionEvent event) {
        float tx2 = event.getX();
        float ty2 = event.getY();
        checkForBackwards(tx2, ty2);
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
