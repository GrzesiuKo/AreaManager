package GrzesTesty;

import Common.Point;
import Diagram.Diagram;
import Exceptions.IncorrectDefinitionUnknownTypeException;
import Exceptions.IncorrectLineException;
import Exceptions.IncorrectObjectLineException;
import Exceptions.InvalidContourException;
import Diagram.AreaField;

import java.io.File;
import java.util.List;


public class DiagramTest {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static void main(String[] args) throws IncorrectLineException, InvalidContourException, IncorrectObjectLineException, IncorrectDefinitionUnknownTypeException {
        Diagram diagram;
        File file;

        file = new File("src\\GrzesTesty\\file.txt");

        diagram = new Diagram();
        diagram.readFile(file);

        System.out.println("Contour Points: ");
        printList(diagram.getContour().getContourPoints());

        System.out.println("Key Points: ");
        printList(diagram.getKeyPoints());

        //System.out.println("Area with objects (true/false): ");
        //printArray(diagram.getArea());

        System.out.println("Area Fields: ");
        printArray(diagram.getAreaFields());

    }

    private static void printArray(boolean[][] array) {
        for (int y = 0; y < array[0].length; y++) {
            for (int x = 0; x < array.length; x++) {
                if (array[x][y]) {
                    System.out.print(ANSI_GREEN + array[x][y] + ANSI_RESET + " ");
                } else {
                    System.out.print(array[x][y] + " ");
                }
            }
            System.out.print(" \n");
        }
    }

    private static void printList(List<? extends Point> list) {
        for (Point p : list) {
            System.out.println("x = " + p.getX() + " y = " + p.getY());
        }
    }

    private static void printArray(AreaField[][] array) {
        for (int y = 0; y < array[0].length; y++) {
            for (int x = 0; x < array.length; x++) {
                    System.out.print(array[x][y].getNearestKeyPoint().string() + " ");
            }
            System.out.print(" \n");
        }
    }

}
