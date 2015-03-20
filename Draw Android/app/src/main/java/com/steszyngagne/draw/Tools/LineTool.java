package com.steszyngagne.draw.Tools;

import com.steszyngagne.draw.Shapes.Shape;
import com.steszyngagne.draw.ToolBox;
import com.steszyngagne.draw.Shapes.Line;

public class LineTool extends RectangleBasedTool {

    public LineTool(ToolBox tbox) {
        super(tbox, ToolName.LINE);

    }

    @Override
    public void addToDrawing(Boolean preview) {
        Line line;
        line = new Line(x1, y1, x2, y2, toolbox.getStrokeColor(), toolbox.getStrokeWidth());
        toolbox.getDrawingView().addShape(line);
        toolbox.getDrawingView().invalidate();
    }

    @Override
    public Shape getPreview() {
        return new Line(x1, y1, x2, y2, toolbox.getStrokeColor(), toolbox.getStrokeWidth());
    }


}
