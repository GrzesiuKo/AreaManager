package GrzesTesty;

import Common.KeyPoint;
import Common.Point;
import Diagram.Diagram;
import Diagram.IncorrectFileException;
import Diagram.InvalidContourException;
import Diagram.*;

import java.io.File;
import java.util.List;

public class RemakingVoronoiTest {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static void main(String[] args) throws IncorrectFileException, InvalidContourException {
        Diagram diagram;
        File file;

        file = new File("src\\GrzesTesty\\file.txt");

        diagram = new Diagram(file, 10);

        diagram.getKeyPoints().add(new KeyPoint(0, 0));

        diagram.remakeVoronoi(diagram.getKeyPoints());

        System.out.println("Area Fields: ");
        printArray(diagram.getAreaFields());

        printKeyPoints(Diagram.SIZE, diagram.getKeyPoints(), 10);

    }

    private static void printArray(AreaField[][] array) {
        for (int y = 0; y < array[0].length; y++) {
            for (int x = 0; x < array.length; x++) {
                System.out.print(array[x][y].getNearestKeyPoint().string() + " ");
            }
            System.out.print(" \n");
        }
    }

    private static int[][] printKeyPoints(int size, List<KeyPoint> keyPoints, int precision){
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
        return tab;
    }


}