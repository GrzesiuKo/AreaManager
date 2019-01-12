package Exceptions;

public class IncorrectLineException extends Exception {
    int lineNumber;
    String line;


    public IncorrectLineException(int lineNumber, String line){
        this.lineNumber = lineNumber;
        this.line = line;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getLine() {
        return line;
    }
}
