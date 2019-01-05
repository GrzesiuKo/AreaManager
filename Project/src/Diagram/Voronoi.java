package Diagram;


import Common.KeyPoint;
import Common.Point;

import java.util.LinkedList;
import java.util.List;

public class Voronoi {

    private boolean[][] dividedAreaHasObject;
    private int precisionX;
    private int precisionY;

    public Voronoi(Point size, LinkedList<KeyPoint> keyPoints, LinkedList<Point> objectPoints) {
        int x;
        int y;

        x = (int) size.getX();
        y = (int) size.getY();
        dividedAreaHasObject = new boolean[x][y];

        precisionX = x;
        precisionY = y;

        makeAreas(keyPoints);
        indicateObjects(objectPoints);
    }

    private void makeAreas(LinkedList<KeyPoint> keyPoints) {
        Point current;

        for (int x = 0; x < dividedAreaHasObject.length; x++) {
            for (int y = 0; y < dividedAreaHasObject[0].length; y++) {
                current = new Point(scale(x, precisionX), scale(y, precisionY));
                addToKeyPoint(current, keyPoints);
            }
        }
    }

    private void addToKeyPoint(Point point, LinkedList<KeyPoint> keyPoints) {
        KeyPoint nearest = null;
        double smallestLength = Double.MAX_VALUE;
        double currentLength;

        for (KeyPoint keyPoint : keyPoints) {
            currentLength = findLengthOfSegment(point, keyPoint);
            if (currentLength < smallestLength) {
                nearest = keyPoint;
                smallestLength = currentLength;
            }
        }
        nearest.addPoint(point);
    }

    private double scale(int a, int precision) {
        return ((double) a) / (double) precision;
    }


    private double findLengthOfSegment(Point a, Point b) {
        double first = java.lang.Math.pow(a.getX() - b.getX(), 2);
        double second = java.lang.Math.pow(a.getY() - b.getY(), 2);

        return java.lang.Math.sqrt(first + second);
    }


    private void indicateObjects(List<Point> objectPoints) {
        Point current;


        for (int y = 0; y < dividedAreaHasObject.length; y++) {
            for (int x = 0; x < dividedAreaHasObject[0].length; x++) {
                current = new Point(scale(x, precisionX), scale(y, precisionY));

                if (objectBelongsToTheField(current, objectPoints)) {
                    dividedAreaHasObject[y][x] = true;
                } else {
                    dividedAreaHasObject[y][x] = false;
                }
            }
        }
    }

    private boolean objectBelongsToTheField(Point field, List<Point> objectPoints) {
        for (Point p : objectPoints) {
            System.out.println("Porónuje punkty: pole x = " + field.getX() + " y = " + field.getY() + " oraz objekt x = " + p.getX() + " y = " + p.getY());
            if (p.equals(field)) {
                return true;
            }
        }
        return false;
    }

    public boolean[][] getDividedArea() {
        return dividedAreaHasObject;
    }
}
