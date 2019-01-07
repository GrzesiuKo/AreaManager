package FileData;

import Common.KeyPoint;
import Common.Point;
import Statistics.UserObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {
    public final static int STRING = 0;
    public final static int INT = 1;
    public final static int DOUBLE = 2;
    public final static int UNKNOWN = 3;
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
            readObjectDefinitionLine(line);

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

        scanner.nextDouble();
        x = scanner.nextDouble();
        y = scanner.nextDouble();

        point = new KeyPoint(x, y);

        list.add(point);
    }

    private void readObjectDefinitionLine(String line) {
        Scanner scanner;
        String name;
        String typeName;
        int typeId;

        try {
            scanner = new Scanner(line);
        } catch (NullPointerException e) {
            return;
        }
        scanner.next();
        name = scanner.next();

        if (scanner.hasNext()) {
            typeName = scanner.next();
            typeId = recognizeType(typeName);
        } else {
            typeId = UNKNOWN;
        }

        System.out.println("Dodaje do mapy: " + name);
        definitions.put(name, typeId);
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
        x = scanner.nextDouble();
        y = scanner.nextDouble();

        type = definitions.get(name);

        point = new Point(x, y);

        addObject(point, name, type, scanner);
    }

    private void addObject(Point point, String name, int type, Scanner scanner) {
        if (type == STRING) {
            objectPoints.add(point);
            scanner.useDelimiter("$");
            UserObject.addObject(point, name, scanner.next());
        } else if (type == DOUBLE) {
            objectPoints.add(point);
            // UserObject.addObject(point, name, scanner.nextDouble()); czeka na funkcje od Arkadiusza
        } else if (type == INT) {
            objectPoints.add(point);
            UserObject.addObject(point, name, scanner.nextInt());
        } else if (type == UNKNOWN) {
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

        scanner.nextDouble();
        x = scanner.nextDouble();
        y = scanner.nextDouble();

        point = new Point(x, y);

        list.add(point);
    }

    private int recognizeType(String name) {
        if (name == null) {
            return UNKNOWN;
        }

        if (name.matches("string")) {
            return STRING;
        } else if (name.matches("double")) {
            return DOUBLE;
        } else if (name.matches("int")) {
            return INT;
        } else {
            return UNKNOWN;
        }
    }

    private void initializeFileReader() {
        keyPoints = new LinkedList<>();
        contourPoints = new LinkedList<>();
        definitions = new HashMap<>();
        objectPoints = new LinkedList<>();
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
