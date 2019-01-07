package FileData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileChecker {
    public final static int UNKNOWN = -100;
    public final static int STRING = 0;
    public final static int INT = 1;
    public final static int DOUBLE = 2;
    public final static int NOT_GIVEN = 3;
    private static int currentFilePart;
    private List<Integer> errorLines;
    private Map<String, Integer> definitions;

    public FileChecker(){
        definitions = new HashMap<>();
    }

    public boolean checkFile(File file) {
        boolean isFailFound;
        Scanner scanner;
        String currentLine;
        int currentLineNumber;
        int currentFilePart;

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
        return !isFailFound;
    }

    private boolean checkLine(String line) {
        int hashCharIndex;

        hashCharIndex = line.indexOf("#");

        if (hashCharIndex == 0 || hashCharIndex == 1) {
            System.out.println("Kolejna sekcja:");
            currentFilePart++;
            return true;

        } else if (FileNavigation.isContourPointsSection(currentFilePart)) {

            System.out.println("Sprawdzam linie CONTOUR POINT:");
            return checkContourPointLine(line);

        } else if (FileNavigation.isKeyPointsSection(currentFilePart)) {
            System.out.println("Sprawdzam linie KEY POINT:");
            return checkKeyPointLine(line);

        } else if (FileNavigation.isObjectsDefinitionSection(currentFilePart)) {

            System.out.println("Sprawdzam linie OBJECT DEFINITION:");
            if (checkObjectDefinitionLine(line)) {
                System.out.println("    Dodaje definicje:");
                readObjectDefinitionLine(line, definitions);
                return true;
            }
            return false;

        } else if (FileNavigation.isObjectsSection(currentFilePart)) {
            System.out.println("Sprawdzam linie OBJECT LINE:");
            return checkObjectLine(line);

        } else {
            return false;
        }
    }


    private boolean checkContourPointLine(String line) {
        return line.matches(".*\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s[0-9]{1,2}((([,.])[0-9])|[,.])?(\\s)*");
    }

    private boolean checkKeyPointLine(String line) {
        return line.matches(".*\\s[0-9]{1,2}((([,.])[0-9])|[,.])?\\s[0-9]{1,2}((([,.])[0-9])|[,.])?(\\d|\\w|\\s)*");
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

    private int checkTypeByDefinedName(String objectLine) {
        Scanner scanner;
        String key;

        scanner = new Scanner(objectLine);
        scanner.useDelimiter(" ");

        scanner.next();
        key = scanner.next();

        return definitions.get(key);
    }

    public static void readObjectDefinitionLine(String line, Map<String, Integer> definitions) {
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
            typeId = recognizeTypeByVariableName(typeName);
        } else {
            typeId = NOT_GIVEN;
        }

        // System.out.println("Dodaje do mapy: " + name);
        definitions.put(name, typeId);
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

}
