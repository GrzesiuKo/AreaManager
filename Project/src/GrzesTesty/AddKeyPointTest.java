package GrzesTesty;

import Common.KeyPoint;
import Diagram.Diagram;
import Diagram.IncorrectFileException;
import Diagram.InvalidContourException;
import Diagram.AreaField;

import java.io.File;

public class AddKeyPointTest {
    public static void main(String[] args) throws IncorrectFileException, InvalidContourException {
        Diagram diagram;
        File file;

        file = new File("src\\GrzesTesty\\file.txt");

        diagram = new Diagram(file);

        diagram.addKeyPoint(new KeyPoint(0.1,0.4));

        System.out.println("Area Fields: ");
        printArray(diagram.getAreaFields());

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
