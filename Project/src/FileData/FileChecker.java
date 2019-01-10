package FileData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileChecker {
    public final static int UNKNOWN = -100;
    public final static int STRING = 0;
    public final static int INT = 1;
    public final static int DOUBLE = 2;
    public final static int NOT_GIVEN = 3;
    public final static int X = -1;
    public final static int Y = -2;
    public final static int CHOICE = -3;
    public final static int MAX_NUMBER_OF_ARGUMENTS = 3;
    private static int currentFilePart;
    private int errorLine;
    private static Map<String, Integer> definitions;
    private static Map<String, LinkedList<Integer>> argumentsOrders;

    public FileChecker() {
        definitions = new HashMap<>();
    }


    public boolean checkFile(File file) {
        boolean isFailFound;
        Scanner scanner;
        String currentLine;
        int currentLineNumber;

        isFailFound = false;
        currentLineNumber = 0;

        try {
            scanner = new Scanner(file, "UTF-8");
        } catch (FileNotFoundException e) {
            return false;
        }

        while (!isFailFound && scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentLineNumber++;
            isFailFound = !checkLine(currentLine);
        }

        if (isFailFound){
            errorLine = currentLineNumber;
        }

        return !isFailFound;
    }

    private boolean checkLine(String line) {
        int hashCharIndex;

        hashCharIndex = line.indexOf("#");

        if (hashCharIndex == 0 || hashCharIndex == 1) {
            currentFilePart++;
            return true;

        } else if (FileNavigation.isContourPointsSection(currentFilePart)) {
            return checkContourPointLine(line);

        } else if (FileNavigation.isKeyPointsSection(currentFilePart)) {
            return checkKeyPointLine(line);

        } else if (FileNavigation.isObjectsDefinitionSection(currentFilePart)) {
            if (checkObjectDefinitionLine(line)) {
                readObjectDefinitionLine(line, definitions);
                return true;
            }
            return false;

        } else if (FileNavigation.isObjectsSection(currentFilePart)) {
            return checkObjectLine(line);

        } else {
            return false;
        }
    }

    private boolean checkContourPointLine(String line) {
        return line.matches(".*\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s[0-9]{1,2}((([,.])[0-9])|[,.])?(\\s)*");
    }

    private boolean checkKeyPointLine(String line) {
        return line.matches(".*\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s[0-9]{1,2}((([,.])[0-9])|[,.])?(\\s.*)*");
    }

    private boolean checkObjectDefinitionLine(String line) {
        return line.matches(".*\\s.{1,40}(\\s*|(\\s(?i)(string|int|double)(?-i)(\\s)*))");
    }

    private boolean checkObjectLine(String line) {
        int objectType;
        objectType = checkTypeByDefinedName(line);
        switch (objectType) {
            case STRING:
                return line.matches(".*\\s.{1,40}\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s[0-9]{1,2}((([,.])[0-9])|[,.])?(\\s.*)?");
            case INT:
                return line.matches(".*\\s.{1,40}\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s\\d*\\s*");
            case DOUBLE:
                return line.matches(".*\\s.{1,40}\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s[0-9]+((([,.])[0-9]*)|[,.])?\\s*");
            case NOT_GIVEN:
                return line.matches(".*\\s.{1,40}\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s*");
            default:
                return false;
        }
    }

    public static void readObjectDefinitionLine(String line, Map<String, Integer> definitions) {
        Scanner scanner;
        String name;
        String text;
        int order;

        try {
            scanner = new Scanner(line);
        } catch (NullPointerException e) {
            return;
        }
        scanner.next();
        name = scanner.next();

        addNameToDictionary(name);

        text = scanner.next();

        for (int i = 0; i< MAX_NUMBER_OF_ARGUMENTS; i++) {
            if (isCoordinateDefinition(text)) {
                order = recognizeCoordinate(text);
                argumentsOrders.get(name).add(order);
                scanner.next();
            }else{
                handleUserVariable(name, text, scanner.next());
            }
            text = scanner.next();
        }



    }

    private static void addNameToDictionary(String name){
        argumentsOrders.put(name, new LinkedList<>());
        definitions.put(name, UNKNOWN);
    }

    private static boolean isCoordinateDefinition(String text){
        return text.matches("(?i)x(?-i)") || text.matches("(?i)y(?-i)");
    }

    private static int recognizeCoordinate(String name){
        if (name.matches("(?i)x(?-i)")){
            return X;
        }else if (name.matches("(?i)y(?-i)")){
            return Y;
        }else{
            return UNKNOWN;
        }
    }

    private static void handleUserVariable(String objectName, String name, String type){

    }

    private static int recognizeTypeByVariableName(String name) {
        if (name == null) {
            return NOT_GIVEN;
        }

        if (name.matches("(?i)string(?-i)")) {
            return STRING;
        } else if (name.matches("(?i)double(?-i)")) {
            return DOUBLE;
        } else if (name.matches("(?i)int(?-i)")) {
            return INT;
        } else {
            return NOT_GIVEN;
        }
    }

    private int checkTypeByDefinedName(String objectLine) {
        Scanner scanner;
        String key;
        int result;

        scanner = new Scanner(objectLine);
        scanner.useDelimiter(" ");

        scanner.next();
        key = scanner.next();
        try {
            result = definitions.get(key);
        } catch (NullPointerException e) {
            result = UNKNOWN;
        }
        return result;
    }

    public int getErrorLine() {
        return errorLine;
    }
}
