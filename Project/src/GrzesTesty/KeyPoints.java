package GrzesTesty;

import Common.KeyPoint;
import Common.Point;
import Diagram.Voronoi;

import java.util.LinkedList;

public class KeyPoints {

    public static void main(String[] args) {
        LinkedList<KeyPoint> keyPoints = new LinkedList<>();
        LinkedList<Point> objectPoints = new LinkedList<>();
        boolean[][] mapObjects;

        keyPoints.add(new KeyPoint(0.0, 0.0));
        keyPoints.add(new KeyPoint(0.0, 0.1));
        keyPoints.add(new KeyPoint(0.5, 0.5));

        objectPoints.add(new Point(0.5, 0.5));
        objectPoints.add(new Point(0.1, 0.0));
        objectPoints.add(new Point(0.2, 0.2));
        objectPoints.add(new Point(0.1, 0.2));

        int xSize = 100;
        int ySize =100;

        Voronoi voronoi = new Voronoi(new Point(xSize, ySize), keyPoints, objectPoints);
        int[][] tab = new int[xSize][ySize];
        double x, y;
        int i = 1;
        for (KeyPoint k : keyPoints) {
            System.out.println("Punkty należące do obszaru punktu kluczowego (" + k.getX() + ", " + k.getY() + ")");
            for (Point p : k.getAreaPoints()) {
                x = p.getX();
                y = p.getY();
                //System.out.println("("+x+", "+y+")");
                x *= xSize;
                y *= ySize;

                x = Math.round(x);
                y = Math.round(y);

                System.out.println("(" + x + ", " + y + ")");
                tab[(int) y][(int) x] = i;
            }
            System.out.println();
            i++;
        }
        printArray(tab);
    }

    private static void printArray(int[][] array) {

        for (int y = 0; y < array[0].length; y++) {
            System.out.print(y+".  ");
            for (int x = 0; x < array.length; x++) {
                System.out.print(array[x][y]);
            }
            System.out.print(" \n");
        }
    }

}
