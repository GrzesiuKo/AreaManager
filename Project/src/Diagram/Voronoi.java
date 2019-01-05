package Diagram;


import Common.Point;

import java.util.LinkedList;
import java.util.List;

public class Voronoi {
    private Cell[][] dividedArea;

    public Voronoi(Point size, LinkedList<Point> keyPoints, LinkedList<Point> objectPoints) {
        int x;
        int y;

        x = (int) size.getX();
        y = (int) size.getY();
        dividedArea = new Cell[x][y];

        makeAreas(keyPoints);
        addObjects(objectPoints);
    }

    private void makeAreas(List<Point> keyPoints) {
        Point currentPoint;

        while (!keyPoints.isEmpty()) {

        }

    }

    private void addObjects(List<Point> objectPoints) {

    }

    public Cell[][] getDividedArea() {
        return dividedArea;
    }
}
