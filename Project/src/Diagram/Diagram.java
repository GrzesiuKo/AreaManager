package Diagram;

import Common.Contour;
import Common.KeyPoint;
import Common.Point;
import Exceptions.IncorrectDefinitionUnknownTypeException;
import Exceptions.IncorrectLineException;
import Exceptions.IncorrectObjectLineException;
import Exceptions.InvalidContourException;
import FileData.FileChecker;
import FileData.FileReader;
import Statistics.Statistics;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public void readFile(File file) throws InvalidContourException, IncorrectLineException, IncorrectObjectLineException, IncorrectDefinitionUnknownTypeException {
        FileChecker fileChecker = new FileChecker();
        boolean isFileCorrect;

        isFileCorrect = fileChecker.checkFile(file);

        if (isFileCorrect) {
            handleCorrectFile(file, fileChecker.getDefinitions());
        }
    }


    private void handleCorrectFile(File file, Map<String, LinkedList<Integer>> definitions) throws InvalidContourException {
        FileReader fileReader = new FileReader();
        List<KeyPoint> keyPoints;


        Statistics.delete();
        fileReader.readFile(file, definitions);
        keyPoints = fileReader.getKeyPoints();
        voronoi = new Voronoi(SIZE, keyPoints, fileReader.getObjectPoints());
        contour = new Contour(fileReader.getContourPoints());

        if (!contour.isContourValid()){
            throw new InvalidContourException();
        }
    }

    public boolean addContourPoint(Point point){
        List<Point> points;
        Contour newContour;

        points = new LinkedList<>(contour.getContourPoints());
        points.add(point);
        newContour = new Contour(points);

        if (newContour.getContourPoints().contains(point)){
            contour = newContour;
            return true;
        }else{
            return false;
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

    public void setContour(Contour contour) {
        this.contour = contour;
    }
}
