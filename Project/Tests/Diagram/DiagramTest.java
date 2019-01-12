package Diagram;

import Exceptions.IncorrectDefinitionUnknownTypeException;
import Exceptions.IncorrectLineException;
import Exceptions.IncorrectObjectLineException;
import Exceptions.InvalidContourException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

}