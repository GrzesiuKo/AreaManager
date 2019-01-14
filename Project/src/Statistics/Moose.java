package Statistics;

import Common.Point;

public class Moose extends UserObject {
    private double value = 0;

    public Moose(Point point, String objectName, double value) {
        super(point, objectName);
        this.value = value;
    }

    @Override
    public String toString() {
        return "Łosiopodobny " + super.toString() + " Wartość: " + String.valueOf(value);
    }
}

