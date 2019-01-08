package Common;

import java.util.LinkedList;
import java.util.List;

public class Contour {
    private List<Point> contourPoints;
    private List<Point> ignoredPoints;

    public Contour(List<Point> points) {
        contourPoints = new LinkedList<>();
        ignoredPoints = new LinkedList<>();

        makeContour(points);
    }

    private void makeContour(List<Point> points) {
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
        contourPoints.add(latest);

        while (latest != lowest) {
            latest = nextLeftPoint(points, latest);
            if (latest != highest) {
                points.remove(latest);
                contourPoints.add(latest);
            }
        }
        return points;
    }

    private Point nextRightPoint(List<Point> points, Point current) {
        //TODO
        return null;
    }

    private Point nextLeftPoint(List<Point> points, Point current) {
        //TODO
        return null;
    }


    public List<Point> getContourPoints() {
        return contourPoints;
    }

    public List<Point> getIgnoredPoints() {
        return ignoredPoints;
    }
}
