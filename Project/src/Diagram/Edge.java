package Diagram;

import Common.Point;

public class Edge {
    private Point start;
    private Point end;
    private Edge sister;

    public Edge(Point start) {
        this.start = start;
    }

    public void setSister(Edge sister) {
        this.sister = sister;
    }
}
