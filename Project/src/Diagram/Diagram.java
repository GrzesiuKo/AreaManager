package Diagram;

import Common.Contour;
import Common.KeyPoint;
import Common.Point;
import FileData.FileChecker;
import FileData.FileReader;

import java.io.File;
import java.util.List;

public class Diagram {
    private List<KeyPoint> keyPoints;
    private Contour contour;
    private Cell[][] area;


    public List<KeyPoint> getKeyPoints() {
        return keyPoints;
    }

    public Contour getContour() {
        return contour;
    }

    public Cell[][] getArea() {
        return area;
    }
}
