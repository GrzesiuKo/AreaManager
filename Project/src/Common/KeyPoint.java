package Common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KeyPoint extends Point {
    private List<Point> areaPoints;

    public KeyPoint(double x, double y) {
        super(x, y);
        areaPoints = new ArrayList<>();
    }

    public void addPoint(Point p) {
        if (!areaPoints.contains(p)) {
            areaPoints.add(p);
        }
    }

    public List<Point> getAreaPoints() {
        return areaPoints;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
