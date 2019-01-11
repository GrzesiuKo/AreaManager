package FileData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

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
    private static Map<String, LinkedList<Integer>> argumentsOrders;

    public FileChecker() {
        argumentsOrders = new HashMap<>();
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

        if (isFailFound) {
            errorLine = currentLineNumber;
        }

        return !isFailFound;
    }

    private boolean checkLine(String line) {
        int hashCharIndex;

        hashCharIndex = line.indexOf("#");

        if (hashCharIndex == 0 || hashCharIndex == 1) {
            System.out.println("Kolejna sekcja: ");
            currentFilePart++;
            return true;

        } else if (FileNavigation.isContourPointsSection(currentFilePart)) {
            System.out.println("Linia konturu: ");
            return checkContourPointLine(line);

        } else if (FileNavigation.isKeyPointsSection(currentFilePart)) {
            System.out.println("Linia key Point: ");
            return checkKeyPointLine(line);

        } else if (FileNavigation.isObjectsDefinitionSection(currentFilePart)) {
            System.out.println("Deinicja obiektu linia: ");
            if (checkObjectDefinitionLine(line)) {
             //   readObjectDefinitionLine(line, definitions);
                return true;
            }
            return false;

        } else if (FileNavigation.isObjectsSection(currentFilePart)) {
            System.out.println("Linia obiektu: ");
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

    public boolean checkObjectDefinitionLine(String line) {
        Scanner scanner;
        String text;
        int part;
        String objectName;
        LinkedList<Integer> order;

        if (!line.matches("([^\\s]+\\s){5}[^\\s]+(\\s([^\\s]+\\s[^\\s]+\\s*)|\\s*)")) {
            System.out.println("Linia cała słaba:");
            System.out.println(line);
            return false;
        }

        try {
            scanner = new Scanner(line);
        } catch (NullPointerException e) {
            return false;
        }

        scanner.next();
        objectName = scanner.next();
        order = new LinkedList<>();
        text = scanner.next();

        for (int i = 0; i < MAX_NUMBER_OF_ARGUMENTS; i++) {
            System.out.println(text);
            if (isCoordinateDefinition(text)) {
                System.out.println("    CoordinateDefinition");
                part = recognizeCoordinate(text);
                System.out.println("        recognized coordinate: "+part);
                order.add(part);
                if (scanner.hasNext()) {
                    scanner.next();
                }
            } else if (scanner.hasNext()) {
                System.out.println("    UserVaraiable");
                order.add(handleUserVariable(scanner.next()));
            }
            if (scanner.hasNext()) {
                text = scanner.next();
            }
        }

        if (order.contains(X) && order.contains(Y)) {
            argumentsOrders.put(objectName, order);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkObjectLine(String line) {
        LinkedList<Integer> order;
        Scanner scanner;
        boolean isArgumentValid;
        String name;

        if (!line.matches("[^\\s]+(\\s[^\\s]+){3}(\\s[^\\s]+)?\\s*")) {
            return false;
        }
        scanner = new Scanner(line);
        isArgumentValid = true;
        scanner.next();
        name = scanner.next();
        order = argumentsOrders.get(name);

        System.out.println("Nazwa obiektu:"+name+" a jej lista: "+order);
        while (!order.isEmpty() && isArgumentValid) {
            System.out.println("Sprawdzam argument");
            isArgumentValid = checkArgument(order.removeFirst(), scanner.next());
        }
        return isArgumentValid;
    }

    private boolean checkArgument(int type, String argument) {
        if (type == X || type == Y) {
            System.out.println("Coordinate: "+argument);
            return argument.matches("[0-9]{1,2}((([,.])[0-9])|[,.])?");
        } else {
            System.out.println("Variable: "+argument);
            return checkUserVariable(type, argument);
        }
    }

    private boolean checkUserVariable(int type, String argument) {
        switch (type) {
            case DOUBLE:
                return argument.matches("[0-9]{1,2}((([,.])[0-9])|[,.])?");
            case INT:
                return argument.matches("\\d+");
            case STRING:
                return argument.matches("[^\\s]+");
            default:
                return false;
        }
    }

    public static void readObjectDefinitionLine(String line, Map<String, Integer> definitions) {
        //TODO
    }

    private static boolean isCoordinateDefinition(String text) {
        return text.matches("(?i)x(?-i)") || text.matches("(?i)y(?-i)");
    }

    private static int recognizeCoordinate(String name) {
        if (name.matches("(?i)x(?-i)")) {
            return X;
        } else if (name.matches("(?i)y(?-i)")) {
            return Y;
        } else {
            return UNKNOWN;
        }
    }

    private static int handleUserVariable(String type) {
        int typeNumber;
        typeNumber = recognizeTypeByVariableName(type);
        return typeNumber;
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
            result = 0;//definitions.get(key);
        } catch (NullPointerException e) {
            result = UNKNOWN;
        }
        return result;
    }

    public int getErrorLine() {
        return errorLine;
    }

    public static Map<String, LinkedList<Integer>> getArgumentsOrders() {
        return argumentsOrders;
    }
}
