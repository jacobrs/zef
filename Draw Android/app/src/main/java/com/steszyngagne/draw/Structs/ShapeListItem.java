package com.steszyngagne.draw.Structs;

public class ShapeListItem {

    public String name;
    public String token;
    public Integer num_shapes;

    public ShapeListItem(){}

    public ShapeListItem(String name, String token, Integer num_shapes) {
        this.name = name;
        this.token = token;
        this.num_shapes = num_shapes;
    }
}
