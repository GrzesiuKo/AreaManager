package Diagram;


import Common.Point;

import java.util.LinkedList;
import java.util.List;

public class Voronoi {
    private Cell[][] dividedArea;
    private int precisionX;
    private int precisionY;

    public Voronoi(Point size, LinkedList<Point> keyPoints, LinkedList<Point> objectPoints) {
        int x;
        int y;

        x = (int) size.getX();
        y = (int) size.getY();
        dividedArea = new Cell[x][y];

        precisionX = x;
        precisionY = y;

        makeAreas(keyPoints);
        addObjects(objectPoints);
    }

    private void makeAreas(LinkedList<Point> keyPoints) {

        for (int x = 0; x < dividedArea.length; x++) {
            for (int y = 0; y < dividedArea[0].length) {
                dividedArea[x][y].setColor(isBorder(x, y, keyPoints));
            }
        }

    }

    private boolean isBorder(int x, int y, LinkedList<Point> keyPoints) {
        Point point = new Point(scale(x, precisionX), scale(y, precisionY));
        double smallestLength = Double.MAX_VALUE;
        double currentLength;

        while (!keyPoints.isEmpty()) {
            currentLength = findLengthOfSegment(point, keyPoints.remove());

            if (currentLength == smallestLength || currentLength - 1 == smallestLength) {
                return true;
            } else {
                smallestLength = getSmaller(currentLength, smallestLength);
            }
        }
        return false;
    }

    private double scale(int a, int precision) {
        return ((double) a) / (double) precision;
    }

    private double findLength(Point a, Point b) {
        if (areCollinear(a, b)) {
            return findLengthOfSegment(a, b);
        }else if(areAcross(a,b)){
            return findLengthAcross(a,b);
        }else{

        }
    }

    private boolean areCollinear(Point a, Point b){
        return a.getX() == b.getX() || a.getY() == b.getY();
    }

    private int findLengthOfSegment(Point a, Point b) {
        double first = java.lang.Math.pow(a.getX() - b.getX(), 2);
        double second = java.lang.Math.pow(a.getY() - b.getY(), 2);

        return (int)java.lang.Math.sqrt(first + second);
    }

    private double getSmaller(double a, double b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    private void addObjects(List<Point> objectPoints) {

    }

    public Cell[][] getDividedArea() {
        return dividedArea;
    }
}
