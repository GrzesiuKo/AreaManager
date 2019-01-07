package Diagram;

import Common.Contour;
import Common.KeyPoint;
import Common.Point;
import FileData.FileChecker;
import FileData.FileReader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Diagram {
    public final static int X_SIZE = 10;
    public final static int Y_SIZE = 10;

    private List<KeyPoint> keyPoints;
    private Contour contour;
    private boolean[][] areaHasObject;
    private boolean isFileCorrect;

    public Diagram(File file) {
        FileChecker fileChecker = new FileChecker();
        FileReader fileReader = new FileReader();
        Voronoi voronoi;

        isFileCorrect = fileChecker.checkFile(file);

        if (isFileCorrect) {
            fileReader.readFile(file);

            keyPoints = fileReader.getKeyPoints();

            voronoi = new Voronoi(new Point(X_SIZE, Y_SIZE), keyPoints, fileReader.getObjectPoints());

            areaHasObject = voronoi.getDividedArea();

            contour = new Contour(fileReader.getContourPoints());
        }else{
            keyPoints = null;
            areaHasObject = null;
            contour = null;
        }
    }

    public List<KeyPoint> getKeyPoints() {
        return keyPoints;
    }

    public Contour getContour() {
        return contour;
    }

    public boolean[][] getArea() {
        return areaHasObject;
    }

    public boolean isFileCorrect() {
        return isFileCorrect;
    }
}
