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

    }

    public List<Point> getContourPoints() {
        return contourPoints;
    }

    public List<Point> getIgnoredPoints() {
        return ignoredPoints;
    }
}
