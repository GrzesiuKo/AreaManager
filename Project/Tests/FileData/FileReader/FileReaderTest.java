package FileData;

import Common.Point;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Executable;
import java.lang.reflect.TypeVariable;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {

    @Test
    void readFileNull() {
        //Given
        FileReader fileReader = new FileReader();
        File file = null;
        //Then
        assertThrows(NullPointerException.class, ()-> fileReader.readFile(file));
    }

    @Test
    void readFileGoodFile() {
        //Given
        FileReader fileReader = new FileReader();
        File file = new File("Tests/TestFiles/dobryPlik.txt");
        //When
        fileReader.readFile(file);

        //Then
//        System.out.println("Contour points:");
//        for(Point point : fileReader.getContourPoints()) {
//            System.out.println(point.getX()+" "+point.getY());
//        }
//        System.out.println("Key points:");
//        for(Point point : fileReader.getKeyPoints()) {
//            System.out.println(point.getX()+" "+point.getY());
//        }
//        Map<String, Integer> mapa = fileReader.getDefinitions();
//        System.out.println("Szkoła: "+mapa.get("SZKOŁA"));
//        System.out.println("DOM: "+mapa.get("DOM"));
//        System.out.println("NIEDŹWIEDŹ: "+mapa.get("NIEDŹWIEDŹ"));
//
//        System.out.println("ŁOŚ: "+mapa.get("ŁOŚ"));

    }

}