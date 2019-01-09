package Diagram;

public class IncorrectFileException extends Exception {
    int line;

    public IncorrectFileException(int line){
        this.line = line;
    }

    public int getLine() {
        return line;
    }
}
