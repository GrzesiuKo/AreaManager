package Diagram;

import Common.Math;
import Common.Point;

public class Edge {
    private Point start;
    private Point end;
    private Edge sister;
    private double slope;
    private double InterceptY;

    public Edge(Point start, Point a, Point b) {
        this.start = start;
        findEdgeFormula(a, b);
    }

    public void setSister(Edge sister) {
        this.sister = sister;
    }

    private void findEdgeFormula(Point a, Point b){
        Math.TwoResults data = Math.findBisector(a, b);
        slope = data.getFirst();
        InterceptY = data.getSecond();
    }



    public double getSlope() {
        return slope;
    }

    public double getInterceptY() {
        return InterceptY;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
