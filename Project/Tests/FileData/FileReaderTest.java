package FileData;

import Exceptions.IncorrectDefinitionUnknownTypeException;
import Exceptions.IncorrectLineException;
import Exceptions.IncorrectObjectLineException;
import org.junit.jupiter.api.Test;

import java.io.File;

class FileReaderTest {

    @Test
    void readFileNull() {
        //Given
        FileReader fileReader = new FileReader();
        File file = null;
        //Then
        fileReader.readFile(file, null);
    }

    @Test
    void readFileGoodFile() throws IncorrectObjectLineException, IncorrectDefinitionUnknownTypeException, IncorrectLineException {
        //Given
        FileReader fileReader = new FileReader();
        FileChecker fileChecker = new FileChecker();
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "GoodFile.txt");
        //When
        fileChecker.checkFile(file);
        fileReader.readFile(file, fileChecker.getDefinitions());
    }

    @Test
    void readFileWithDotsAndComas() throws IncorrectObjectLineException, IncorrectDefinitionUnknownTypeException, IncorrectLineException {
        //Given
        FileReader fileReader = new FileReader();
        FileChecker fileChecker = new FileChecker();
        File file = new File("Tests" + File.separator + "TestFiles" + File.separator + "DotsAndComas.txt");
        //When
        fileChecker.checkFile(file);
        fileReader.readFile(file, fileChecker.getDefinitions());
    }
}