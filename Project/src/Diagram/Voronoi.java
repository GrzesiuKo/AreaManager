package Diagram;


import Common.KeyPoint;
import Common.Point;

import java.util.List;

public class Voronoi {

    private boolean[][] dividedAreaHasObject;
    private int precision = 10;

    public Voronoi(int size, List<KeyPoint> keyPoints, List<Point> objectPoints) {
        dividedAreaHasObject = new boolean[size][size];

        makeAreas(keyPoints);
        indicateObjects(objectPoints);
    }

    private void makeAreas(List<KeyPoint> keyPoints) {
        Point current;

        for (int x = 0; x < dividedAreaHasObject.length; x++) {
            for (int y = 0; y < dividedAreaHasObject[0].length; y++) {
                current = new Point(scaleToDouble(x, precision), scaleToDouble(y, precision));
                addToKeyPoint(current, keyPoints);
            }
        }
    }

    private void addToKeyPoint(Point point, List<KeyPoint> keyPoints) {
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

        if (nearest!=null && !nearest.equals(point)) {
            nearest.addPoint(point);
        }
    }

    private double scaleToDouble(int a, int precision) {
        return ((double) a) / (double) precision;
    }


    private double findLengthOfSegment(Point a, Point b) {
        double first = java.lang.Math.pow(a.getX() - b.getX(), 2);
        double second = java.lang.Math.pow(a.getY() - b.getY(), 2);

        return java.lang.Math.sqrt(first + second);
    }


    private void indicateObjects(List<Point> objectPoints) {
        Point current;

         for (int y = 0; y < dividedAreaHasObject[0].length; y++) {
            for (int x = 0; x < dividedAreaHasObject.length; x++) {
                current = new Point(scaleToDouble(x, precision), scaleToDouble(y, precision));

                if (objectBelongsToTheField(current, objectPoints)) {
                    dividedAreaHasObject[x][y] = true;
                } else {
                    dividedAreaHasObject[x][y] = false;
                }
            }
        }
    }

    private boolean objectBelongsToTheField(Point field, List<Point> objectPoints) {
        for (Point p : objectPoints) {
            //System.out.println("Porónuje punkty: pole x = " + field.getX() + " y = " + field.getY() + " oraz objekt x = " + p.getX() + " y = " + p.getY());
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
