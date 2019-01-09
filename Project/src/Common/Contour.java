package Common;

import java.util.LinkedList;
import java.util.List;

public class Contour {
    private List<Point> contourPoints;
    private List<Point> ignoredPoints;

    public Contour(List<Point> points) {
        contourPoints = new LinkedList<>();
        ignoredPoints = new LinkedList<>();

        makeConvexContour(points);
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


    public List<Point> getContourPoints() {
        return contourPoints;
    }

    public List<Point> getIgnoredPoints() {
        return ignoredPoints;
    }
}
