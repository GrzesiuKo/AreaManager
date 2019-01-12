package FileData;

import Exceptions.IncorrectDefinitionUnknownTypeException;
import Exceptions.IncorrectLineException;
import Exceptions.IncorrectObjectLineException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;


import static org.junit.jupiter.api.Assertions.*;

class FileCheckerTest {

    private FileChecker fileChecker;

    @BeforeEach
    public void beforeEach() {
        fileChecker = new FileChecker();
    }

    @Test
    void checkFileUndefinedUserObject() throws IncorrectObjectLineException, IncorrectDefinitionUnknownTypeException, IncorrectLineException {
        //Given
        File file = new File("Tests\\TestFiles\\UndefinedUserObject.txt");
        boolean result;
        //When
        result = fileChecker.checkFile(file);
        //Then
        assertTrue(!result);
    }

    @Test
    void checkFileMismatchOfUserTypes() throws IncorrectObjectLineException, IncorrectDefinitionUnknownTypeException, IncorrectLineException {
        //Given
        File file = new File("Tests\\TestFiles\\MismatchOfUserTypes.txt");
        boolean result;
        //When
        result = fileChecker.checkFile(file);
        //Then
        assertTrue(!result);
    }

    @Test
    void checkFileLackOfSection() throws IncorrectObjectLineException, IncorrectDefinitionUnknownTypeException, IncorrectLineException {
        //Given
        File file = new File("Tests\\TestFiles\\LackOfSection.txt");
        boolean result;
        //When
        result = fileChecker.checkFile(file);
        //Then
        assertTrue(!result);
    }

}