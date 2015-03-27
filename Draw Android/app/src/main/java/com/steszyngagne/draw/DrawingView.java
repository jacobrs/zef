package com.steszyngagne.draw;

import com.steszyngagne.draw.Shapes.Shape;
import com.steszyngagne.draw.Tools.Tool;
import com.steszyngagne.draw.Tools.ToolName;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

    private Picture picture;
    private ToolBox toolbox;

    private Tool preview;

    private Paint paint;
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.picture = new Picture();
        this.toolbox = new ToolBox(this);
        toolbox.changeTool(ToolName.LINE);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        picture.draw(paint, canvas);
        // Log.d("Shapes", picture.shapesToJson().toString()); // Lazy logging
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                toolbox.getCurrentTool().touchStart(event);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                Picture.PREVIEW = null;
                toolbox.getCurrentTool().preview = false;
                toolbox.getCurrentTool().touchEnd(event);
                break;

            default:
                toolbox.getCurrentTool().preview = true;
                toolbox.getCurrentTool().touchMove(event);
        }
        return true;
    }

    public void addShape(Shape s) {
        picture.add(s);
    }

    public ToolBox getToolBox() {
        return toolbox;
    }

    public void erase() {
        picture.clear();
    }

    public void undo() {picture.undo();}

    public Picture getPicture() {
        return picture;
    }
}
