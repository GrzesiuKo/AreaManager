package Statistics;

import Common.Point;

public class School extends UserObject {
    private String name;

    School(Point point, String objectName, String name) {
        super(point , objectName);
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " " + super.toString();
    }
}
