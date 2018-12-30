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

    public void setEvent(Event event) {
        this.event = event;
    }

    public Cross getNearestLeftCross(){
        Item parent = this.getParent();
        Item child = this;

        if (parent == null){
            return null;
        }
        while(parent.getLeft() == child){
            if (parent.getParent() == null){
                return null;
            }else{
                child = parent;
                parent = parent.getParent();
            }
        }
        return (Cross)parent;
    }

    public Cross getNearestRightCross(){
        Item parent = this.getParent();
        Item child = this;

        if (parent == null){
            return null;
        }

        while(parent.getRight() == child){
            if (parent.getParent() == null){
                return null;
            }else{
                child = parent;
                parent = parent.getParent();
            }
        }
        return (Cross)parent;
    }
}
