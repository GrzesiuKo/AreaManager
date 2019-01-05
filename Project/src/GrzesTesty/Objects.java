package GrzesTesty;

import Common.KeyPoint;
import Common.Point;
import Diagram.Voronoi;

import java.util.LinkedList;

public class Objects {

    public static void main(String[] args){
        LinkedList<KeyPoint> keyPoints = new LinkedList<>();
        LinkedList<Point> objectPoints = new LinkedList<>();
        boolean[][] mapObjects;

        keyPoints.add(new KeyPoint(0.0, 0.0));
        keyPoints.add(new KeyPoint(0.0, 0.1));
        keyPoints.add(new KeyPoint(0.0, 0.2));

        objectPoints.add(new Point(0.5, 0.5));
        objectPoints.add(new Point(0.1, 0.0));
        objectPoints.add(new Point(0.2, 0.2));
        objectPoints.add(new Point(0.1, 0.2));


        Voronoi voronoi = new Voronoi(new Point(10, 10), keyPoints, objectPoints);

        mapObjects = voronoi.getDividedArea();

        printArray(mapObjects);
    }

    private static void printArray(boolean[][] array) {
        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array[0].length; x++) {
                System.out.print(array[y][x] + " ");
            }
            System.out.print(" \n");
        }
    }

}
