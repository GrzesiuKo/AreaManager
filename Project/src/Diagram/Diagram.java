package Diagram;

import Common.Contour;
import Common.Point;
import FileData.FileChecker;
import FileData.FileReader;

import java.io.File;
import java.util.List;

public class Diagram {
    private List<Point> keyPoints;
    private Contour contour;
    private Cell[][] area;


    public List<Point> getKeyPoints() {
        return keyPoints;
    }

    public Contour getContour() {
        return contour;
    }

    public Cell[][] getArea() {
        return area;
    }
}
