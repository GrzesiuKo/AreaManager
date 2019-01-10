package Diagram;

import Common.Contour;
import Common.KeyPoint;
import FileData.FileChecker;
import FileData.FileReader;

import java.io.File;
import java.security.Key;
import java.util.List;

public class Diagram {
    public static int SIZE = 1000;

    private Contour contour;
    private Voronoi voronoi;

    public Diagram(){
        SIZE = 1000;
    }

    public Diagram(int size){
        SIZE = size;
    }

    public void readFile(File file) throws InvalidContourException, IncorrectFileException {
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
        contour = null;
        throw new IncorrectFileException(lineNumber);
    }

    private void handleCorrectFile(File file) throws InvalidContourException {
        FileReader fileReader = new FileReader();
        List<KeyPoint> keyPoints;

        fileReader.readFile(file);
        keyPoints = fileReader.getKeyPoints();
        voronoi = new Voronoi(SIZE, keyPoints, fileReader.getObjectPoints());
        contour = new Contour(fileReader.getContourPoints());

        if (!contour.isContourValid()){
            throw new InvalidContourException();
        }
    }

    public void addKeyPoint(KeyPoint keyPoint){
        voronoi.addKeyPoint(keyPoint);
    }

    public void remakeVoronoi(List<KeyPoint> keyPoints){
        voronoi.makeAreas(SIZE, keyPoints);
    }

    public void deleteKeyPoint(KeyPoint keyPoint){
        voronoi.deleteKeyPoint(keyPoint);
    }

    public List<KeyPoint> getKeyPoints() {
        return voronoi.getKeyPoints();
    }

    public Contour getContour() {
        return contour;
    }

    public boolean[][] getArea() {
        return voronoi.getDividedArea();
    }

    public AreaField[][] getAreaFields() {
        return voronoi.getArea();
    }
}
