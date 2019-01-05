package Diagram;

import Common.Contour;
import Common.KeyPoint;

import java.util.List;

public class Diagram {
    private List<KeyPoint> keyPoints;
    private Contour contour;
    private boolean[][] areaHasObject;


    public List<KeyPoint> getKeyPoints() {
        return keyPoints;
    }

    public Contour getContour() {
        return contour;
    }

    public boolean[][] getArea() {
        return areaHasObject;
    }
}
