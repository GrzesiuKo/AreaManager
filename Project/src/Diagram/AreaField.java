package Diagram;

import Common.KeyPoint;
import Common.Point;

public class AreaField {
    Point position;
    KeyPoint nearestKeyPoint;

    public AreaField(Point position, KeyPoint nearestKeyPoint) {
        this.position = position;
        this.nearestKeyPoint = nearestKeyPoint;
    }

    public Point getPosition() {
        return position;
    }

    public KeyPoint getNearestKeyPoint() {
        return nearestKeyPoint;
    }

    public void setNearestKeyPoint(KeyPoint nearestKeyPoint) {
        this.nearestKeyPoint = nearestKeyPoint;
    }
}
