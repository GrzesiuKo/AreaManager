package FileData;

public class FileNavigation {

    public static boolean isContourPointsSection(int currentFilePart) {
        return currentFilePart == 1;
    }

    public static boolean isKeyPointsSection(int currentFilePart) {
        return currentFilePart == 2;
    }

    public static boolean isObjectsDefinitionSection(int currentFilePart) {
        return currentFilePart == 3;
    }

    public static boolean isObjectsSection(int currentFilePart) {
        return currentFilePart == 4;
    }
}
