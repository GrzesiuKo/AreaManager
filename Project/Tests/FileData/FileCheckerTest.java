package FileData;

import Exceptions.IncorrectDefinitionUnknownTypeException;
import Exceptions.IncorrectLineException;
import Exceptions.IncorrectObjectLineException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileCheckerTest {

    private FileChecker fileChecker;

    @BeforeEach
    public void beforeEach() {
        fileChecker = new FileChecker();
    }

    @Test
    void checkFileUndefinedUserObject() {
        //Given
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "UndefinedUserObject.txt");
        //When
        assertThrows(IncorrectObjectLineException.class, () -> fileChecker.checkFile(file));
    }

    @Test
    void checkFileMismatchOfUserTypes() {
        //Given
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "MismatchOfUserTypes.txt");
        //When
        assertThrows(IncorrectObjectLineException.class, () -> fileChecker.checkFile(file));
    }

    @Test
    void checkFileLackOfSection() {
        //Given
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "LackOfSection.txt");
        //When
        assertThrows(Exception.class, () -> fileChecker.checkFile(file));
    }

    @Test
    void checkObjectDefinitionLineGoodLine() throws IncorrectDefinitionUnknownTypeException, IncorrectLineException {
        //Given
        String line = "1. Szkoła X double y double Nazwa StRIng";
        //When
        assertTrue(fileChecker.checkObjectDefinitionLine(line));
    }

    @Test
    void checkObjectDefinitionLineWrongType() {
        //Given
        String line = "1. Szkoła X float y double Nazwa Object";
        //When
        assertThrows(IncorrectDefinitionUnknownTypeException.class, () -> fileChecker.checkObjectDefinitionLine(line));
    }

    @Test
    void readObjectDefinitionLineDoesntMatchPattern() {
        //Given
        String line = "Cztery piki skurczybyki";
        //When
        assertThrows(IncorrectLineException.class, () -> fileChecker.checkObjectDefinitionLine(line));
    }

    @Test
    void checkObjectLineGoodLine() throws IncorrectObjectLineException {
        //Given
        String line = "1. Szkoła \"Reytan\" 30 32";
        Map<String, LinkedList<Integer>> definitions = new HashMap<>();
        LinkedList<Integer> order = new LinkedList<>();
        order.add(FileChecker.STRING);
        order.add(FileChecker.X);
        order.add(FileChecker.Y);
        definitions.put("Szkoła", order);
        fileChecker.setDefinitions(definitions);
        //When
        assertTrue(fileChecker.checkObjectLine(line));
    }

    @Test
    void checkObjectLineWrongArgumentOrder() {
        //Given
        String line = "1. Szkoła \"Reytan\" 30 32";
        Map<String, LinkedList<Integer>> definitions = new HashMap<>();
        LinkedList<Integer> order = new LinkedList<>();
        order.add(FileChecker.X);
        order.add(FileChecker.Y);
        order.add(FileChecker.STRING);
        definitions.put("Szkoła", order);
        fileChecker.setDefinitions(definitions);
        //When
        assertThrows(IncorrectObjectLineException.class, () -> fileChecker.checkObjectLine(line));
    }

    @Test
    void checkObjectLineArgumentsDontMatchPatternAtAll() {
        //Given
        String line = "1. Szkoła Hej ha kolejke nalej!";
        Map<String, LinkedList<Integer>> definitions = new HashMap<>();
        LinkedList<Integer> order = new LinkedList<>();
        order.add(FileChecker.X);
        order.add(FileChecker.Y);
        order.add(FileChecker.STRING);
        definitions.put("Szkoła", order);
        fileChecker.setDefinitions(definitions);
        //When
        assertThrows(IncorrectObjectLineException.class, () -> fileChecker.checkObjectLine(line));
    }

    @Test
    void checkObjectLineNoSuchDefinition() {
        //Given
        String line = "1. Uniwersytet UW <<<< PW";
        Map<String, LinkedList<Integer>> definitions = new HashMap<>();
        LinkedList<Integer> order = new LinkedList<>();
        order.add(FileChecker.X);
        order.add(FileChecker.Y);
        order.add(FileChecker.STRING);
        definitions.put("Szkoła", order);
        fileChecker.setDefinitions(definitions);
        //When
        assertThrows(IncorrectObjectLineException.class, () -> fileChecker.checkObjectLine(line));
    }

    @Test
    void checkObjectLineNull() throws IncorrectObjectLineException {
        //Given
        String line = null;
        //When
        assertFalse(fileChecker.checkObjectLine(line));
    }

    @Test
    void checkObjectDefinitionLineNull() throws IncorrectDefinitionUnknownTypeException, IncorrectLineException {
        //Given
        String line = null;
        //When
        assertFalse(fileChecker.checkObjectDefinitionLine(line));
    }

    @Test
    void checkFileWithDotsAndComas() throws IncorrectObjectLineException, IncorrectDefinitionUnknownTypeException, IncorrectLineException {
        //Given
        FileChecker fileChecker = new FileChecker();
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "DotsAndComas.txt");
        //When
        fileChecker.checkFile(file);
    }

}