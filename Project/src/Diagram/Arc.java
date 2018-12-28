package Diagram;

import Common.Math;
import Common.Point;

public class Arc extends Item {
    Point focus;
    Event event;

    public Arc(Point focus) {
        this.focus = focus;
    }

    public double findY(double x) {
        return Math.findYonTheArc(focus, x);
    }

    public Point getFocus() {
        return focus;
    }
}
