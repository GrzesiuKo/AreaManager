package Exceptions;

public class IncorrectObjectLineException extends Exception {
    int lineNumber;
    String definition;
    String line;

    public IncorrectObjectLineException(int lineNumber, String line, String definition){
        this.lineNumber = lineNumber;
        this.line = line;
        this.definition = definition;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getDefinition() {
        return definition;
    }

    public String getLine() {
        return line;
    }
}
