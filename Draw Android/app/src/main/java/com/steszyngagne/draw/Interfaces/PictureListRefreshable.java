package com.steszyngagne.draw.Interfaces;

import com.steszyngagne.draw.Structs.ShapeListItem;

import java.util.LinkedList;

public interface PictureListRefreshable {
    public void afterRefresh(LinkedList<ShapeListItem> list);
}
