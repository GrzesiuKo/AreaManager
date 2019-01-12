package GrzesTesty;

import Exceptions.IncorrectDefinitionUnknownTypeException;
import Exceptions.IncorrectLineException;
import Exceptions.IncorrectObjectLineException;
import FileData.FileChecker;

import java.io.File;

public class FileCheckerTest {

    public static void main(String[] args) throws IncorrectObjectLineException, IncorrectDefinitionUnknownTypeException, IncorrectLineException {
        FileChecker fileChecker = new FileChecker();
        File file = new File("src\\GrzesTesty\\FileToBeChecked.txt");

        System.out.println("Plik jest: " + fileChecker.checkFile(file));
    }
}
