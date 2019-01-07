package FileData;

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
    void checkFileUndefinedUserObject() {
        //Given
        File file = new File("Tests\\TestFiles\\UndefinedUserObject.txt");
        boolean result;
        //When
        result = fileChecker.checkFile(file);
        //Then
        assertTrue(!result);
    }

    @Test
    void checkFileMismatchOfUserTypes() {
        //Given
        File file = new File("Tests\\TestFiles\\MismatchOfUserTypes.txt");
        boolean result;
        //When
        result = fileChecker.checkFile(file);
        //Then
        assertTrue(!result);
    }

    @Test
    void checkFileLackOfSection() {
        //Given
        File file = new File("Tests\\TestFiles\\LackOfSection.txt");
        boolean result;
        //When
        result = fileChecker.checkFile(file);
        //Then
        assertTrue(!result);
    }

}