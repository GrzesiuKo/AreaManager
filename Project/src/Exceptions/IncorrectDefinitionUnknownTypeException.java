package Exceptions;

public class IncorrectDefinitionUnknownTypeException extends Exception {
    int lineNumber;
    String line;
    String type;

    public IncorrectDefinitionUnknownTypeException(int lineNumber, String line, String type){
        this.lineNumber = lineNumber;
        this.line = line;
        this.type = type;
    }

}
