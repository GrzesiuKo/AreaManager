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
    private Map<String, LinkedList<Integer>> definitions;
    private List<Point> objectPoints;

    public void readFile(File file, Map<String, LinkedList<Integer>> definitions) {
        Scanner scanner;
        String currentLine;

        currentFilePart = 0;
        this.definitions = definitions;
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
        }else if(line.matches("$")){
            //avoid empty line
        } else if (FileNavigation.isContourPointsSection(currentFilePart)) {
            readContourPointLine(line);

        } else if (FileNavigation.isKeyPointsSection(currentFilePart)) {
            readKeyPointLine(line, keyPoints);

        } else if (FileNavigation.isObjectsDefinitionSection(currentFilePart)) {

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
        String value, objectName;
        double x, y;
        UserArgument userArgument;
        LinkedList<Integer> order;
        int argumentId;

        scanner = new Scanner(line);

        scanner.next();
        objectName = scanner.next();
        order = new LinkedList<>(definitions.get(objectName));
        x=-1;
        y=-1;
        userArgument = null;

        while(!order.isEmpty()){

            argumentId = order.removeFirst();
            scanner.next();
            value = scanner.next();

            if (argumentId == FileChecker.X){
                x = handleCoordinate(value);
            }else if (argumentId == FileChecker.Y){
                y = handleCoordinate(value);
            }else if (argumentId == FileChecker.STRING) {
                userArgument = new UserArgument(argumentId, value);
            }else{
                userArgument = handleUserArgumentNoString(argumentId, value);
            }
        }
        addObject(objectName, new Point(x, y), userArgument);
    }

    private double handleCoordinate(String value){
        return getDoubleFromString(value);
    }

    private UserArgument handleUserArgumentNoString(int id, String value){
        UserArgument userArgument;
        double valueDouble;
        int valueInt;

        switch (id){
            case FileChecker.DOUBLE:
                valueDouble = getDoubleFromString(value);
                userArgument = new UserArgument(id,valueDouble);
                break;
            case FileChecker.INT:
                valueInt = Integer.parseInt(value);
                userArgument = new UserArgument(id, valueInt);
                break;
            default:
                return null;
        }
        return userArgument;
    }

    private void addObject(String name, Point point, UserArgument userArgument) {
        int type;

        if (userArgument == null){
            objectPoints.add(point);
            UserObject.addObject(point, name);
            return;
        }

        type = userArgument.getId();

        if (type == FileChecker.STRING) {
            objectPoints.add(point);
            UserObject.addObject(point, name, userArgument.getString());
        } else if (type == FileChecker.DOUBLE) {
            objectPoints.add(point);
            //UserObject.addObject(point, name, userArgument.getDouble()); //czeka na funkcje od Arkadiusza
        } else if (type == FileChecker.INT) {
            objectPoints.add(point);
            UserObject.addObject(point, name, userArgument.getInt());
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

    private double getDoubleFromString(String text) {
        Scanner scanner;
        scanner = new Scanner(text);
        text = scanner.next(Pattern.compile("[0-9]*([,.][0-9]*)?"));
        text = text.replace(",", ".");

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
