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

    public List<KeyPoint> getKeyPoints() {
        return keyPoints;
    }

    public Contour getContour() {
        return contour;
    }

    public boolean[][] getArea() {
        return areaHasObject;
    }

    private void handleIncorrectFile(int lineNumber) throws IncorrectFileException {
        keyPoints = null;
        areaHasObject = null;
        contour = null;
        throw new IncorrectFileException(lineNumber);
    }

    private void handleCorrectFile(File file) throws InvalidContourException {
        FileReader fileReader = new FileReader();
        Voronoi voronoi;

        fileReader.readFile(file);
        keyPoints = fileReader.getKeyPoints();

        voronoi = new Voronoi(SIZE, keyPoints, fileReader.getObjectPoints());
        areaHasObject = voronoi.getDividedArea();

        contour = new Contour(fileReader.getContourPoints());

        if (!contour.isContourValid()){
            throw new InvalidContourException();
        }
    }
}
