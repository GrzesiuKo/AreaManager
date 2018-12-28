package Diagram;

import Common.Point;

public class Arc extends Item {
    Point focus;
    Event event;

    public Arc(Point focus) {
        this.focus = focus;
    }

    public Point getFocus() {
        return focus;
    }
}
