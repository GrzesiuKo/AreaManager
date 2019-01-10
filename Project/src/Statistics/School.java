package Statistics;

import Common.Point;

public class School extends UserObject {
    private String name;

    public School(Point point, String name) {
        super(point);
        this.name = name;

    }

    @Override
    public String toString() {
        return name + " " + super.toString();
    }
}
