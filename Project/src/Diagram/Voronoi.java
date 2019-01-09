package Diagram;


import Common.KeyPoint;
import Common.Point;

import java.util.List;

public class Voronoi {

    private boolean[][] dividedAreaHasObject;
    private AreaField[][] area;
    private int precision = 10;
    private int size;
    private List<KeyPoint> keyPoints;

    public Voronoi(int size, List<KeyPoint> keyPoints, List<Point> objectPoints) {
        dividedAreaHasObject = new boolean[size][size];
        area = new AreaField[size][size];
        this.size = size;
        this.keyPoints = keyPoints;
        makeAreas(keyPoints, this.size);
        indicateObjects(objectPoints);
    }

    private void makeAreas(List<KeyPoint> keyPoints, int areaSize) {
        Point current;
        KeyPoint keyPoint;

        for (int x = 0; x < areaSize; x++) {
            for (int y = 0; y < areaSize; y++) {
                current = new Point(scaleToDouble(x, precision), scaleToDouble(y, precision));
                keyPoint = addToKeyPoint(current, keyPoints);
                area[x][y] = new AreaField(current, keyPoint);
            }
        }

    }

    private KeyPoint addToKeyPoint(Point point, List<KeyPoint> keyPoints) {
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

        if (nearest != null && !nearest.equals(point)) {
            nearest.addPoint(point);
        }
        return nearest;
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

    public void addKeyPoint(KeyPoint keyPoint) {
        int rim;
        boolean wasFieldAdded;
        rim = 0;
        wasFieldAdded = true;

        keyPoints.add(keyPoint);
        while (wasFieldAdded) {
            System.out.println("Seek rim: " + rim);
            wasFieldAdded = seekAround(keyPoint, rim);
            rim++;
        }

    }

    private boolean seekAround(KeyPoint keyPoint, int rimNumber) {
        int limit;
        boolean up, right, down, left, verdict;

        up = false;
        right = false;
        down = false;
        left = false;
        verdict = false;
        limit = rimNumber * 2;

        while (limit >= 0) {
            up = seekUp(keyPoint, rimNumber, limit);
            //right = seekRight(keyPoint, rimNumber);
            //down = seekDown(keyPoint, rimNumber);
            left = seekLeft(keyPoint, rimNumber, limit);
            System.out.println("Was found? " + (up || right || down || left));
            limit--;
            if (up || right || down || left) {
                verdict = true;
            }

        }
        return verdict;
    }

    private boolean seekUp(KeyPoint keyPoint, int rimNumber, int fieldNumber) {
        int x, y;
        boolean wasFound;

        x = Point.scaleCoordinateToInt(keyPoint.getX(), precision) - rimNumber + fieldNumber;
        y = Point.scaleCoordinateToInt(keyPoint.getY(), precision) + rimNumber;
        wasFound = false;

        if (y < size) {
            if (x >= 0) {
                wasFound = seek(keyPoint, new Point(x, y));
            }
        }
        return wasFound;
    }

    private boolean seekRight(KeyPoint keyPoint, int rimNumber) {
        int x, y;

        x = Point.scaleCoordinateToInt(keyPoint.getX(), precision);
        y = Point.scaleCoordinateToInt(keyPoint.getY(), precision);
        //TODO
        return false;
    }

    private boolean seekDown(KeyPoint keyPoint, int rimNumber) {
        int x, y;

        x = Point.scaleCoordinateToInt(keyPoint.getX(), precision);
        y = Point.scaleCoordinateToInt(keyPoint.getY(), precision);
        //TODO
        return false;
    }

    private boolean seekLeft(KeyPoint keyPoint, int rimNumber, int fieldNumber) {
        int x, y;
        boolean wasFound;

        x = Point.scaleCoordinateToInt(keyPoint.getX(), precision) - rimNumber;
        y = Point.scaleCoordinateToInt(keyPoint.getY(), precision) - rimNumber + fieldNumber;
        wasFound = false;

        if (x >= 0) {
            if (y <= size && y >= 0) {
                wasFound = seek(keyPoint, new Point(x, y));
            }
        }
        return wasFound;
    }

    private boolean seek(KeyPoint keyPoint, Point point) {
        KeyPoint nearest;
        int x, y;
        nearest = addToGivenKeyPoint(keyPoint, keyPoints, point);

        if (nearest.equals(keyPoint)) {
            x = (int) point.getX();
            y = (int) point.getY();

            area[x][y].setNearestKeyPoint(keyPoint);
            return true;
        }
        return false;
    }

    private KeyPoint addToGivenKeyPoint(KeyPoint given, List<KeyPoint> keyPoints, Point point) {
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

        if (nearest != null && !nearest.equals(point) && nearest.equals(given)) {
            nearest.addPoint(point);
        }
        return nearest;
    }

    public boolean[][] getDividedArea() {
        return dividedAreaHasObject;
    }

    public AreaField[][] getArea() {
        return area;
    }

    public List<KeyPoint> getKeyPoints() {
        return keyPoints;
    }
}
