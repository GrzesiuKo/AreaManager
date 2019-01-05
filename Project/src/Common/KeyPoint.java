package Common;

import java.util.LinkedList;
import java.util.List;

public class KeyPoint extends Point {
    private List<Point> areaPoints;

    public KeyPoint(double x, double y) {
        super(x, y);
        areaPoints = new LinkedList<>();
    }

    public void addPoint(Point p) {
        areaPoints.add(p);
    }

    public List<Point> getAreaPoints() {
        return areaPoints;
    }
}
