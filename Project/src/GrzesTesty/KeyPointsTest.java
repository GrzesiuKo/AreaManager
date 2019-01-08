package GrzesTesty;

import Common.KeyPoint;
import Common.Point;
import Diagram.Voronoi;

import java.util.LinkedList;

public class KeyPointsTest {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

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

        int size = 10;
        int precision = 10;
        Voronoi voronoi = new Voronoi(size, keyPoints, objectPoints);
        int[][] tab = new int[size][size];
        double x, y;
        int i = 1;
        int color = 0;
        for (KeyPoint k : keyPoints) {
            System.out.println("Punkty należące do obszaru punktu kluczowego (" + k.getX() + ", " + k.getY() + ")");
            for (Point p : k.getAreaPoints()) {
                x = p.getX();
                y = p.getY();
                //System.out.println("("+x+", "+y+")");
                x = Point.scaleCoordinateToInt(x, precision);
                y = Point.scaleCoordinateToInt(y, precision);
                if (color == 1) {
                    System.out.println(ANSI_GREEN + "(" + x + ", " + y + ")" + ANSI_RESET);
                } else {
                    System.out.println("(" + x + ", " + y + ")");
                }
                tab[(int) y][(int) x] = i;
            }
            color++;
            System.out.println();
            i++;
        }
        printArray(tab);
    }

    private static void printArray(int[][] array) {

        for (int y = 0; y < array[0].length; y++) {
            System.out.print(y + ".  ");
            for (int x = 0; x < array.length; x++) {
                if (array[x][y] == 2) {
                    System.out.print(ANSI_GREEN + array[x][y] + ANSI_RESET);
                } else {
                    System.out.print(array[x][y]);
                }
            }
            System.out.print(" \n");
        }
    }

}
