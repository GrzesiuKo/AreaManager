package FileData;

import Common.KeyPoint;
import Common.Point;
import Statistics.UserObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class FileReader {
    private static int currentFilePart;
    private List<KeyPoint> keyPoints;
    private List<Point> contourPoints;
    private Map<String, Integer> definitions;
    private List<Point> objectPoints;

    public void readFile(File file) {
        Scanner scanner;
        String currentLine;

        currentFilePart = 0;
        initializeFileReader();

        try {
            scanner = new Scanner(file, "UTF-8");
        } catch (FileNotFoundException e) {
            return;
        }

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            readLine(currentLine);
        }
    }

    private void readLine(String line) {
        int hashCharIndex;

        hashCharIndex = line.indexOf("#");

        if (hashCharIndex == 0 || hashCharIndex == 1) {
            currentFilePart++;

        } else if (FileNavigation.isContourPointsSection(currentFilePart)) {
            readContourPointLine(line);

        } else if (FileNavigation.isKeyPointsSection(currentFilePart)) {
            readKeyPointLine(line, keyPoints);

        } else if (FileNavigation.isObjectsDefinitionSection(currentFilePart)) {
            FileChecker.readObjectDefinitionLine(line, definitions);

        } else if (FileNavigation.isObjectsSection(currentFilePart)) {
            readObjectLine(line);

        }
    }


    private void readContourPointLine(String line) {
        readPointLine(line, contourPoints);
    }

    private void readKeyPointLine(String line, List<KeyPoint> list) {
        Scanner scanner;
        KeyPoint point;
        double x;
        double y;

        try {
            scanner = new Scanner(line);
        } catch (NullPointerException e) {
            return;
        }

        scanner.next();
        x = getDoubleFromString(scanner.next());
        y = getDoubleFromString(scanner.next());

        point = new KeyPoint(x, y);

        list.add(point);
    }



    private void readObjectLine(String line) {
        Scanner scanner;
        String name;
        double x;
        double y;
        Point point;
        Integer type;

        try {
            scanner = new Scanner(line);
        } catch (NullPointerException e) {
            return;
        }
        scanner.next();
        name = scanner.next();
        x = getDoubleFromString(scanner.next());
        y = getDoubleFromString(scanner.next());

        type = definitions.get(name);

        point = new Point(x, y);

        addObject(point, name, type, scanner);
    }

    private void addObject(Point point, String name, int type, Scanner scanner) {
        if (type == FileChecker.STRING) {
            objectPoints.add(point);
            scanner.useDelimiter("$");
            UserObject.addObject(point, name, scanner.next());
        } else if (type == FileChecker.DOUBLE) {
            objectPoints.add(point);
            // UserObject.addObject(point, name, getDoubleFromString(scanner.next())); //czeka na funkcje od Arkadiusza
        } else if (type == FileChecker.INT) {
            objectPoints.add(point);
            UserObject.addObject(point, name, scanner.nextInt());
        } else if (type == FileChecker.NOT_GIVEN) {
            objectPoints.add(point);
            UserObject.addObject(point, name);
        }
    }

    private void readPointLine(String line, List<Point> list) {
        Scanner scanner;
        Point point;
        double x;
        double y;

        try {
            scanner = new Scanner(line);
        } catch (NullPointerException e) {
            return;
        }

        scanner.next();
        x = getDoubleFromString(scanner.next());
        y = getDoubleFromString(scanner.next());

        point = new Point(x, y);

        list.add(point);
    }


    private void initializeFileReader() {
        keyPoints = new LinkedList<>();
        contourPoints = new LinkedList<>();
        definitions = new HashMap<>();
        objectPoints = new LinkedList<>();
    }

    private double getDoubleFromString(String text){
        Scanner scanner;
        scanner = new Scanner(text);
        text = scanner.next(Pattern.compile("[0-9]*([,.][0-9]*)?"));
        text = text.replace(",",".");

        return Double.parseDouble(text);
    }

    public List<KeyPoint> getKeyPoints() {
        return keyPoints;
    }

    public List<Point> getContourPoints() {
        return contourPoints;
    }

    public List<Point> getObjectPoints() {
        return objectPoints;
    }
}
