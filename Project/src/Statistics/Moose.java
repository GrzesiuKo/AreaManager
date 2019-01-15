package Statistics;

import Common.Point;

public class Moose extends UserObject {
    private double value = 0;

    Moose(Point point, String objectName, double value) {
        super(point, objectName);
        this.value = value;
    }

    @Override
    public String toString() {
        return  super.toString() + "  " + String.valueOf(value);
    }
}

