package com.requiem.abstractentities.pathfinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trent on 1/21/2015.
 */
public class PathVertex {
    public float x;
    public float y;
    public float z;

    public List<PathConvexShape> connectedShapes;

    public PathVertex() {
        connectedShapes = new ArrayList<PathConvexShape>();
    }

    public PathVertex(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        connectedShapes = new ArrayList<PathConvexShape>();
    }
}