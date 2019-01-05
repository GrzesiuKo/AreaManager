package GrzesTesty;

import Common.Point;
import Diagram.Diagram;

import java.io.File;
import java.util.List;

public class DiagramTest {
    public static void main(String[] args) {
        Diagram diagram;
        File file;

        file = new File("GrzesTesty/dobryPlik.txt");
        
        diagram = new Diagram(file);

        System.out.println("Contour Points): ");
        printList(diagram.getContour().getContourPoints());

        System.out.println("Key Points): ");
        printList(diagram.getKeyPoints());

        System.out.println("Area with objects (true/false): ");
        printArray(diagram.getArea());

    }

    private static void printArray(boolean[][] array) {
        for (int y = 0; y < array[0].length; y++) {
            for (int x = 0; x < array.length; x++) {
                System.out.print(array[x][y] + " ");
            }
            System.out.print(" \n");
        }
    }

    private static void printList(List<? extends Point> list) {
        for(Point p: list){
            System.out.println("x = "+p.getX()+" y = "+p.getY());
        }
    }

}
