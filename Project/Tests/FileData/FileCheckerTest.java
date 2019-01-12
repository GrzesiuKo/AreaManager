package FileData;

import Exceptions.IncorrectDefinitionUnknownTypeException;
import Exceptions.IncorrectLineException;
import Exceptions.IncorrectObjectLineException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        File file = new File("Tests"+ File.separator + "TestFiles"+ File.separator + "MismatchOfUserTypes.txt");
        //When
        assertThrows(IncorrectObjectLineException.class, () -> fileChecker.checkFile(file));
    }

    @Test
    void checkFileLackOfSection() throws IncorrectObjectLineException, IncorrectDefinitionUnknownTypeException, IncorrectLineException {
        //Given
        File file = new File("Tests"+ File.separator + "TestFiles"+ File.separator + "LackOfSection.txt");
        //When
        assertThrows(Exception.class, () -> fileChecker.checkFile(file));
    }

    @Test
    void checkObjectDefinitionLine() {
    }

    @Test
    void readObjectDefinitionLine() {
    }

    @Test
    void checkObjectLine() {
    }
}