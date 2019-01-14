package Diagram;

import Common.Contour;
import Common.Point;
import Exceptions.IncorrectDefinitionUnknownTypeException;
import Exceptions.IncorrectLineException;
import Exceptions.IncorrectObjectLineException;
import Exceptions.InvalidContourException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class DiagramTest {
    Diagram diagram;

    @BeforeEach
    public void beforeEach() {
        diagram = new Diagram();
    }

    @Test
    void readFileCorrectFile() throws InvalidContourException, IncorrectObjectLineException, IncorrectLineException, IncorrectDefinitionUnknownTypeException {
        //Given
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "GoodFile.txt");
        //When
        diagram.readFile(file);
    }

    @Test
    void readFileInvalidContour() {
        //Given
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "WrongContourFile.txt");
        //When
        assertThrows(InvalidContourException.class, () -> diagram.readFile(file));
    }

    @Test
    void readFileMisplacedArgumentsObjectLine() {
        //Given
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "MisplacedArgumentsObjectLine.txt");
        //When
        assertThrows(IncorrectObjectLineException.class, () -> diagram.readFile(file));
    }

    @Test
    void readFileUndefinedArgumentsObjectLine() {
        //Given
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "NotDefinedArgumentsObjectLine.txt");
        //When
        assertThrows(IncorrectObjectLineException.class, () -> diagram.readFile(file));
    }

    @Test
    void readFileStringNotInQuotationObjectLine() {
        //Given
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "StringNotInQuotation.txt");
        //When
        assertThrows(IncorrectObjectLineException.class, () -> diagram.readFile(file));
    }

    @Test
    void readFileIncorrectLine() {
        //Given
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "IncorrectLine.txt");
        //When
        assertThrows(IncorrectLineException.class, () -> diagram.readFile(file));
    }

    @Test
    void readFileIncorrectDefinitionType() {
        //Given
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "IncorrectDefinitionType.txt");
        //When
        assertThrows(IncorrectDefinitionUnknownTypeException.class, () -> diagram.readFile(file));
    }

    @Test
    void addContourPointCorrect() {
        //Given
        Contour contour;
        LinkedList<Point> points;
        Point newPoint;
        boolean result;

        points = new LinkedList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, 2));
        points.add(new Point(2, 2));
        points.add(new Point(2, 0));

        contour = new Contour(points);

        diagram = new Diagram();
        diagram.setContour(contour);

        newPoint = new Point(3, 1);
        //When
        result = diagram.addContourPoint(newPoint);
        //Then
        assertTrue(result);
    }

    @Test
    void addContourPointIncorrect() {
        //Given
        Contour contour;
        LinkedList<Point> points;
        Point newPoint;
        boolean result;

        points = new LinkedList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, 2));
        points.add(new Point(2, 2));
        points.add(new Point(2, 0));

        contour = new Contour(points);

        diagram = new Diagram();
        diagram.setContour(contour);

        newPoint = new Point(1, 1);
        //When
        result = diagram.addContourPoint(newPoint);
        //Then
        assertFalse(result);
    }

}