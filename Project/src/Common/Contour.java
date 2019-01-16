package Common;

import java.util.LinkedList;
import java.util.List;

public class Contour {
    private List<Point> contourPoints;
    private List<Point> ignoredPoints;

    public Contour(List<Point> points) {
        contourPoints = new LinkedList<>();
        ignoredPoints = new LinkedList<>();
        points = new LinkedList<>(points);
        makeConvexContour(points);
        if (!checkContour(contourPoints)) {
            contourPoints = null;
            ignoredPoints = null;
        }
    }

    private void makeConvexContour(List<Point> points) {
        Point lowest;
        Point highest;

        lowest = getLowestPoint(points);
        highest = getHighestPoint(points);

        points = makeRightSide(points, lowest, highest);
        points = makeLeftSide(points, lowest, highest);

        ignoredPoints = points;
    }

    private Point getLowestPoint(List<Point> points) {
        Point lowest = null;

        if (points == null) {
            return null;
        }
        for (Point p : points) {
            lowest = p.getSmaller(lowest);
        }

        return lowest;
    }

    private Point getHighestPoint(List<Point> points) {
        Point lowest = null;

        if (points == null) {
            return null;
        }
        for (Point p : points) {
            lowest = p.getBigger(lowest);
        }

        return lowest;
    }

    private List<Point> makeRightSide(List<Point> points, Point lowest, Point highest) {
        Point latest;
        if (points == null) {
            return null;
        }

        latest = lowest;
        points.remove(lowest);
        contourPoints.add(latest);

        while (latest != highest) {
            latest = nextRightPoint(points, latest);
            if (latest != lowest) {
                points.remove(latest);
                contourPoints.add(latest);
            }
        }
        return points;
    }

    private List<Point> makeLeftSide(List<Point> points, Point lowest, Point highest) {
        Point latest;
        if (points == null) {
            return null;
        }

        latest = highest;
        points.add(lowest);

        while (latest != lowest) {
            latest = nextLeftPoint(points, latest);
            points.remove(latest);
            if (latest != lowest) {
                contourPoints.add(latest);
            }
        }
        return points;
    }

    private Point nextRightPoint(List<Point> points, Point current) {
        Vector vector;
        Point next;
        double bestAngle, currentAngle;
        if (points == null) {
            return null;
        }

        bestAngle = 361;
        next = null;

        for (Point p : points) {
            vector = new Vector(current, p);
            currentAngle = vector.findAngleBetween(new Vector(1, 0));
            if ((current.isLowerThan(p) || current.hasTheSameHeightAs(p)) && currentAngle < bestAngle) {
                next = p;
                bestAngle = currentAngle;

            } else if (current.isHigherThan(p) && 360 - currentAngle < bestAngle) {
                next = p;
                bestAngle = 360 - currentAngle;
            }
        }
        return next;
    }

    private Point nextLeftPoint(List<Point> points, Point current) {
        Vector vector;
        Point next;
        double bestAngle, currentAngle;
        if (points == null) {
            return null;
        }

        bestAngle = 361;
        next = null;

        for (Point p : points) {
            vector = new Vector(current, p);
            currentAngle = vector.findAngleBetween(new Vector(-1, 0));
            if (currentAngle < bestAngle) {
                next = p;
                bestAngle = currentAngle;
            }
        }
        return next;
    }

    private boolean checkContour(List<Point> contourPoints) {
        boolean isEnoughPoints = false;
        boolean isThereAnyArea = false;

        if (contourPoints == null) {
            return false;
        }

        isEnoughPoints = isEnoughPoints(contourPoints);
        if (isEnoughPoints) {
            isThereAnyArea = doPointsMakeAnyArea(contourPoints);
        }

        return isEnoughPoints && isThereAnyArea;
    }

    private boolean isEnoughPoints(List<Point> contourPoints) {
        return contourPoints.size() >= 3;
    }

    private boolean doPointsMakeAnyArea(List<Point> contourPoints) {
        Point a = null, b = null, c = null;

        if (contourPoints == null) {
            return false;
        } else if (isEnoughPoints(contourPoints)) {
            a = contourPoints.get(0);
            b = contourPoints.get(1);
            c = contourPoints.get(2);
        }
        return !arePointsColinear(a, b, c);
    }

    private boolean arePointsColinear(Point first, Point second, Point third) {
        double a, b, c, d, result;
        if (first == null || second == null || third == null) {
            return false;
        }

        a = second.getY() - first.getY();
        b = third.getX() - second.getX();
        c = third.getY() - second.getY();
        d = second.getX() - first.getX();

        result = a * b - c * d;

        return result == 0;
    }

    public boolean isContourValid() {
        return contourPoints != null && ignoredPoints != null;
    }

    public List<Point> getContourPoints() {
        return contourPoints;
    }

    public List<Point> getIgnoredPoints() {
        return ignoredPoints;
    }
}
