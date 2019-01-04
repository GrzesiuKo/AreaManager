package FileData;

import Common.Point;
import Statistics.UserObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    private List<Point> keyPoints;
    private List<UserObject> objects;
    private List<Point> contourPoints;

    public void readFile(File file) {
        Scanner scanner;
        String currentLine;
        int currentLineNumber;
        int currentFilePart;

        currentLineNumber = 0;
        currentFilePart = 0;

        try {
            scanner = new Scanner(file, "UTF-8");
        } catch (FileNotFoundException e) {
            return;
        }

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentLineNumber++;
            readLine(currentLine, currentLineNumber, currentFilePart);
        }
    }

    private void readLine(String line, int lineNumber, int currentFilePart) {
        int hashCharIndex;

        hashCharIndex = line.indexOf("#");

        if (hashCharIndex == 0 || hashCharIndex == 1) {
            currentFilePart++;

        } else if (FileNavigation.isContourPointsSection(lineNumber)) {
            readContourPointLine(line);

        } else if (FileNavigation.isKeyPointsSection(lineNumber)) {
            readKeyPointLine(line);

        } else if (FileNavigation.isObjectsDefinitionSection(lineNumber)) {
            readObjectDefinitionLine(line);

        } else if (FileNavigation.isObjectsSection(lineNumber)) {
            readObjectLine(line);

        }
    }


    private void readContourPointLine(String line) {
    }

    private void readKeyPointLine(String line) {
    }

    private void readObjectDefinitionLine(String line) {
    }

    private void readObjectLine(String line) {
    }

}
