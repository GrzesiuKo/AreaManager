package Diagram;

import Common.Math;
import Common.Point;

public class Cross extends Item {
    Edge edge;

    public double getCurrentX() {
        Arc nearestLeftArc;
        Arc nearestRightArc;
        Math.TwoResults result;

        nearestLeftArc = this.getNearestLeftArc();
        nearestRightArc = this.getNearestRightArc();
        result = Math.findCommonXofTwoArcs( nearestLeftArc,  nearestRightArc, Voronoi.currentYofSweepLine);

        return chooseResult( nearestLeftArc.getFocus(), nearestRightArc.getFocus(), result);
    }

    private double chooseResult(Point left, Point right, Math.TwoResults results) {
        if (left.getY() > right.getY()) {
            return results.getBigger();
        } else {
            return results.getSmaller();
        }
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public Edge getEdge() {
        return edge;
    }
}
