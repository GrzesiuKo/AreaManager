package FileData;

import Common.Point;
import Statistics.UserObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileReader {
    private static int STRING = 0;
    private static int INT = 1;
    private static int DOUBLE = 2;
    private static int UNKNOWN = -123456789;

    private List<Point> keyPoints;
    private List<UserObject> objects;
    private List<Point> contourPoints;
    private Map<String, Integer> definitions;
    private static int currentFilePart;

    public void readFile(File file) {
        Scanner scanner;
        String currentLine;
        int currentLineNumber;

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
            readLine(currentLine, currentLineNumber);
        }
    }

    private void readLine(String line, int lineNumber) {
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
        readPointLine(line);
    }

    private void readKeyPointLine(String line) {
        readPointLine(line);
    }

    private void readObjectDefinitionLine(String line) {
    }

    private void readObjectLine(String line) {
    }

    private void readPointLine(String line){
        Scanner scanner;
        Point point;
        double x;
        double y;

        try{
            scanner = new Scanner(line);
        }catch (NullPointerException e){
            return;
        }

        scanner.nextDouble();
        x = scanner.nextDouble();
        y = scanner.nextDouble();

        point = new Point(x, y);

        keyPoints.add(point);
    }
}
