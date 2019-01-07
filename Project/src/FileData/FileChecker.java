package FileData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileChecker {
    private List<Integer> errorLines;
    private Map<String, Integer> definitions;


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

        } else if (FileNavigation.isContourPointsSection(currentFilePart)) {
            return checkContourPointLine(line);

        } else if (FileNavigation.isKeyPointsSection(currentFilePart)) {
            if (checkKeyPointLine(line)) {
                addDefinition(line);
                return true;
            }
            return false;

        } else if (FileNavigation.isObjectsDefinitionSection(currentFilePart)) {
            return checkObjectDefinitionLine(line);

        } else if (FileNavigation.isObjectsSection(currentFilePart)) {
            return checkObjectLine(line);

        } else {
            return false;
        }
    }


    private boolean checkContourPointLine(String line) {
        return line.matches(".*\\s[0-9]{1,2}(([,.])[0-9])?\\s[0-9]{1,2}((([,.])[0-9])|[,.])?(\\s)*");
    }

    private boolean checkKeyPointLine(String line) {
        return line.matches(".*\\s[0-9]{1,2}(([,.])[0-9])?\\s[0-9]{1,2}((([,.])[0-9])|[,.])?(\\d\\w\\s)*");
    }

    private boolean checkObjectDefinitionLine(String line) {
        return line.matches(".*\\s.{1,40}\\s(?i)(string|int|double)(?-i)(\\s)*");
    }

    private boolean checkObjectLine(String line) {
        int objectType;
        objectType = checkType(line);
        switch (objectType) {
            case FileReader.STRING:
                return line.matches("");
            case FileReader.INT:
                return line.matches("");
            case FileReader.DOUBLE:
                return line.matches("");
            case FileReader.UNKNOWN:
                return line.matches("");
            default:
                return false;
        }
    }

    private void addDefinition(String line){

    }

    private int checkType(String line){
        return 0;
    }

}
