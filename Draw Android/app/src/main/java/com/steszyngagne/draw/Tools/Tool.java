package com.steszyngagne.draw.Tools;

import com.steszyngagne.draw.Shapes.Shape;
import com.steszyngagne.draw.ToolBox;
import android.view.MotionEvent;

public abstract class Tool {

    protected ToolBox toolbox;
    protected ToolName name;

    public boolean preview = false;

    public Tool(ToolBox toolbox, ToolName name) {
        super();
        this.toolbox = toolbox;
        this.name = name;
    }

    public ToolName getName() {
        return name;
    }

    public abstract void touchStart(MotionEvent event);
    public abstract void touchEnd(MotionEvent event);
    public abstract void touchMove(MotionEvent event);
    public abstract void addToDrawing(Boolean preview);

    public abstract void validateDraw();
    public abstract Shape getPreview();
}
