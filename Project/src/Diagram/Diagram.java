package Diagram;

import Common.Contour;
import Common.KeyPoint;
import FileData.FileChecker;
import FileData.FileReader;

import java.io.File;
import java.util.List;

public class Diagram {
    public final static int SIZE = 1000;

    private List<KeyPoint> keyPoints;
    private Contour contour;
    private boolean[][] areaHasObject;
    private AreaField[][] area;
    private Voronoi voronoi;

    public Diagram(File file) throws IncorrectFileException, InvalidContourException {
        FileChecker fileChecker = new FileChecker();
        boolean isFileCorrect;

        isFileCorrect = fileChecker.checkFile(file);

        if (isFileCorrect) {
            handleCorrectFile(file);
        } else {
            handleIncorrectFile(fileChecker.getErrorLine());
        }
    }


    private void handleIncorrectFile(int lineNumber) throws IncorrectFileException {
        keyPoints = null;
        areaHasObject = null;
        contour = null;
        area = null;
        throw new IncorrectFileException(lineNumber);
    }

    private void handleCorrectFile(File file) throws InvalidContourException {
        FileReader fileReader = new FileReader();

        fileReader.readFile(file);
        keyPoints = fileReader.getKeyPoints();

        voronoi = new Voronoi(SIZE, keyPoints, fileReader.getObjectPoints());
        areaHasObject = voronoi.getDividedArea();
        area = voronoi.getArea();
        contour = new Contour(fileReader.getContourPoints());

        if (!contour.isContourValid()){
            throw new InvalidContourException();
        }
    }

    public void addKeyPoint(KeyPoint keyPoint){
        voronoi.addKeyPoint(keyPoint, keyPoints);
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

    public AreaField[][] getAreaFields() {
        return area;
    }
}
