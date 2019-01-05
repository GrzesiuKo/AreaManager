package Diagram;

public class Cell {
    public final static boolean BLACK = true;
    public final static boolean WHITE = false;

    private boolean color;
    private boolean hasObject;

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public boolean hasObject() {
        return hasObject;
    }

    public void setHasObject(boolean hasObject) {
        this.hasObject = hasObject;
    }

}
