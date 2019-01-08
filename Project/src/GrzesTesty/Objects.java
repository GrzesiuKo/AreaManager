package GrzesTesty;

import Common.KeyPoint;
import Common.Point;
import Diagram.Voronoi;

import java.util.LinkedList;

public class Objects {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        LinkedList<KeyPoint> keyPoints = new LinkedList<>();
        LinkedList<Point> objectPoints = new LinkedList<>();
        boolean[][] mapObjects;
        int areaSize = 20;

        keyPoints.add(new KeyPoint(0.0, 0.0));
        keyPoints.add(new KeyPoint(0.0, 0.1));
        keyPoints.add(new KeyPoint(0.0, 0.2));

        objectPoints.add(new Point(0.5, 0.5));
        objectPoints.add(new Point(0.1, 0.0));
        objectPoints.add(new Point(0.2, 0.2));
        objectPoints.add(new Point(0.1, 0.2));


        Voronoi voronoi = new Voronoi(areaSize, keyPoints, objectPoints);

        mapObjects = voronoi.getDividedArea();

        printArray(mapObjects);
    }

    private static void printArray(boolean[][] array) {
        for (int y = 0; y < array[0].length; y++) {
            for (int x = 0; x < array.length; x++) {
                if (array[x][y]) {
                    System.out.print(ANSI_GREEN+array[x][y]+ANSI_RESET + " ");
                } else {
                    System.out.print(array[x][y] + " ");
                }
            }
            System.out.print(" \n");
        }
    }

}
