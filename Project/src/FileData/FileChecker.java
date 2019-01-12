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
    private static int currentFilePart;
    private int errorLine;
    private static Map<String, LinkedList<Integer>> definitions;

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
        currentFilePart = 0;

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
            currentFilePart++;
            return true;

        } else if (line.matches("$")) {
            return true;
        } else if (FileNavigation.isContourPointsSection(currentFilePart)) {
            return checkContourPointLine(line);

        } else if (FileNavigation.isKeyPointsSection(currentFilePart)) {
            return checkKeyPointLine(line);

        } else if (FileNavigation.isObjectsDefinitionSection(currentFilePart)) {
            if (checkObjectDefinitionLine(line)) {
                readObjectDefinitionLine(line);
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

    public boolean checkObjectDefinitionLine(String line) {
        Scanner scanner;
        String typeName;


        if (!line.matches("([^\\s]+\\s){5}[^\\s]+(\\s([^\\s]+\\s[^\\s]+\\s*)|\\s*)")) {
            return false;
        }

        scanner = new Scanner(line);
        scanner.next();
        scanner.next();

        while (scanner.hasNext()) {
            scanner.next();
            typeName = scanner.next();
            if (recognizeTypeByVariableName(typeName) == UNKNOWN) {
                return false;
            }
        }
        return true;
    }

    public static void readObjectDefinitionLine(String line) {
        Scanner scanner;
        String objectName, name, typeName;
        int part;
        LinkedList<Integer> order;


        scanner = new Scanner(line);
        order = new LinkedList<>();

        scanner.next();
        objectName = scanner.next();

        while (scanner.hasNext()) {
            name = scanner.next();
            typeName = scanner.next();
            if (isCoordinateDefinition(name)) {
                part = recognizeCoordinate(name);
                order.add(part);
            } else {
                order.add(handleUserVariable(typeName));
            }
        }

        definitions.put(objectName, order);

    }

    public boolean checkObjectLine(String line) {
        LinkedList<Integer> order;
        Scanner scanner;
        boolean isArgumentValid;
        String name;
        String text;

        scanner = new Scanner(line);
        isArgumentValid = true;
        scanner.next();
        name = scanner.next();
        order = new LinkedList<>(definitions.get(name));

        while (!order.isEmpty() && isArgumentValid) {
            if (scanner.hasNext()) {
                text = scanner.next();
                try {
                    isArgumentValid = checkArgument(order.removeFirst(), text);
                } catch (StringArgumentException e) {
                    isArgumentValid = true;
                    scanner.useDelimiter("\"");
                    scanner.next();
                    scanner.useDelimiter(" ");
                    scanner.next();
                }
            } else {
                return false;
            }
        }
        return isArgumentValid;
    }

    private boolean checkArgument(int type, String argument) throws StringArgumentException {
        if (type == X || type == Y) {
            return argument.matches("[0-9]{1,2}((([,.])[0-9])|[,.])?");
        } else {
            return checkUserVariable(type, argument);
        }
    }

    private boolean checkUserVariable(int type, String argument) throws StringArgumentException {
        switch (type) {
            case DOUBLE:
                return argument.matches("[0-9]{1,2}((([,.])[0-9])|[,.])?");
            case INT:
                return argument.matches("\\d+");
            case STRING:
                if (argument.matches("\"\"")){
                    return false;
                }else if (argument.matches("(\")|(\".+)")) {
                    throw new StringArgumentException();
                }
            default:
                return false;
        }
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
            return UNKNOWN;
        }
    }

    public int getErrorLine() {
        return errorLine;
    }

    public Map<String, LinkedList<Integer>> getDefinitions() {
        return definitions;
    }
}
