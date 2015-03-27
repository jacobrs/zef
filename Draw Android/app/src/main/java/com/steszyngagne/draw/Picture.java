package com.steszyngagne.draw;

import java.util.LinkedList;
import java.util.List;

import com.steszyngagne.draw.Shapes.Shape;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Picture {

    private List<Shape> shapes;
    public static Shape PREVIEW;

    public Picture() {
        super();
        shapes = new LinkedList<Shape>();
    }

    public void draw(Paint paint, Canvas canvas) {
        for(Shape shape : shapes)
            shape.draw(ToolBox.PREVIEW_PAINT, canvas, false);

        if(PREVIEW != null) {
            PREVIEW.draw(ToolBox.PREVIEW_PAINT, canvas, true);
        }
    }

    public void add(Shape s) {
        shapes.add(s);
    }

    public void clear() {
        shapes.clear();
    }

    public void undo(){
        if(!shapes.isEmpty())
            shapes.remove(shapes.size()-1);
    }

    public List<Shape> getShapes(){
        return shapes;
    }

    public JSONObject shapesToJson() throws JSONException {
        JSONObject container = new JSONObject();
        JSONArray array = new JSONArray();

        for (int i = 0; i < shapes.size(); i++){
            array.put(shapes.get(i).shapeToJson());
        }

        container.put("shapes", array);

        return container;
    }
}
