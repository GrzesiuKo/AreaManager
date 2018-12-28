package Diagram;

import Common.Point;

public class Event {
    public static boolean SITE_EVENT = true;
    public static boolean CIRCLE_EVENT = false;

    private boolean type;
    private Point point;

    public Event(Point p, boolean type){
        point = p;
        this.type = type;
    }

    public boolean isSiteEvent(){
        return type;
    }

    public boolean isCircleEvent() {
        return !type;
    }

    public Point getPoint() {
        return point;
    }
}
