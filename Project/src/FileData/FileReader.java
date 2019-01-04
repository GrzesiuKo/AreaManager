package FileData;

import Common.Point;
import Statistics.UserObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {
    private final static int STRING = 0;
    private final static int INT = 1;
    private final static int DOUBLE = 2;
    private final static int UNKNOWN = 3;

    private List<Point> keyPoints;
    private List<Point> contourPoints;
    private Map<String, Integer> definitions;
    private static int currentFilePart;



    public void readFile(File file) {
        Scanner scanner;
        String currentLine;
        int currentLineNumber;

        currentLineNumber = 0;
        currentFilePart = 0;
        initializeFileReader();

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

        } else if (FileNavigation.isContourPointsSection(currentFilePart)) {
            readContourPointLine(line);

        } else if (FileNavigation.isKeyPointsSection(currentFilePart)) {
            readKeyPointLine(line);

        } else if (FileNavigation.isObjectsDefinitionSection(currentFilePart)) {
            readObjectDefinitionLine(line);

        } else if (FileNavigation.isObjectsSection(currentFilePart)) {
           // readObjectLine(line);

        }
    }


    private void readContourPointLine(String line) {
        readPointLine(line);
    }

    private void readKeyPointLine(String line) {
        readPointLine(line);
    }

    private void readObjectDefinitionLine(String line) {
        Scanner scanner;
        String name;
        String type;

        try{
            scanner = new Scanner(line);
        }catch (NullPointerException e){
            return;
        }
        name = scanner.next();
        type = scanner.next();

        definitions.put(name, recognizeType(type));
    }

    private void readObjectLine(String line) {
        Scanner scanner;
        String name;
        double x;
        double y;
        Point point;
        int type;

        try{
            scanner = new Scanner(line);
        }catch (NullPointerException e){
            return;
        }
        name = scanner.next();
        x = scanner.nextDouble();
        y = scanner.nextDouble();
        type = definitions.get(name);

        point = new Point(x, y);

        addObject(point, name, type, scanner);
    }

    private void addObject(Point point, String name, int type, Scanner scanner) {
        if(type == STRING){
            scanner.useDelimiter("$");
            UserObject.addObject(point, name, scanner.next());
        }else if (type == DOUBLE){
           // UserObject.addObject(point, name, scanner.nextDouble()); czeka na funkcje od Arkadiusza
        }else if (type == INT){
            UserObject.addObject(point, name, scanner.nextInt());
        }else if (type == UNKNOWN){
            UserObject.addObject(point, name);
        }
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

    private int recognizeType(String name){
        if (name==null){
            return UNKNOWN;
        }

        if (name.matches("string")){
            return STRING;
        }else if (name.matches("double")){
            return DOUBLE;
        }else if (name.matches("int")){
            return INT;
        }else{
            return UNKNOWN;
        }
    }

    private void initializeFileReader(){
        keyPoints = new LinkedList<>();
        contourPoints = new LinkedList<>();
        definitions = new HashMap<>();
    }

    public List<Point> getKeyPoints() {
        return keyPoints;
    }

    public List<Point> getContourPoints() {
        return contourPoints;
    }
}
