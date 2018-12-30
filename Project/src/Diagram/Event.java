package Diagram;

import Common.Point;


public class Event implements Comparable<Event> {
    public static boolean SITE_EVENT = true;
    public static boolean CIRCLE_EVENT = false;

    private boolean type;
    private Point point;
    private Arc arc;

    public Event(Point p, boolean type) {
        point = p;
        this.type = type;
    }

    public boolean isSiteEvent() {
        return type;
    }

    public boolean isCircleEvent() {
        return !type;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public int compareTo(Event e) {
        double result = this.getPoint().getY() - e.getPoint().getY();
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        }
        return 0;
    }

    public void setArc(Arc arc) {
        this.arc = arc;
    }

    public Arc getArc() {
        return arc;
    }
}
