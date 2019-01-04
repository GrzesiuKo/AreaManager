package FileData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class FileChecker {
    private List<Integer> errorLines;


    public boolean checkFile(File file) {
        boolean result;
        Scanner scanner;
        String currentLine;
        int currentLineNumber;
        int currentFilePart;

        result = true;
        currentLineNumber = 0;
        currentFilePart = 0;

        try {
            scanner = new Scanner(file, "UTF-8");
        } catch (FileNotFoundException e) {
            return false;
        }


        while (result != false && scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentLineNumber++;
            result = checkLine(currentLine, currentLineNumber, currentFilePart);
        }


        return result;
    }

    private boolean checkLine(String line, int lineNumber, int currentFilePart) {
        int hashCharIndex;

        hashCharIndex = line.indexOf("#");

        if (hashCharIndex == 0 || hashCharIndex == 1) {
            currentFilePart++;
            return true;

        } else if (isContourPointsSection(lineNumber)) {
            return checkContourPointLine(line);

        } else if (isKeyPointsSection(lineNumber)) {
            return checkKeyPointLine(line);

        } else if (isObjectsDefinitionSection(lineNumber)) {
            return checkObjectDefinitionLine(line);

        } else if (isObjectsSection(lineNumber)) {
            return checkObjectLine(line);

        } else {
            return false;
        }
    }

    private boolean isContourPointsSection(int currentFilePart) {
        return currentFilePart == 1;
    }

    private boolean isKeyPointsSection(int currentFilePart) {
        return currentFilePart == 2;
    }

    private boolean isObjectsDefinitionSection(int currentFilePart) {
        return currentFilePart == 3;
    }

    private boolean isObjectsSection(int currentFilePart) {
        return currentFilePart == 4;
    }

    private boolean checkContourPointLine(String line) {
        return true;
    }

    private boolean checkKeyPointLine(String line) {
        return true;
    }

    private boolean checkObjectDefinitionLine(String line) {
        return true;
    }

    private boolean checkObjectLine(String line) {
        return true;
    }


}
