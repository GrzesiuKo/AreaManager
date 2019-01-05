package Diagram;


import Common.Point;

import java.util.LinkedList;
import java.util.List;

public class Voronoi {
    private Cell[][] dividedArea;

    public Voronoi(LinkedList<Point> contourPoints, LinkedList<Point> keyPoints, LinkedList<Point> objectPoints) {
        Point areaSize;
        int x;
        int y;

        areaSize = findAreaSize(contourPoints);
        x = (int) areaSize.getX();
        y = (int) areaSize.getY();
        dividedArea = new Cell[x][y];

        makeAreas(keyPoints);
        addObjects(objectPoints);
    }


    private Point findAreaSize(LinkedList<Point> contourPoints) {
        Point current;
        double maxY, maxX, minY, minX, xCurrent, yCurrent;
        double xSize, ySize;

        if (contourPoints == null) {
            return null;
        } else {
            maxX = 0;
            maxY = 0;
            minX = Double.MIN_VALUE;
            minY = Double.MAX_VALUE;
        }

        while (!contourPoints.isEmpty()) {
            current = contourPoints.remove();
            xCurrent = current.getX();
            yCurrent = current.getY();

            maxX = checkMax(xCurrent, maxX);
            minX = checkMin(xCurrent, minX);

            maxY = checkMax(yCurrent, maxY);
            minY = checkMin(yCurrent, minY);
        }

        xSize = getSize(minX, maxX);
        ySize = getSize(minY, maxY);

        return new Point(xSize, ySize);
    }

    private double checkMax(double current, double max) {
        if (current > max) {
            return current;
        } else {
            return max;
        }
    }

    private double checkMin(double current, double min) {
        if (current < min) {
            return current;
        } else {
            return min;
        }
    }

    private double getSize(double max, double min) {
        return max - min + 1;
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
