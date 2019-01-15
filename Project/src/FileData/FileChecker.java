package FileData;

import Exceptions.IncorrectDefinitionUnknownTypeException;
import Exceptions.IncorrectLineException;
import Exceptions.IncorrectObjectLineException;
import Exceptions.StringArgumentException;

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
    private static int currentLineNumber;
    private int errorLineNumber;
    private String errorLine;
    private static Map<String, LinkedList<Integer>> definitions;
    private static Map<String, String> definitionsLines;

    public FileChecker() {
        definitions = new HashMap<>();
        definitionsLines = new HashMap<>();
    }


    public boolean checkFile(File file) throws IncorrectObjectLineException, IncorrectLineException, IncorrectDefinitionUnknownTypeException {
        boolean isFailFound;
        Scanner scanner;
        String currentLine;

        isFailFound = false;
        currentLine = null;
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
            errorLineNumber = currentLineNumber;
            errorLine = currentLine;
        }

        return !isFailFound;
    }

    private boolean checkLine(String line) throws IncorrectObjectLineException, IncorrectLineException, IncorrectDefinitionUnknownTypeException {
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

    private boolean checkContourPointLine(String line) throws IncorrectLineException {
        if(line.matches(".*\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s[0-9]{1,2}((([,.])[0-9])|[,.])?(\\s)*")){
            return true;
        }else{
            throw new IncorrectLineException(currentLineNumber, line);
        }
    }

    private boolean checkKeyPointLine(String line) throws IncorrectLineException {
        if(line.matches(".*\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s[0-9]{1,2}((([,.])[0-9])|[,.])?(\\s.*)*")){
            return true;
        }else{
            throw new IncorrectLineException(currentLineNumber, line);
        }
    }

    public boolean checkObjectDefinitionLine(String line) throws IncorrectDefinitionUnknownTypeException, IncorrectLineException {
        Scanner scanner;
        String typeName;

        if (line == null){
            return false;
        }else if (!line.matches("([^\\s]+\\s){5}[^\\s]+(\\s([^\\s]+\\s[^\\s]+\\s*)|\\s*)")) {
            throw new IncorrectLineException(currentLineNumber, line);
        }

        scanner = new Scanner(line);
        scanner.next();
        scanner.next();

        while (scanner.hasNext()) {
            scanner.next();
            typeName = scanner.next();
            if (recognizeTypeByVariableName(typeName) == UNKNOWN) {
                throw new IncorrectDefinitionUnknownTypeException(currentLineNumber, line, typeName);
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

        definitionsLines.put(objectName, line);

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

    public boolean checkObjectLine(String line) throws IncorrectObjectLineException {
        LinkedList<Integer> order;
        Scanner scanner;
        boolean isArgumentValid;
        String name;
        String text;

        if (line == null){
            return false;
        }

        scanner = new Scanner(line);
        isArgumentValid = true;
        scanner.next();
        name = scanner.next();
        try{
            order = new LinkedList<>(definitions.get(name));
        }catch(NullPointerException e){
            throw new IncorrectObjectLineException(currentLineNumber, line, null);
        }

        while (!order.isEmpty() && isArgumentValid) {
            if (scanner.hasNext()) {
                text = scanner.next();
                try {
                    isArgumentValid = checkArgument(order.removeFirst(), text);
                } catch (StringArgumentException e) {
                    isArgumentValid = true;
                    scanner = skipToEndOfStringArgument(scanner);
                }
            } else {
                    isArgumentValid = false;
            }
        }
        if (isArgumentValid){
            return true;
        }else{
            throw new IncorrectObjectLineException(currentLineNumber, line, definitionsLines.get(name));
        }
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
                if (argument.matches("\"\"")) {
                    return false;
                }else if (argument.matches("\"[^\\s]+\"")){
                    return true;
                }else if (argument.matches("(\")|(\".+)")) {
                    throw new StringArgumentException();
                }
            default:
                return false;
        }
    }

    private Scanner skipToEndOfStringArgument(Scanner scanner){
        if(scanner == null){
            return null;
        }
        scanner.useDelimiter("\"");
        scanner.next();
        scanner.useDelimiter(" ");
        scanner.next();
        return scanner;
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

    public int getErrorLineNumber() {
        return errorLineNumber;
    }

    public String getErrorLine() {
        return errorLine;
    }

    public Map<String, LinkedList<Integer>> getDefinitions() {
        return definitions;
    }

    public static void setDefinitions(Map<String, LinkedList<Integer>> definitions) {
        FileChecker.definitions = definitions;
    }
}
